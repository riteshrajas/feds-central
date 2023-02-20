package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

public class DeployIntake extends CommandBase {
    private final IntakeSubsystem m_intake;

    public DeployIntake(IntakeSubsystem intake) {
        this.m_intake = intake;
    }    

    @Override
    public void execute() {
        m_intake.rotateIntakeForwards();
    }

    @Override
    public boolean isFinished() {
        return m_intake.getPositionEncoderCounts() >= Constants.IntakeConstants.kIntakeEncoderOffsetDeployed;
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.stopIntakeRotation();
    }
}
