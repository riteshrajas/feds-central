package frc.robot.commands.sensor;

import frc.robot.subsystems.SwerveSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class StrafeAlign extends CommandBase{   

private final SwerveSubsystem s_swerve;

    public StrafeAlign(SwerveSubsystem s_swerve){
        this.s_swerve = s_swerve;

        addRequirements(this.s_swerve);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        s_swerve.strafeToTarget();
    }

}