package frc.robot.commands.sensor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveSubsystem;

public class RotateAlign extends CommandBase{

    private final SwerveSubsystem Swerve_S;

    public RotateAlign(SwerveSubsystem Swerve_S, boolean isTargetLow){
        Swerve_S = new SwerveSubsystem();
        this.Swerve_S = Swerve_S;


        addRequirements(this.Swerve_S);

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
