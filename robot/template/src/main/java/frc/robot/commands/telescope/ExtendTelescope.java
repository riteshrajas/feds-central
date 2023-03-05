package frc.robot.commands.telescope;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.TelescopeConstants;
import frc.robot.subsystems.TelescopeSubsystem;

public class ExtendTelescope extends CommandBase{

    private final TelescopeSubsystem s_telescope;
    private final Timer m_timer;

    public ExtendTelescope(TelescopeSubsystem s_telescope){
        this.s_telescope = s_telescope;
        m_timer = new Timer();

        addRequirements(this.s_telescope);
    }
    
    @Override
    public void initialize(){
        m_timer.reset();
        m_timer.start();
    }

    @Override
    public void execute(){
        s_telescope.extendTelescope();
    }

    @Override
    public boolean isFinished(){
        return m_timer.hasElapsed(2);
    }

    @Override
    public void end(boolean interrupted){
        s_telescope.stop();
    }
}
