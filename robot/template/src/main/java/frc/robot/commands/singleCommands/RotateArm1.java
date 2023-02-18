package frc.robot.commands.singleCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.ArmSubsystem;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class RotateArm1 extends CommandBase{
    private ArmSubsystem armRotation = new ArmSubsystem();

    public RotateArm1(ArmSubsystem arm){
        armRotation = arm;

        addRequirements(armRotation);
    }

    

    @Override
    public void execute(){
        armRotation.rotateArm();
    }

    @Override
    public boolean isFinished(){
        return armRotation.getArmRotationPosition() >= (ArmConstants.kArmRotatePreset1 + ArmConstants.kArmOffset);
    }

    @Override
    public void end(boolean interrupted){
        armRotation.stop();
    }

    
}
