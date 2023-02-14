package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

public class IntakeSubsystem extends SubsystemBase{

    private final TalonFX intakeDeployMotor;
    private final TalonFX intakeWheelMotor;

    public IntakeSubsystem(){
        intakeDeployMotor = new TalonFX(Constants.IntakeConstants.kIntakeRightDeployMotor);
        intakeWheelMotor = new TalonFX(Constants.IntakeConstants.kIntakeRightWheelMotor);
    }

    public double getPositionEncoderCounts(){
        return intakeDeployMotor.getSelectedSensorPosition();
    }
    
    // public void deploy(double intakeSpeed, double wheelSpeed){
    //     intakeDeployMotor.set(ControlMode.PercentOutput, intakeSpeed);
    //     intakeWheelMotor.set(ControlMode.PercentOutput, wheelSpeed);
    // }

    // public void stationary(double wheelSpeed){
    //     intakeDeployMotor.set(ControlMode.PercentOutput,0);
    //     intakeWheelMotor.set(ControlMode.PercentOutput,wheelSpeed);
    // }

    // public void stop(){
    //     intakeDeployMotor.set(ControlMode.PercentOutput,0);
    //     intakeWheelMotor.set(ControlMode.PercentOutput,0);
    // }

    public void runIntakeWheels() {
        intakeWheelMotor.set(ControlMode.PercentOutput, Constants.IntakeConstants.kIntakeWheelSpeed);
    }

    public void stopIntakeWheels() {
        intakeWheelMotor.set(ControlMode.PercentOutput, 0);
    }

    public void runIntakeDeploy() {
        intakeDeployMotor.set(ControlMode.PercentOutput, Constants.IntakeConstants.kIntakeDeploySpeed);
    }
    
    public void stopIntakeDeploy() {
        intakeDeployMotor.set(ControlMode.PercentOutput, 0);
    }
}
