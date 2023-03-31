package frc.robot.commands.sensor;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class DepthAlign extends CommandBase{
    private final SwerveSubsystem s_swerve;
    private final LimelightSubsystem s_limelight;
    private final PIDController depthController;

    public DepthAlign(SwerveSubsystem s_swerve, LimelightSubsystem s_limelight){
        this.s_swerve = s_swerve;
        this.s_limelight = s_limelight;
        depthController = new PIDController(0.2, 0, 0);

        addRequirements(this.s_swerve);
        addRequirements(this.s_limelight);
    }
    
    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        double depthCommand = depthController.calculate(s_limelight.getHorizontalDistanceToTarget(), VisionConstants.kDepthAlignmentDistance);
        s_swerve.drive(new Translation2d(depthCommand, new Rotation2d(0)), 0, true, true);
    }

    @Override
    public boolean isFinished(){
        return Math.abs(s_limelight.getHorizontalDistanceToTarget() - VisionConstants.kDepthAlignmentDistance) < VisionConstants.kDepthAlignmentDistance;
    }
}
