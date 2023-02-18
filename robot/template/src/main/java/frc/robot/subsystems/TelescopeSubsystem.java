package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TelescopeConstants;

public class TelescopeSubsystem extends SubsystemBase{

    private final TalonFX armTelescopeMotor;
    private boolean extendArm = false;
    private extendDirection EXTEND_DIRECTION;
    
    private enum extendDirection{
        OUT,
        IN
    } 
    
    public TelescopeSubsystem(){
        armTelescopeMotor = new TalonFX(TelescopeConstants.kTelescopeMotor);        
    }

    public double getTelescopePosition(){
        return armTelescopeMotor.getSelectedSensorPosition();
    }

    public void telescopePreset1(){
        extendArm = true;
        EXTEND_DIRECTION = extendDirection.OUT;
    }

    public void stop(){
        extendArm = false;
    }

    public void periodic(){
        if(extendArm){
            armTelescopeMotor.set(ControlMode.PercentOutput,
            EXTEND_DIRECTION == extendDirection.OUT ? TelescopeConstants.kTelescopeSpeed : -TelescopeConstants.kTelescopeOffset);
        }
        else{
            armTelescopeMotor.setNeutralMode(NeutralMode.Brake);
        }
    }
}
