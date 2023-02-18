package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase{
    
    private final TalonFX rotateMotor1;
    private final TalonFX rotateMotor2;
    private boolean rotateArm;
    private static ArmDirection ARM_DIRECTION;

    private enum ArmDirection{
        FORWARD,
        BACKWARD
    }    

    public ArmSubsystem(){
        rotateMotor1 = new TalonFX(ArmConstants.kArmMotor1);
        rotateMotor2 = new TalonFX(ArmConstants.kArmMotor2);
        rotateMotor2.follow(rotateMotor1);
    }

    public void rotateArm(){
        rotateArm = true;
        //if(getArmRotationPosition() <= ArmConstants.kArmRotatePreset1){
        ARM_DIRECTION = ArmDirection.FORWARD;
       // }
        //else{
            //ARM_DIRECTION= ArmDirection.BACKWARD;
        //}
    } 

    public void stop(){
        rotateArm = false;
    }

    public double getArmRotationPosition(){
        return rotateMotor1.getSelectedSensorPosition();
    }

    @Override
    public void periodic(){
        if(rotateArm){
            rotateMotor1.set(ControlMode.PercentOutput,
            ARM_DIRECTION == ArmDirection.FORWARD ? ArmConstants.kArmMotorSpeed : -ArmConstants.kArmMotorSpeed);
        }
        else if(rotateArm = false){
            rotateMotor1.set(ControlMode.PercentOutput,0);
            rotateMotor1.setNeutralMode(NeutralMode.Brake);
            rotateMotor2.setNeutralMode(NeutralMode.Brake);
        }
        SmartDashboard.putNumber("Rotate Motor Position", getArmRotationPosition());

    }
}
