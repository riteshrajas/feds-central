package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WheelSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;

public class RunIntakeWheelsInfinite extends CommandBase {
    private final WheelSubsystem m_intake;
    private final Timer timer;

    public RunIntakeWheelsInfinite(WheelSubsystem intake) {
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
        m_intake.runIntakeWheelsIn();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        m_intake.stopIntakeWheels();
    }
}
