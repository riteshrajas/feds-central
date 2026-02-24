package frc.robot.profiler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Shooter Profiling Tool — Brute-Force Trajectory Sweep
 *
 * <p>
 * Analytically sweeps every (fieldX, fieldY, hoodAngle, launchVelocity)
 * combination over the
 * entire FRC field and determines which combinations result in a successful
 * shot into the Hub.
 *
 * <p>
 * Physics model matches {@code frc.sim.gamepiece.LaunchParameters} exactly:
 * <ul>
 * <li>Ball launches from (robotX, robotY, LAUNCH_HEIGHT) — 0.6 m above ground
 * <li>Robot heading is always auto-aimed at hub (atan2 of hub − robot position)
 * <li>Horizontal speed = velocity × cos(hoodAngle)
 * <li>Vertical speed = velocity × sin(hoodAngle)
 * <li>Trajectory: z(t) = launchHeight + vz·t − ½·g·t² (pure gravity, no drag)
 * </ul>
 *
 * <p>
 * A shot is SUCCESSFUL if, at the moment the ball's XY position crosses the hub
 * center's
 * XY plane, the 3D position is within the Hub inner cylinder (radius check + Z
 * window check).
 *
 * <p>
 * Output CSV columns:
 * 
 * <pre>
 *   robot_x_m, robot_y_m, distance_m, hood_angle_deg, velocity_mps,
 *   tof_s, impact_x_m, impact_y_m, impact_z_m, heading_deg
 * </pre>
 *
 * <p>
 * Run via: {@code ./gradlew runProfiler}
 * <p>
 * Output file: {@code shooter_profile.csv} in the project root directory.
 */
public class ShooterProfiler {

    // ── Field size (2026 REBUILT — approximate, matches AprilTag layout) ─────
    private static final double FIELD_LENGTH_M = 16.4592; // meters
    private static final double FIELD_WIDTH_M = 8.2296; // meters

    // ── Hub geometry (from FieldConstants.Hub) ────────────────────────────────
    // Hub inner center point: (AprilTag26_x + hub_width/2, fieldWidth/2,
    // innerHeight)
    // AprilTag 26 (near hub face, blue side) is at x ≈ 1.0541 m based on field
    // layout.
    // hub_width = 47 in = 1.1938 m → hubCenterX = 1.0541 + 0.5969 = 1.6510 m
    // fieldWidth/2 = 4.1148 m
    // innerHeight = 56.5 in = 1.4351 m
    // innerWidth = 41.7 in = 1.0592 m → inner radius = 0.5296 m
    //
    // NOTE: These values are computed by FieldConstants at runtime from the
    // AprilTag JSON,
    // but since we can't load the HAL in a standalone tool, we precompute them
    // here.
    // If you update the field layout JSON, update these constants to match.
    private static final double HUB_CENTER_X_M = 1.6510; // meters from blue wall
    private static final double HUB_CENTER_Y_M = 4.1148; // meters from bottom wall
    private static final double HUB_INNER_HEIGHT_M = 1.4351; // where the ball enters (m)
    private static final double HUB_INNER_RADIUS_M = 0.5296; // half of 41.7 in = scoring cylinder radius (m)
    // Ball must enter the cylinder: allow the ball center to land within this
    // radius
    // Subtract ball radius so the entire ball clears the scoring opening
    private static final double BALL_RADIUS_M = 0.075; // 75 mm (from RebuiltGamePieces.FUEL)
    private static final double HUB_EFFECTIVE_RADIUS_M = HUB_INNER_RADIUS_M - BALL_RADIUS_M;
    // Z window: ball center must pass within ±0.3 m of inner height
    // (accounts for the catcher depth above the inner ring)
    private static final double HUB_Z_WINDOW_M = 0.30;
    private static final double HUB_Z_MIN_M = HUB_INNER_HEIGHT_M - HUB_Z_WINDOW_M;
    private static final double HUB_Z_MAX_M = HUB_INNER_HEIGHT_M + HUB_Z_WINDOW_M;

