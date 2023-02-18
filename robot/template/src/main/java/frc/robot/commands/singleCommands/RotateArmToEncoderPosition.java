package frc.robot.commands.singleCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.ArmSubsystem;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class RotateArmToEncoderPosition extends CommandBase{
    private final ArmSubsystem m_arm;
    private final double m_encoderPosition;

    public RotateArmToEncoderPosition(ArmSubsystem arm, double encoderPosition){
        this.m_arm = arm;
        this.m_encoderPosition = encoderPosition;

        addRequirements(m_arm);
    }

    @Override
    public void execute(){
        m_arm.rotateArmTo(m_encoderPosition);
    }  
}
