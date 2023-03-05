package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OrientatorConstants;
import frc.robot.utils.ConeDetection;

public class OrientatorSubsystem extends SubsystemBase {
    private final TalonFX orientatorMotor;
    private final ConeDetection coneDetector;
    
    public OrientatorSubsystem(){
        orientatorMotor = new TalonFX(OrientatorConstants.kOrientatorMotorID);
        coneDetector = new ConeDetection();
        orientatorMotor.enableVoltageCompensation(true);
    }

    public void rotateOrientatorIn(){
        orientatorMotor.set(ControlMode.PercentOutput, OrientatorConstants.kOrientatorSpeed);
    }

    public void rotateOrientatorOut(){
        orientatorMotor.set(ControlMode.PercentOutput, -OrientatorConstants.kOrientatorSpeed);
    }

    public void stopOrientator(){
        orientatorMotor.set(ControlMode.PercentOutput, 0);
    }

}