    // ── Shooter constraints ───────────────────────────────────────────────────
    private static final double LAUNCH_HEIGHT_M = 0.60; // ball exit height (m) — from ShooterSim
    private static final double MIN_HOOD_DEG = 10.0; // minimum hood angle (degrees)
    private static final double MAX_HOOD_DEG = 80.0; // maximum hood angle (degrees)
    private static final double HOOD_STEP_DEG = 1.0; // sweep step (degrees)
    private static final double MIN_VELOCITY_MPS = 5.0; // minimum launch speed (m/s)
    private static final double MAX_VELOCITY_MPS = 28.0; // maximum launch speed (m/s)
    private static final double VELOCITY_STEP_MPS = 0.5; // sweep step (m/s)

    // ── Field sweep resolution ────────────────────────────────────────────────
    private static final double GRID_STEP_M = 0.25; // field position grid step (meters)
    // Extra margin from field walls to avoid spawning the robot outside
    private static final double FIELD_MARGIN_M = 0.5;

    // ── Physics ───────────────────────────────────────────────────────────────
    private static final double GRAVITY_MPS2 = 9.80665;
    // Time-of-flight integration step (seconds) — small enough for sub-cm accuracy
    private static final double TOF_STEP_S = 0.001;
    private static final double MAX_FLIGHT_S = 5.0; // cap flight time to avoid infinite loop

    // ── Output ────────────────────────────────────────────────────────────────
    private static final String OUTPUT_FILE = "shooter_profile.csv";

    // ─────────────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("=== Shooter Profile Brute-Force Sweep ===");
        System.out.printf("Field: %.2f m × %.2f m  |  Grid: %.2f m steps%n",
                FIELD_LENGTH_M, FIELD_WIDTH_M, GRID_STEP_M);
        System.out.printf("Hood: %.0f° – %.0f° (%.0f° steps)%n",
                MIN_HOOD_DEG, MAX_HOOD_DEG, HOOD_STEP_DEG);
        System.out.printf("Velocity: %.1f – %.1f m/s (%.1f m/s steps)%n",
                MIN_VELOCITY_MPS, MAX_VELOCITY_MPS, VELOCITY_STEP_MPS);
        System.out.printf("Hub center: (%.4f, %.4f, %.4f)%n",
                HUB_CENTER_X_M, HUB_CENTER_Y_M, HUB_INNER_HEIGHT_M);

        long startNs = System.nanoTime();
        long totalCombos = 0;
        long successfulShots = 0;

