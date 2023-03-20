package frc.robot.commands.sensor;

import frc.robot.subsystems.SwerveSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class StrafeAlign extends CommandBase{   

private final SwerveSubsystem s_swerve;
private final boolean isTargetLow;

    public StrafeAlign(SwerveSubsystem s_swerve, boolean isTargetLow){
        this.s_swerve = s_swerve;
        this.isTargetLow = isTargetLow;

        addRequirements(this.s_swerve);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        s_swerve.strafeToTarget();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){

    }

}