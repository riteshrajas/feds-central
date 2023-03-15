package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.IntakeSubsystem;

public class RetractIntake extends CommandBase {
    private final IntakeSubsystem m_intake;
    private final Timer timer;

    public RetractIntake(IntakeSubsystem intake) {
        this.m_intake = intake;
        timer = new Timer();

        addRequirements(m_intake);
    }    

    @Override
    public void initialize(){
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
         m_intake.rotateIntakeBackwards();
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(IntakeConstants.kRetractTime);
    }

    @Override
    public void end(boolean interrupted) {
        m_intake.stopIntakeRotation();
    }
}
