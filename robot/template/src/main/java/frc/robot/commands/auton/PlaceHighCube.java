package frc.robot.commands.auton;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.arm2.RotateArm2Position;
import frc.robot.commands.claw.OuttakeCone;
import frc.robot.commands.sensor.DepthAlign;
import frc.robot.commands.sensor.StrafeAlign;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.ArmSubsystem5;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.WheelSubsystem;


public class PlaceHighCube extends SequentialCommandGroup{
    
    public PlaceHighCube(SwerveSubsystem s_swerve, ArmSubsystem5 s_arm, ClawSubsystem s_claw){

        addRequirements(s_swerve);


        addCommands(
                new ParallelDeadlineGroup(
                        new WaitCommand(2.5), 
                        new RotateArm2Position(s_arm, ArmConstants.kArmPutHigh),
                        new SequentialCommandGroup(
                                new WaitCommand(2.0),
                                new RunCommand(
                                () -> {
                                // Robot.motionMode = MotionMode.NULL;
                                s_swerve.setModuleStates(
                                new SwerveModuleState[] {
                                        new SwerveModuleState(-.5, Rotation2d.fromDegrees(0)),
                                        new SwerveModuleState(-.5, Rotation2d.fromDegrees(0)),
                                        new SwerveModuleState(-.5, Rotation2d.fromDegrees(0)),
                                        new SwerveModuleState(-.5, Rotation2d.fromDegrees(0))
                            });
                        }))),
                new ParallelDeadlineGroup(
                        new WaitCommand(0.4), 
                        new OuttakeCone(s_claw),
                        new RotateArm2Position(s_arm, ArmConstants.kArmPutHigh)),
                new ParallelDeadlineGroup(
                        new WaitCommand(0.4), 
                        new RunCommand(
                        () -> {
                        // Robot.motionMode = MotionMode.NULL;
                        s_swerve.setModuleStates(
                        new SwerveModuleState[] {
                              new SwerveModuleState(.5, Rotation2d.fromDegrees(0)),
                              new SwerveModuleState(.5, Rotation2d.fromDegrees(0)),
                              new SwerveModuleState(.5, Rotation2d.fromDegrees(0)),
                              new SwerveModuleState(.5, Rotation2d.fromDegrees(0))
                            });
                        }),
                        new RotateArm2Position(s_arm, ArmConstants.kArmPutHigh)),
                new ParallelDeadlineGroup(
                        new WaitCommand(2.7),
                        new RotateArm2Position(s_arm, ArmConstants.kArmHome))
        );
    }
}
