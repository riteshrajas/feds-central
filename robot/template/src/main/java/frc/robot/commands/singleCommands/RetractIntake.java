package frc.robot.commands.singleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

public class RetractIntake extends CommandBase {
    private final IntakeSubsystem m_intake;

    public RetractIntake(IntakeSubsystem intake) {
        this.m_intake = intake;
    }    

    @Override
    public void execute() {
        // m_intake.runIntakeBackwards();
    }

    @Override
    public boolean isFinished() {
        return m_intake.getPositionEncoderCounts() <= Constants.IntakeConstants.kIntakeEncoderOffsetRetracted;
    }

    @Override
    public void end(boolean interrupted) {
        // m_intake.stopIntake();
    }
}
