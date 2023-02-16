package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeDeployMotor;
    private final TalonFX intakeWheelMotor;

    private static boolean rotateIntake = false;
    private static IntakeRotateDirection intakeRotateDirection = IntakeRotateDirection.FORWARD;
    
    private static boolean runWheels = false;
    private static IntakeWheelsDirection intakeWheelsDirection = IntakeWheelsDirection.IN;

    private enum IntakeRotateDirection {
        FORWARD,
        BACKWARD
    }

    private enum IntakeWheelsDirection {
        IN,
        OUT
    }

    public IntakeSubsystem() {
        intakeDeployMotor = new TalonFX(Constants.IntakeConstants.kIntakeRightDeployMotor);
        intakeWheelMotor = new TalonFX(Constants.IntakeConstants.kIntakeRightWheelMotor);
    }

    public double getPositionEncoderCounts() {
        return intakeDeployMotor.getSelectedSensorPosition();
    }

    public void rotateIntakeForwards() {
        rotateIntake = true;
        intakeRotateDirection = IntakeRotateDirection.FORWARD;
    }

    public void rotateIntakeBackwards() {
        rotateIntake = true;
        intakeRotateDirection = IntakeRotateDirection.BACKWARD;
    }

    public void stopIntakeRotation() {
        rotateIntake = false;
    }

    public void runIntakeWheelsIn() {
        runWheels = true;
        intakeWheelsDirection = IntakeWheelsDirection.IN;
    }

    public void runIntakeOut() {
        runWheels = true;
        intakeWheelsDirection = IntakeWheelsDirection.OUT;
    }

    public void stopIntakeWheels() {
        runWheels = false;
    }

    @Override
    public void periodic() {
        if(rotateIntake) {
            intakeDeployMotor.set(ControlMode.PercentOutput, 
                intakeRotateDirection == IntakeRotateDirection.FORWARD ? Constants.IntakeConstants.kIntakeDeploySpeed : -Constants.IntakeConstants.kIntakeRetractSpeed);
        }

        if(runWheels) {
            intakeWheelMotor.set(ControlMode.PercentOutput, 
                intakeWheelsDirection == IntakeWheelsDirection.IN ? Constants.IntakeConstants.kIntakeWheelSpeed : -Constants.IntakeConstants.kIntakeWheelSpeed);
        }
    }
}
