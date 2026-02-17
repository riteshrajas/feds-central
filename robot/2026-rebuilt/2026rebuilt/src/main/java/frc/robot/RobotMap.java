package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring.
 */
public final class RobotMap {

    public enum robotState{
        SIM,REAL,REPLAY;
    }
    public static final class IntakeSubsystemConstants {
        public static final int kMotorID = 1;
        public static final int kLimit_switch_rID = 2;
        public static final int kLimit_switch_lID = 3; 

    }

    public static robotState getRobotMode() {
        return Robot.isReal() ? robotState.REAL : robotState.SIM;
    }
}
