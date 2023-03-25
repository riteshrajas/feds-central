package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WheelSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;

public class RunIntakeWheelsInfinite extends CommandBase {
    private final WheelSubsystem m_wheels;
    private final double speed;


    public RunIntakeWheelsInfinite(WheelSubsystem intake, double speed) {
        this.m_wheels = intake; 
        this.speed = speed;
       

        addRequirements(m_wheels);
    } 
    public RunIntakeWheelsInfinite(WheelSubsystem intake) {
        this.m_wheels = intake; 
        this.speed = 0;
       

        addRequirements(m_wheels);
    } 

    @Override 
    public void initialize(){
       
    }
    
    @Override
    public void execute() {
        if(speed != 0) {
            m_wheels.runIntakeWheelsIn(speed);
        } else {
            m_wheels.runIntakeWheelsIn();
        }
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        m_wheels.stopIntakeWheels();
    }
}
