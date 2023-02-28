package frc.robot.commands.sensor;

import frc.robot.subsystems.SwerveSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class StrafeAlign extends CommandBase{   

private final SwerveSubsystem Swerve_S;
private final boolean isTargetLow;

    public StrafeAlign(SwerveSubsystem Swerve_S, boolean isTargetLow){
        this.Swerve_S = Swerve_S;
        this.isTargetLow = isTargetLow;

        addRequirements(this.Swerve_S);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        Swerve_S.strafeToTarget(isTargetLow);
    }

    @Override
    public boolean isFinished(){
        return Swerve_S.finishedStrafeTarget();
    }

    @Override
    public void end(boolean interrupted){

    }

}