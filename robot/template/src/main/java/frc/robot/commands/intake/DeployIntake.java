package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.IntakeSubsystem;

public class DeployIntake extends CommandBase {
    private final IntakeSubsystem m_intake;

    public DeployIntake(IntakeSubsystem intake) {
        this.m_intake = intake;

        addRequirements(m_intake);
    }    

    @Override
    public void initialize(){
    }

    @Override
    public void execute() {
        m_intake.rotateIntakeForwards();
    }

    @Override
    public boolean isFinished() {
        return m_intake.hitSoftLimit();
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.stopIntakeRotation();
    }
}
