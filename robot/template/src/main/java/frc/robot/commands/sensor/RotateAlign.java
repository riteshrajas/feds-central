package frc.robot.commands.sensor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveSubsystem;

public class RotateAlign extends CommandBase{

    private final SwerveSubsystem s_swerve;

    public RotateAlign(SwerveSubsystem Swerve_S, boolean isTargetLow){
        this.s_swerve = Swerve_S;


        addRequirements(this.s_swerve);

    }

    @Override
    public void initialize(){
        
    }

    @Override
    public void execute(){

    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){

    }

}
