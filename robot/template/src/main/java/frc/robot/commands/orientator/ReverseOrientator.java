package frc.robot.commands.orientator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OrientatorSubsystem;

public class ReverseOrientator extends CommandBase{
    private OrientatorSubsystem s_orientator;

    public ReverseOrientator(OrientatorSubsystem s_orientator){
        this.s_orientator = s_orientator;

        addRequirements(this.s_orientator);
    }

    @Override
    public void execute(){
        s_orientator.rotateOrientatorOut();
    }

    public boolean isFinished(){
        return false;
    }

    public void end(boolean interrupted){
        s_orientator.stopOrientator();
    }
}
