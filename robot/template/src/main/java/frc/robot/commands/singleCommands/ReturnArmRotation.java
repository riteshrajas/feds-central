package frc.robot.commands.singleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ReturnArmRotation extends CommandBase{

    private ArmSubsystem ARM_SUBSYSTEM;

    public ReturnArmRotation(){
        ARM_SUBSYSTEM = new ArmSubsystem();
    }

    @Override
    public void initialize(){

    }
    
}
