package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class IntakeSubsystem extends SubsystemBase {

  private final TalonFX motor;
  private final DigitalInput limit_switch_r;
  private final NetworkTable limelight;
  private final DigitalInput limit_switch_l;

  public enum IntakeState {
    EXTENDED, 
    DEFAULT
  }
  private IntakeState currentState = IntakeState.DEFAULT;
  private IntakeState targetState = IntakeState.DEFAULT;

  public void setState(IntakeState targetState) { // -> Extended
    this.currentState = targetState;

    


  }

  private void extendIntake(){
    if (limit_switch_l.get() == true) { //-> If the limit switch is pressed, extend the intake
      motor.set(0.1);
    } else {
      stopmotor();
    }
  }

  private void retractIntake() {
    if (limit_switch_l.get() == false) {
      motor.set(-0.1);
    }
      else {
        stopmotor(); 
      }

    }

  

  public IntakeState getState() {
    return this.currentState;
  }

  public Command setIntakeStateCommand(IntakeState targState){ // -> Extended 
    return run(() -> setState(targState)); // -> Extended
  }



  public IntakeSubsystem() {
    motor = new TalonFX(RobotMap.IntakeSubsystemConstants.kMotorID, "rio");
    limit_switch_r = new DigitalInput(RobotMap.IntakeSubsystemConstants.kLimit_switch_rID);
    limelight = NetworkTableInstance.getDefault().getTable("limelight");
    limit_switch_l = new DigitalInput(RobotMap.IntakeSubsystemConstants.kLimit_switch_lID);
  }

  @Override
  public void periodic() {

    switch(targetState) { // -> Extended
      case EXTENDED: extendIntake();
        break;
      
        
      case DEFAULT: retractIntake();
      break; 
    }

    System.out.println("Intake State: " + targetState); // -> Extended

    
    super.periodic();
  }



  public void stopmotor() {
    motor.stopMotor();
  }

  public double getmotorVelocity() {
    return motor.getVelocity().getValue().in(Units.RotationsPerSecond);
  }
}

