package frc.robot.commands.singleCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TimerDeadline extends CommandBase {
    private Timer m_timer;
    private double m_duration;

    public TimerDeadline(double duration) {
        m_timer = new Timer();
        m_duration = duration;
    }

    @Override
    public void initialize() {
        m_timer.reset();
        m_timer.start();
    }

    public boolean isFinished() {
        return m_timer.get() > m_duration;
    }

    public void end(boolean interrupted) {
        m_timer.stop();
    }
}