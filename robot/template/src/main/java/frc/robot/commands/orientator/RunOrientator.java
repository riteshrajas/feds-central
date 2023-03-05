package frc.robot.commands.orientator;

import frc.robot.Constants.OrientatorConstants;
import frc.robot.subsystems.OrientatorSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunOrientator extends CommandBase{
    private final OrientatorSubsystem s_orientator;
    private final Timer timer;

    public RunOrientator(OrientatorSubsystem s_orientator){
        this.s_orientator = s_orientator;
        timer = new Timer();

        addRequirements(this.s_orientator);
    }

    @Override
    public void initialize(){
        timer.reset();
        timer.start();
    }
    
    @Override
    public void execute(){
        s_orientator.rotateOrientatorIn();
    }

    @Override
    public boolean isFinished(){
        return timer.hasElapsed(OrientatorConstants.KOrientatorTime);
    }

    @Override
    public void end(boolean interrupted){
        s_orientator.stopOrientator();
    }
}
