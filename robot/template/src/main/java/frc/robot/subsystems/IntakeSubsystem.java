package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeDeployMotor;

    public IntakeSubsystem() {
        intakeDeployMotor = new TalonFX(IntakeConstants.kIntakeBlueLeftDeployMotor);
        IntakeConstants.configIntakeMotor(intakeDeployMotor);
    }

    public double getPositionEncoderCounts() {
        return intakeDeployMotor.getSelectedSensorPosition();
    }

    public void rotateIntakeForwards() {
        intakeDeployMotor.set(TalonFXControlMode.PercentOutput, IntakeConstants.kIntakeDeploySpeed);
    }

    public void rotateIntakeBackwards() {
        intakeDeployMotor.set(TalonFXControlMode.PercentOutput, IntakeConstants.kIntakeRetractSpeed);
    }

    public void stopIntakeRotation() {
        intakeDeployMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    

    public void setIntakePosition(double position){
        intakeDeployMotor.set(ControlMode.MotionMagic, position);
    }

    public boolean hitForwardSoftLimit(){
        return getPositionEncoderCounts() >= IntakeConstants.kIntakeForwardSetpoint;
    }
    
    public boolean hitReverseSoftLimit(){
        return getPositionEncoderCounts() <= IntakeConstants.kIntakeRetractSetpoint;
    }
}
