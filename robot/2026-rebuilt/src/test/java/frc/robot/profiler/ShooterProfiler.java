package frc.robot.profiler;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.sim.RebuiltField;
import frc.robot.sim.RebuiltGamePieces;
import org.ode4j.ode.DBody;
import org.ode4j.ode.DGeom;
import frc.sim.core.PhysicsWorld;
import frc.sim.gamepiece.GamePieceManager;
import frc.sim.gamepiece.LaunchParameters;
import frc.sim.gamepiece.GamePiece;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Shooter Profiling Tool — Brute-Force Trajectory Sweep
 *
 * <p>
 * Sweeps every (fieldX, fieldY, hoodAngle, launchVelocity)
 * combination over the entire FRC field using the PhysicsWorld engine.
 *
 * <p>
 * A shot is SUCCESSFUL if the ball contacts a scoring zone sensor in the field.
 *
 * <p>
 * Output CSV columns:
 *
 * <pre>
 *   robot_x_m, robot_y_m, distance_m, hood_angle_deg, velocity_mps,
 *   tof_s, impact_x_m, impact_y_m, impact_z_m, heading_deg
 * </pre>
 */
public class ShooterProfiler {

    // ── Field size (2026 REBUILT) ───────────────────────────────────────────
    private static final double FIELD_LENGTH_M = 16.4592;
    private static final double FIELD_WIDTH_M = 8.2296;

    // Hub center for distance calculation (matches RebuiltField)
    private static final double HUB_CENTER_X_M = 1.6510;
    private static final double HUB_CENTER_Y_M = 4.1148;

    // ── Shooter constraints ───────────────────────────────────────────────────
    private static final double LAUNCH_HEIGHT_M = 0.60;
    private static final double MIN_HOOD_DEG = 10.0;
    private static final double MAX_HOOD_DEG = 80.0;
    private static final double HOOD_STEP_DEG = 5.0; // Faster steps for live preview
    private static final double MIN_VELOCITY_MPS = 5.0;
    private static final double MAX_VELOCITY_MPS = 28.0;
    private static final double VELOCITY_STEP_MPS = 2.0;

    // ── Field sweep resolution ────────────────────────────────────────────────
    private static final double GRID_STEP_M = 1.0; // Faster steps for live preview
    private static final double FIELD_MARGIN_M = 1.0;

    // ── Physics ───────────────────────────────────────────────────────────────
    private static final double DT = 0.02;
    private static final double MAX_FLIGHT_S = 4.0;
    private static final double TIME_MULTIPLIER = 10.0;

    // ── Output ────────────────────────────────────────────────────────────────
    private static final String OUTPUT_FILE = "shooter_profile.csv";

    @Test
    public void runProfilerSweep() {
        System.out.println("=== Shooter Profile Physics Sim Sweep ===");

        // --- Initialize Physics ---
        PhysicsWorld physicsWorld = new PhysicsWorld();
        physicsWorld.setTimeMultiplier(TIME_MULTIPLIER);
        RebuiltField field = new RebuiltField(physicsWorld);
        GamePieceManager gamePieceManager = new GamePieceManager(physicsWorld);

        long startNs = System.nanoTime();
        long totalCombos = 0;
        long successfulShots = 0;

        try (PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            out.println("robot_x_m,robot_y_m,distance_m,hood_angle_deg,velocity_mps," +
                    "tof_s,impact_x_m,impact_y_m,impact_z_m,heading_deg");

            // Outer loops: field position grid
            for (double ry = FIELD_MARGIN_M; ry <= FIELD_WIDTH_M - FIELD_MARGIN_M; ry += GRID_STEP_M) {
                for (double rx = FIELD_MARGIN_M; rx <= FIELD_LENGTH_M - FIELD_MARGIN_M; rx += GRID_STEP_M) {

                    double dxHub = HUB_CENTER_X_M - rx;
                    double dyHub = HUB_CENTER_Y_M - ry;
                    double distToHub = Math.sqrt(dxHub * dxHub + dyHub * dyHub);
                    if (distToHub < 1.0)
                        continue;

                    double heading = Math.atan2(dyHub, dxHub);
                    Pose2d robotPose = new Pose2d(rx, ry, new Rotation2d(heading));

                    for (double angleDeg = MIN_HOOD_DEG; angleDeg <= MAX_HOOD_DEG; angleDeg += HOOD_STEP_DEG) {
                        for (double vel = MIN_VELOCITY_MPS; vel <= MAX_VELOCITY_MPS; vel += VELOCITY_STEP_MPS) {
                            totalCombos++;

                            ShotResult result = simulateShot(physicsWorld, field, gamePieceManager, robotPose, angleDeg,
                                    vel);

                            if (result != null) {
                                successfulShots++;
                                out.printf(Locale.US,
                                        "%.4f,%.4f,%.4f,%.2f,%.2f,%.4f,%.4f,%.4f,%.4f,%.2f%n",
                                        rx, ry, distToHub,
                                        angleDeg, vel,
                                        result.tof,
                                        result.impactX, result.impactY, result.impactZ,
                                        Math.toDegrees(heading));
                                out.flush();
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
        System.out.printf("Elapsed time:           %,d ms%n", elapsedMs);
    }

    private static ShotResult simulateShot(PhysicsWorld world, RebuiltField field, GamePieceManager manager,
            Pose2d robotPose, double angleDeg, double velocity) {

        // 1. Prepare launch parameters
        LaunchParameters params = new LaunchParameters(velocity, Math.toRadians(angleDeg), LAUNCH_HEIGHT_M, 0);
        Translation3d pos = params.getLaunchPosition(robotPose);
        Translation3d vel = params.getLaunchVelocity(robotPose, 0, 0);

        // 2. Spawn the ball
        manager.setHeldCount(1);
        GamePiece ball = manager.launchPiece(RebuiltGamePieces.FUEL, pos, vel);
        if (ball == null)
            return null;

        ShotResult result = null;
        double shotStartTime = world.getSimulatedTime();

        // 3. Step simulation
        while (world.getSimulatedTime() - shotStartTime < MAX_FLIGHT_S) {
            world.step(DT);

            // Check for score: ball contacts any scoring sensor
            for (DGeom goalZone : field.getScoringZones()) {
                Set<DBody> contacts = world.getSensorContacts(goalZone);
                if (contacts.contains(ball.getBody())) {
                    Translation3d impactPos = ball.getPosition3d();
                    result = new ShotResult(world.getSimulatedTime() - shotStartTime,
                            impactPos.getX(), impactPos.getY(), impactPos.getZ());
                    break;
                }
            }

            if (result != null)
                break;

            // Stop if ball hits ground
            if (ball.getPosition3d().getZ() < 0.05)
                break;
        }

        // 4. Cleanup: remove ball from world (so next shot starts fresh)
        ball.consume();

        return result;
    }

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
