package frc.robot.commands.orientator;

import frc.robot.subsystems.OrientatorSubsystem; 

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunOrientator extends CommandBase{
    private OrientatorSubsystem ORIENTATOR_S;

    public RunOrientator(OrientatorSubsystem ORIENTATOR_S){
        this.ORIENTATOR_S = ORIENTATOR_S;

        addRequirements(this.ORIENTATOR_S);
    }

    @Override
    public void execute(){
        ORIENTATOR_S.rotateOrientatorIn();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        ORIENTATOR_S.stopOrientator();
    }
}
