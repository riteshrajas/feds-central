package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.SwerveSubsystem;

public class GetOnBridge extends SequentialCommandGroup {
  public GetOnBridge(SwerveSubsystem s_swerve) {
    addCommands(
        new RunCommand(
                () -> {
                  //Robot.motionMode = MotionMode.NULL;
                  s_swerve.setModuleStates(
                      new SwerveModuleState[] {
                        new SwerveModuleState(-0.7, Rotation2d.fromDegrees(0)),
                        new SwerveModuleState(-0.7, Rotation2d.fromDegrees(0)),
                        new SwerveModuleState(-0.7, Rotation2d.fromDegrees(0)),
                        new SwerveModuleState(-0.7, Rotation2d.fromDegrees(0))
                      });
                })
            .until(() -> s_swerve.getGyroPitch() == 33),
        new RunCommand(
                () -> {
                  s_swerve.setModuleStates(
                      new SwerveModuleState[] {
                        new SwerveModuleState(0.2, Rotation2d.fromDegrees(0)),
                        new SwerveModuleState(0.2, Rotation2d.fromDegrees(0)),
                        new SwerveModuleState(0.2, Rotation2d.fromDegrees(0)),
                        new SwerveModuleState(0.2, Rotation2d.fromDegrees(0))
                      });
                })
            .until(() -> s_swerve.gyroPitchHasChanged()));
  }
}
