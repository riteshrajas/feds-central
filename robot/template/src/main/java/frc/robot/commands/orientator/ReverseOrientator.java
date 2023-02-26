package frc.robot.commands.orientator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OrientatorSubsystem;

public class ReverseOrientator extends CommandBase{
    private OrientatorSubsystem ORIENTATOR_S;

    public ReverseOrientator(OrientatorSubsystem ORIENTATOR_S){
        this.ORIENTATOR_S = ORIENTATOR_S;

        addRequirements(this.ORIENTATOR_S);
    }

    @Override
    public void execute(){
        ORIENTATOR_S.rotateOrientatorOut();
    }

    public boolean isFinished(){
        return false;
    }

    public void end(boolean interrupted){
        ORIENTATOR_S.stopOrientator();
    }
}
