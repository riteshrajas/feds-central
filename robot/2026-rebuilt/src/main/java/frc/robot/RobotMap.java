package frc.robot;

import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.util.Map;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.subsystems.swerve.CommandSwerveDrivetrain;
import frc.robot.subsystems.swerve.generated.TunerConstants;
import frc.robot.utils.FieldConstants;
import frc.robot.utils.SwerveModuleStatusUtil;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring.
 */
public final class RobotMap {

    public static class VisionConstants {
        // MT1 is configured to be effectively ignored for X/Y position (very large
        // stddev)
        // while still being trusted for rotation. The 1e6 X/Y values indicate extremely
        // high uncertainty in translation so pose estimators will down‑weight MT1's
        // position contribution, but the relatively small rotational stddev (~3
        // degrees)
        // allows MT1 to meaningfully contribute to heading estimation.
        public static final Matrix<N3, N1> MT1_STDDEV = VecBuilder.fill(1e6, 1e6, Math.PI / 60);
        // MT2 is the complementary measurement source: it is trusted for X/Y
        // translation
        // (small stddevs) and effectively ignored for rotation (very large stddev).
        // Together, these settings implement "use only x/y from MT2" and "use only
        // rotation from MT1" when fusing measurements.
        public static final Matrix<N3, N1> MT2_STDDEV = VecBuilder.fill(0.5, 0.5, 1e6);
    }

    public static class Constants {
        public static boolean disableHAL = false;

        public static void disableHAL() {
            disableHAL = true;
        }

        public static class Sim {
            public static double TIME_MULTIPLIER = 1.0;
        }
    }

    public enum robotState {
        SIM, REAL, REPLAY;
    }

    public static final class IntakeSubsystemConstants {
        public static final int kMotorID = 1;
        public static final int kLimit_switch_rID = 2;
        public static final int kLimit_switch_lID = 3;

    }

    public static robotState getRobotMode() {
        return Robot.isReal() ? robotState.REAL : robotState.SIM;
    }

    public static class DrivetrainConstants {
        private static final int kFrontLeftDriveMotorId = 11;
        private static final int kFrontLeftSteerMotorId = 12;
        private static final int kFrontLeftEncoderId = 13;
        private static final int kFrontRightDriveMotorId = 21;
        private static final int kFrontRightSteerMotorId = 22;
        private static final int kFrontRightEncoderId = 23;
        private static final int kBackLeftDriveMotorId = 32;
        private static final int kBackLeftSteerMotorId = 31;
        private static final int kBackLeftEncoderId = 33;
        private static final int kBackRightDriveMotorId = 41;
        private static final int kBackRightSteerMotorId = 42;
        private static final int kBackRightEncoderId = 43;

        public static CommandSwerveDrivetrain createDrivetrain() {
            SwerveModuleStatusUtil.addSwerveModule(SwerveModuleStatusUtil.ModuleLocation.FL, kFrontLeftDriveMotorId,
                    kFrontLeftSteerMotorId, kFrontLeftEncoderId);
            SwerveModuleStatusUtil.addSwerveModule(SwerveModuleStatusUtil.ModuleLocation.FR, kFrontRightDriveMotorId,
                    kFrontRightSteerMotorId, kFrontRightEncoderId);
            SwerveModuleStatusUtil.addSwerveModule(SwerveModuleStatusUtil.ModuleLocation.BL, kBackLeftDriveMotorId,
                    kBackLeftSteerMotorId, kBackLeftEncoderId);
            SwerveModuleStatusUtil.addSwerveModule(SwerveModuleStatusUtil.ModuleLocation.BR, kBackRightDriveMotorId,
                    kBackRightSteerMotorId, kBackRightEncoderId);

            return TunerConstants.createDrivetrain();
        }
    }

    public static class SpindexerConstants {
        public static final int kSpindexerMotorId = 0;
    }

    // ── Shooter constants ──────────────────────────────────────────────────────
    public static class ShooterConstants {
        public static final int kShooterLeaderId = 0;
        public static final int kShooterFollower1Id = 1;
        public static final int kShooterFollower2Id = 2;
        public static final int kShooterFollower3Id = 3;
        public static final int kHoodMotorId = 4;
        public static final AngularVelocity velocityTolerance = RotationsPerSecond.of(3);
        public static final Angle postionTolerance = Rotations.of(.05);

        public static final Angle maxHoodAngle = Rotations.of(0); // tune
        public static final Angle minHoodAngle = Rotations.of(0); // tune

        /**
         * Flywheel wheel radius (meters). Used to convert between surface speed
         * (m/s, what the sim uses) and flywheel RPS (what ShooterWheels uses):
         * RPS = velocity_mps / (2π × FLYWHEEL_RADIUS_M)
         * PLACEHOLDER — measure and update with the actual radius from CAD.
         */
        public static final double FLYWHEEL_RADIUS_M = 0.0508; // 2-inch wheel placeholder

