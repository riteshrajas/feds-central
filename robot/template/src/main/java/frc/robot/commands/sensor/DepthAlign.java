package frc.robot.commands.sensor;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class DepthAlign extends CommandBase{
    private final SwerveSubsystem s_swerve;
    private final LimelightSubsystem s_limelight;
    private final PIDController depthController;

    public DepthAlign(SwerveSubsystem s_swerve, LimelightSubsystem s_limelight){
        this.s_swerve = s_swerve;
        this.s_limelight = s_limelight;
        depthController = new PIDController(0, 0, 0);
    }
    
}
