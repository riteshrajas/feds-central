package frc.robot.commands.auton;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm2.RotateArm2Position;
import frc.robot.commands.claw.OuttakeCone;
import frc.robot.commands.utilityCommands.TimerDeadline;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem5;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class PlaceConeHigh extends SequentialCommandGroup{
    private ArmSubsystem5 s_arm;
    private ClawSubsystem s_claw;
    private SwerveSubsystem s_swerve;

    public PlaceConeHigh(ArmSubsystem5 s_arm, ClawSubsystem s_claw, SwerveSubsystem s_swerve){
        this.s_arm = s_arm;
        this.s_claw = s_claw;
        this.s_swerve = s_swerve;

        addRequirements(this.s_arm);
        addRequirements(this.s_claw);
        addRequirements(this.s_swerve);

        addCommands(
            new RotateArm2Position(s_arm, ArmConstants.kArmPutHigh),
            new OuttakeCone(s_claw),
            new ParallelDeadlineGroup(new TimerDeadline(1), new RunCommand(
                () -> {
                  // Robot.motionMode = MotionMode.NULL;
                  s_swerve.setModuleStates(
                      new SwerveModuleState[] {
                          new SwerveModuleState(-1.2, Rotation2d.fromDegrees(0)),
                          new SwerveModuleState(-1.2, Rotation2d.fromDegrees(0)),
                          new SwerveModuleState(-1.2, Rotation2d.fromDegrees(0)),
                          new SwerveModuleState(-1.2, Rotation2d.fromDegrees(0))
                      });
                }
        )),
        new ParallelDeadlineGroup(new TimerDeadline(1), new RunCommand(
                () -> {
                  // Robot.motionMode = MotionMode.NULL;
                  s_swerve.setModuleStates(
                      new SwerveModuleState[] {
                          new SwerveModuleState(1.2, Rotation2d.fromDegrees(0)),
                          new SwerveModuleState(1.2, Rotation2d.fromDegrees(0)),
                          new SwerveModuleState(1.2, Rotation2d.fromDegrees(0)),
                          new SwerveModuleState(1.2, Rotation2d.fromDegrees(0))
                      });
                }
        )),
            new RotateArm2Position(s_arm, ArmConstants.kArmHome)
        );
    }
    
}