        public static final Translation2d hubCenter = FieldConstants.Hub.innerCenterPoint.toTranslation2d();

        // ── Shooting maps (generated by ShooterProfiler brute-force sim, 531 k shots)
        // ──
        //
        // Keys = distance to hub (meters)
        // Values = minimum launch surface-speed (m/s) that scores from that distance
        // → used directly by ShooterSim (LaunchParameters expects m/s)
        // → divide by (2π × FLYWHEEL_RADIUS_M) to get RPS for ShooterWheels
        public static final InterpolatingDoubleTreeMap kShootingVelocityMap = InterpolatingDoubleTreeMap.ofEntries(
                Map.entry(0.88, 5.00),
                Map.entry(1.27, 5.00),
                Map.entry(1.77, 5.00),
                Map.entry(2.28, 5.50),
                Map.entry(2.75, 6.00),
                Map.entry(3.25, 6.50),
                Map.entry(3.71, 6.50),
                Map.entry(4.25, 7.00),
                Map.entry(4.75, 7.50),
                Map.entry(5.25, 8.00),
                Map.entry(5.75, 8.00),
                Map.entry(6.25, 8.50),
                Map.entry(6.75, 8.50),
                Map.entry(7.25, 9.00),
                Map.entry(7.75, 9.50),
                Map.entry(8.24, 9.50),
                Map.entry(8.75, 10.00),
                Map.entry(9.24, 10.00),
                Map.entry(9.76, 10.50),
                Map.entry(10.26, 10.50),
                Map.entry(10.74, 11.00),
                Map.entry(11.24, 11.00),
                Map.entry(11.75, 11.00),
                Map.entry(12.25, 11.50),
                Map.entry(12.76, 11.50),
                Map.entry(13.25, 12.00),
                Map.entry(13.75, 12.00),
                Map.entry(14.17, 12.50));

        // Keys = distance to hub (meters)
        // Values = optimal hood angle (rotations, i.e. degrees/360) for the
        // min-velocity shot
        // → multiply by 2π to get radians for ShooterSim
        // → pass directly to TalonFX PositionVoltage for ShooterHood
        public static final InterpolatingDoubleTreeMap kShootingPositionMap = InterpolatingDoubleTreeMap.ofEntries(
                Map.entry(0.88, 0.119444), // 43.0 deg
                Map.entry(1.27, 0.113889), // 41.0 deg
                Map.entry(1.77, 0.119444), // 43.0 deg
                Map.entry(2.28, 0.119444), // 43.0 deg
                Map.entry(2.75, 0.111111), // 40.0 deg
                Map.entry(3.25, 0.108333), // 39.0 deg
                Map.entry(3.71, 0.115278), // 41.5 deg
                Map.entry(4.25, 0.113889), // 41.0 deg
                Map.entry(4.75, 0.105556), // 38.0 deg
                Map.entry(5.25, 0.102778), // 37.0 deg
                Map.entry(5.75, 0.113889), // 41.0 deg
                Map.entry(6.25, 0.102778), // 37.0 deg
                Map.entry(6.75, 0.113889), // 41.0 deg
                Map.entry(7.25, 0.105556), // 38.0 deg
                Map.entry(7.75, 0.102778), // 37.0 deg
                Map.entry(8.24, 0.108333), // 39.0 deg
                Map.entry(8.75, 0.102778), // 37.0 deg
                Map.entry(9.24, 0.108333), // 39.0 deg
                Map.entry(9.76, 0.100000), // 36.0 deg
                Map.entry(10.26, 0.108333), // 39.0 deg
                Map.entry(10.74, 0.102778), // 37.0 deg
                Map.entry(11.24, 0.106944), // 38.5 deg
                Map.entry(11.75, 0.113889), // 41.0 deg
                Map.entry(12.25, 0.105556), // 38.0 deg
                Map.entry(12.76, 0.113889), // 41.0 deg
                Map.entry(13.25, 0.102778), // 37.0 deg
                Map.entry(13.75, 0.111111), // 40.0 deg
                Map.entry(14.17, 0.102778) // 37.0 deg
        );

        // Passing maps — tuned separately from match play; zeroed for now
        public static final InterpolatingDoubleTreeMap kPassingVelocityMap = InterpolatingDoubleTreeMap.ofEntries(
                Map.entry(0.0, 0.0));

        public static final InterpolatingDoubleTreeMap kPassingPositionMap = InterpolatingDoubleTreeMap.ofEntries(
                Map.entry(0.0, 0.0));
    }
}
