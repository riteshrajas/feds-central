package frc.robot.constants;

import frc.lib.math.Conversions;

public final class IntakeConstants {
    // Pheonix IDs
    public static final int kIntakeBlueLeftDeployMotor = 51;
    public static final int kIntakeBlueLeftWheelMotor = 61;
    public static final int kIntakeTriggerID = 2;

    // Speeds
    public static final double kIntakeDeploySpeed = 0.30;
    public static final double kIntakeRetractSpeed = -0.2;
    public static final double kIntakeWheelSpeed = -0.30;
    public static final double kIntakeWheelEjectTime = 0.5;

    public static final double kDeployTime = 0.5;
    public static final double kRetractTime = 0.5;

    // Encoder Counts
    public static final double kIntakeEncoderOffsetDeployed = 2640;
    public static final double kIntakeEncoderOffsetRetracted = 0;

    public static final double kIntakeGearRatio = 25;

    //Soft Limits
    public static final double kIntakeForwardSoftLimit = Conversions.degreesToFalcon(90, kIntakeGearRatio);
    public static final double kIntakeRetractSoftLimit = Conversions.degreesToFalcon(-10, kIntakeGearRatio);
}