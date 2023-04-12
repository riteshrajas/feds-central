package frc.robot.commands.sensor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimelightSubsystem;

public class TeleopVision extends CommandBase{
    private final LimelightSubsystem s_limelight;

    public TeleopVision(LimelightSubsystem s_limelight){
        this.s_limelight = s_limelight;

        addRequirements(this.s_limelight);
    }

    public void execute(){
        s_limelight.setFieldMode();
    }
}
