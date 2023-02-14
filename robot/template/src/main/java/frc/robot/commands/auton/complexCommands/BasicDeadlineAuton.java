package frc.robot.commands.auton.complexCommands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auton.singleCommands.Drive;
import frc.robot.commands.auton.singleCommands.DriveDuration;
import frc.robot.commands.auton.singleCommands.TimerDeadline;
import frc.robot.subsystems.DriveSubsystem;

public class BasicDeadlineAuton extends SequentialCommandGroup {
    public BasicDeadlineAuton(DriveSubsystem drive) {
        addCommands(
            new ParallelDeadlineGroup(
                new TimerDeadline(5),
                new Drive(drive, 0.25, 3, 0)));
    }
}
