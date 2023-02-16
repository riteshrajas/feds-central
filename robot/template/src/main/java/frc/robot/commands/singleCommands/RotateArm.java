package frc.robot.commands.singleCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.ArmSubsystem;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class RotateArm extends CommandBase{
    private ArmSubsystem armRotation = new ArmSubsystem();

    public RotateArm(ArmSubsystem arm){
        armRotation = arm;
    }

    public void initialize(){

    }

    public void execute(){
        armRotation.rotateArm(ArmConstants.kArmMotorSpeed);
    }

    public boolean isFinished(){
        return false;
    }

    public void end(){

    }

    
}
