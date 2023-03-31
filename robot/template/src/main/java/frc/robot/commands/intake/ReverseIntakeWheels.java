package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.WheelSubsystem;

public class ReverseIntakeWheels extends CommandBase{
    private final WheelSubsystem m_intake;
    private final Timer timer;
    private final double time;
    private final double speed;

    public ReverseIntakeWheels(WheelSubsystem m_intake, double time, double speed){
        this.m_intake = m_intake;
        timer = new Timer();
        this.time = time;
        this.speed = speed;

        addRequirements(this.m_intake);
    }

    @Override
    public void initialize(){
        timer.reset();
        timer.start();
    }


    @Override
    public void execute(){
        m_intake.runIntakeWheelsOut(speed);
    }

    @Override
    public boolean isFinished(){
        return timer.hasElapsed(time);
    }

    @Override
    public void end(boolean interrupted){
        m_intake.stopIntakeWheels();
    }
}
