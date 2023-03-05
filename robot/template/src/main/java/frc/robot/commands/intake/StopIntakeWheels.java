package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class StopIntakeWheels extends CommandBase{
    private final IntakeSubsystem m_intake;
    private final Timer timer;

    public StopIntakeWheels(IntakeSubsystem m_intake){
        this.m_intake = m_intake;
        timer = new Timer();

        addRequirements(this.m_intake);
    }

    @Override
    public void initialize(){
        timer.reset();
        timer.start();
    }


    @Override
    public void execute(){
        m_intake.stopIntakeWheels();
    }

    @Override
    public boolean isFinished(){
        return timer.hasElapsed(1);
    }

    @Override
    public void end(boolean interrupted){
        m_intake.stopIntakeRotation();
    }
}
