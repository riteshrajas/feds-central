package frc.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;

public class TelescopeConstants {
    public static final int kTelescopeMotor = 60;

    public static final double kP = 0.05; // FIXME: CONFIG THIS
    public static final double kI = 0;
    public static final double kD = 0;

    public static final int    peakVelocity        = 100_000; // What could this be?
    public static final double percentOfPeak       = .35;
    public static final double cruiseVelocityAccel = peakVelocity * percentOfPeak;
    
    // Setpoints
    public static final double kTelescopeOffset = 0;
    public static final double kTelescopeExtendedMax = 900_000; // FIXME: CONFIG THIS
    public static final double kTelescopeExtendedMiddle = 400_000; // FIXME: CONFIG THIS
    public static final double kTelescopeGrabCone = 275_000;
    public static final double kTelescopeThreshold = 1000;
    public static final double kForwardTelescopeSoftLimit = 1_000_000;
    public static final double kReverseTelescopeSoftLimit = -1;

    public static final double kManualSpeedOut = 0.93;
    public static final double kManualSpeedIn = 0.93;

    public static final Rotation2d kThreshold = Rotation2d.fromDegrees(3);
}
