package frc.robot.constants;

public final class IntakeConstants {
    // Pheonix IDs
    public static final int kIntakeBlueLeftDeployMotor = 51;
    public static final int kIntakeBlueLeftWheelMotor = 61;
    public static final int kIntakeRedRightDeployMotor = 52;
    public static final int kIntakeRedRightWheelMotor = 62;
    public static final int kIntakeTriggerID = 2;

    // Speeds
    public static final double kIntakeDeploySpeed = 0.30;
    public static final double kIntakeRetractSpeed = -0.2;
    public static final double kIntakeWheelSpeed = -0.30;

    public static final double kDeployTime = 0.5;
    public static final double kRetractTime = 0.5;

    // Encoder Counts
    public static final double kIntakeEncoderOffsetDeployed = 2640;
    public static final double kIntakeEncoderOffsetRetracted = 0;
}