        try (PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            // Write CSV header
            out.println("robot_x_m,robot_y_m,distance_m,hood_angle_deg,velocity_mps," +
                    "tof_s,impact_x_m,impact_y_m,impact_z_m,heading_deg");

            // ── Outer loops: field position grid ─────────────────────────────
            for (double ry = FIELD_MARGIN_M; ry <= FIELD_WIDTH_M - FIELD_MARGIN_M; ry += GRID_STEP_M) {
                for (double rx = FIELD_MARGIN_M; rx <= FIELD_LENGTH_M - FIELD_MARGIN_M; rx += GRID_STEP_M) {

                    // Skip if robot is too close to hub (inside hub structure)
                    double dxHub = HUB_CENTER_X_M - rx;
                    double dyHub = HUB_CENTER_Y_M - ry;
                    double distToHub = Math.sqrt(dxHub * dxHub + dyHub * dyHub);
                    if (distToHub < 0.7)
                        continue; // hub is ~0.6m half-width, skip interior

                    // Robot always aims at hub — compute required heading
                    double heading = Math.atan2(dyHub, dxHub); // radians, world frame

                    // ── Inner loops: angle & velocity ─────────────────────────
                    for (double angleDeg = MIN_HOOD_DEG; angleDeg <= MAX_HOOD_DEG; angleDeg += HOOD_STEP_DEG) {
                        double angleRad = Math.toRadians(angleDeg);
                        double hSpeed = Math.cos(angleRad); // unit horizontal speed factor
                        double vSpeed = Math.sin(angleRad); // unit vertical speed factor

                        for (double vel = MIN_VELOCITY_MPS; vel <= MAX_VELOCITY_MPS; vel += VELOCITY_STEP_MPS) {
                            totalCombos++;

                            // World-frame velocity components
                            double vx = vel * hSpeed * Math.cos(heading);
                            double vy = vel * hSpeed * Math.sin(heading);
                            double vz = vel * vSpeed;

                            // Launch position
                            double px = rx;
                            double py = ry;
                            double pz = LAUNCH_HEIGHT_M;

                            // Analytical time to cross hub XY center
                            // We integrate until XY distance to hub center is minimized
                            // (numerically step — allows handling any trajectory shape)
                            ShotResult result = traceTrajectory(px, py, pz, vx, vy, vz);

                            if (result != null) {
                                successfulShots++;
                                out.printf(Locale.US,
                                        "%.4f,%.4f,%.4f,%.2f,%.2f,%.4f,%.4f,%.4f,%.4f,%.2f%n",
                                        rx, ry, distToHub,
                                        angleDeg, vel,
                                        result.tof,
                                        result.impactX, result.impactY, result.impactZ,
                                        Math.toDegrees(heading));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR writing CSV: " + e.getMessage());
            System.exit(1);
        }

        long elapsedMs = (System.nanoTime() - startNs) / 1_000_000;
        double successRate = totalCombos > 0 ? 100.0 * successfulShots / totalCombos : 0;

        System.out.println("\n=== Results ===");
        System.out.printf("Total combos evaluated: %,d%n", totalCombos);
        System.out.printf("Successful shots:       %,d (%.2f%%)%n", successfulShots, successRate);
        System.out.printf("Output file:            %s%n", OUTPUT_FILE);
        System.out.printf("Elapsed time:           %,d ms%n", elapsedMs);
        System.out.println("\nDone! Run tools/shooter_profile_plotter.py to visualize.");
    }

    /**
     * Trace the ball trajectory using fixed-step numerical integration.
     *
     * <p>
     * Checks if the ball's 3D position at any timestep passes through the hub
     * scoring cylinder: XY distance to hub center ≤ effective radius, and Z is
     * within [HUB_Z_MIN, HUB_Z_MAX].
     *
     * <p>
     * The strategy: at each step, compute XY distance to hub center. Find the
     * timestep where XY distance is closest to hub center (minimum). At that point,
     * check Z. This avoids false negatives from skipping over the hub center.
     *
     * @return {@link ShotResult} with impact details if successful, {@code null} if
     *         miss
     */
    private static ShotResult traceTrajectory(double px, double py, double pz,
            double vx, double vy, double vz) {
        double prevDist = Double.MAX_VALUE;
        boolean closingIn = false;

        for (double t = 0; t <= MAX_FLIGHT_S; t += TOF_STEP_S) {
            // Kinematic equations (no drag)
            double x = px + vx * t;
            double y = py + vy * t;
            double z = pz + vz * t - 0.5 * GRAVITY_MPS2 * t * t;

            // Ball has hit the ground — stop tracing
            if (z < 0)
                break;

            // XY distance to hub center cylinder axis
            double dx = x - HUB_CENTER_X_M;
            double dy = y - HUB_CENTER_Y_M;
            double xyDist = Math.sqrt(dx * dx + dy * dy);

            // Detect when we start getting closer then further (passed closest approach)
            if (xyDist < prevDist) {
                closingIn = true;
            } else if (closingIn && xyDist > prevDist) {
                // We just passed the closest approach point — use previous step as impact
                // Go back one step for the best approximation
                double tPrev = t - TOF_STEP_S;
                double xPrev = px + vx * tPrev;
                double yPrev = py + vy * tPrev;
                double zPrev = pz + vz * tPrev - 0.5 * GRAVITY_MPS2 * tPrev * tPrev;
                double dxPrev = xPrev - HUB_CENTER_X_M;
                double dyPrev = yPrev - HUB_CENTER_Y_M;
                double xyDistPrev = Math.sqrt(dxPrev * dxPrev + dyPrev * dyPrev);

                // Check scoring conditions at closest approach
                if (xyDistPrev <= HUB_EFFECTIVE_RADIUS_M
                        && zPrev >= HUB_Z_MIN_M && zPrev <= HUB_Z_MAX_M) {
                    return new ShotResult(tPrev, xPrev, yPrev, zPrev);
                }

                // Missed — once we've passed and missed, we won't score
                break;
            }

            prevDist = xyDist;
        }

        return null; // no score
    }

    /** Immutable result of a successful shot. */
    private static class ShotResult {
        final double tof;
        final double impactX, impactY, impactZ;

        ShotResult(double tof, double impactX, double impactY, double impactZ) {
            this.tof = tof;
            this.impactX = impactX;
            this.impactY = impactY;
            this.impactZ = impactZ;
        }
    }
}
