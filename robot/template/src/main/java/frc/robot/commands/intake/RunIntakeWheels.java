package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WheelSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class RunIntakeWheels extends CommandBase {
    private final WheelSubsystem m_intake;
    private final Timer timer;
    private final double time;

    public RunIntakeWheels(WheelSubsystem intake, double time) {
        this.m_intake = intake; 
        timer = new Timer();
        this.time = time;

        addRequirements(m_intake);
    } 
    

    @Override 
    public void initialize(){
        timer.reset();
        timer.start();
    }
    
    @Override
    public void execute() {
        m_intake.runIntakeWheelsIn();
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
