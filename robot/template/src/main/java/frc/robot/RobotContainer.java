package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.auton.complexCommands.BasicAuton;
import frc.robot.commands.auton.complexCommands.BasicDeadlineAuton;
import frc.robot.commands.auton.singleCommands.DriveDuration;
import frc.robot.commands.teleop.FieldRelativeDriveControlCommand;
import frc.robot.commands.teleop.SwerveDriveControlCommand;
import frc.robot.subsystems.DriveSubsystem;

public class RobotContainer {
    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
   
    XboxController m_driveController = new XboxController(Constants.OIConstants.kDriveControllerPort);
    XboxController m_operatorController = new XboxController(Constants.OIConstants.kOperatorControllerPort);

    private final Command m_basicAuton = new BasicAuton(m_robotDrive);
    private final Command m_deadlineAuton = new BasicDeadlineAuton(m_robotDrive);

    SendableChooser<Command> m_autonChooser = new SendableChooser<>();

    public RobotContainer() {
        m_robotDrive.setDefaultCommand(
            new FieldRelativeDriveControlCommand(m_robotDrive, () -> -m_driveController.getLeftY(), () -> -m_driveController.getLeftX(), () -> m_driveController.getRightX(), () -> m_robotDrive.getPoseAngle())
        );

        m_autonChooser.setDefaultOption("basicAuton", m_basicAuton);
        m_autonChooser.addOption("basicDeadlineAuton", m_deadlineAuton);

        Shuffleboard.getTab("Autonomous Command").add(m_autonChooser);
    }

    public Command getAutonomousCommand() {
        return m_autonChooser.getSelected();
    }
}
