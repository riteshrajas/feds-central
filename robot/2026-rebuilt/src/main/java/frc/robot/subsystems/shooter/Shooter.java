package frc.robot.subsystems.shooter;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import java.util.function.DoubleSupplier;

/**
 * Shooter subsystem stub — wired to the ShooterProfiler-derived lookup tables.
 *
 * <p>
 * Hood angle and launch velocity are now looked up from
 * {@link RobotMap.ShooterConstants#kShootingPositionMap} and
 * {@link RobotMap.ShooterConstants#kShootingVelocityMap} using the current
 * distance to the hub. This makes the ShooterSim use the same physics-backed
 * parameters that the brute-force profiler found to guarantee scoring.
 *
 * <p>
 * Students: replace the motor stubs with real TalonFX control once the
 * hardware-specific subsystems (ShooterWheels, ShooterHood) are fully wired in.
 */
public class Shooter extends SubsystemBase {

    // ── Distance supplier ──────────────────────────────────────────────────────
    /**
     * Supplies the current robot-to-hub distance in meters.
     * Wire this to {@code CommandSwerveDrivetrain::getDistanceToHub} (in meters).
     * Falls back to a fixed 3.0 m if null is passed (safe default for sim).
     */
    private final DoubleSupplier distanceToHubM;

    private boolean isShooting = false;

    /** Minimum hood angle — from profiler sweep: 36° minimum. */
    private static final double HOOD_MIN = Math.toRadians(10);
    /** Maximum hood angle — from profiler sweep: 43° maximum. */
    private static final double HOOD_MAX = Math.toRadians(80);

    // Distance clamps — keep lookup within the profiler's valid range
    private static final double MIN_PROFILER_DIST = 0.88;
    private static final double MAX_PROFILER_DIST = 14.17;

    /**
     * Create the shooter subsystem.
     *
     * @param distanceToHubM supplier of robot-to-hub distance in meters.
     *                       Pass {@code () -> 3.0} for a fixed-distance sim/test.
     */
    public Shooter(DoubleSupplier distanceToHubM) {
        this.distanceToHubM = distanceToHubM != null ? distanceToHubM : () -> 3.0;
    }

    /** Convenience constructor for tests — fixed 3-m distance. */
    public Shooter() {
        this(() -> 3.0);
    }

    // ── Map lookup helpers ─────────────────────────────────────────────────────

    /**
     * Returns the current distance, clamped to the profiler's valid range so the
     * InterpolatingDoubleTreeMap never extrapolates outside the known data.
     */
    private double clampedDist() {
        return Math.max(MIN_PROFILER_DIST, Math.min(MAX_PROFILER_DIST, distanceToHubM.getAsDouble()));
    }

    // ── SubsystemBase ──────────────────────────────────────────────────────────

    @Override
    public void periodic() {
        var nt = NetworkTableInstance.getDefault();
        nt.getEntry("Shooter/HoodAngleDeg").setDouble(Math.toDegrees(getHoodAngleRad()));
        nt.getEntry("Shooter/LaunchVelocityMps").setDouble(getLaunchVelocity());
        nt.getEntry("Shooter/DistanceToHubM").setDouble(distanceToHubM.getAsDouble());
        nt.getEntry("Shooter/IsShooting").setBoolean(isShooting);
    }

    // ── Hood manual trim (for teleop override) ─────────────────────────────────

    /**
     * Nudge the hood upward — useful for manually trimming if the sim lookup
     * needs live adjustment. For now this is a no-op stub.
     */
    public void adjustHoodUp(double dt) {
        /* stub */ }

    /** Nudge the hood downward. */
    public void adjustHoodDown(double dt) {
        /* stub */ }

    // ── State ──────────────────────────────────────────────────────────────────

    public void startShooting() {
        isShooting = true;
    }

    public void stopShooting() {
        isShooting = false;
    }

    // ── Commands ───────────────────────────────────────────────────────────────

    public Command hoodUpCommand() {
        return run(() -> adjustHoodUp(0.02)).finallyDo(() -> {
        });
    }

    public Command hoodDownCommand() {
        return run(() -> adjustHoodDown(0.02)).finallyDo(() -> {
        });
    }

    public Command shootCommand() {
        return startEnd(this::startShooting, this::stopShooting);
    }

    // ── Sim accessors (used by ShooterSim) ─────────────────────────────────────

    /**
     * Hood angle in radians, looked up from the profiler-derived
     * {@code kShootingPositionMap} (distance → rotations → converted to radians).
     */
    public double getHoodAngleRad() {
        double turns = RobotMap.ShooterConstants.kShootingPositionMap.get(clampedDist());
        double rad = turns * 2.0 * Math.PI;
        // Safety clamp so physical hood limits are always respected
        return Math.max(HOOD_MIN, Math.min(HOOD_MAX, rad));
    }

    /** Whether the shooter is actively firing. */
    public boolean isShooting() {
        return isShooting;
    }

    /**
     * Launch surface speed in m/s, looked up from the profiler-derived
     * {@code kShootingVelocityMap} (distance → m/s).
     * ShooterSim passes this directly to {@code LaunchParameters}.
     */
    public double getLaunchVelocity() {
        return RobotMap.ShooterConstants.kShootingVelocityMap.get(clampedDist());
    }
}
