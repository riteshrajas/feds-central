package frc.robot.subsystems;

// Removed VelocityVoltage usage; using simple motor set calls instead
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2  ;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
public class RollersSubsystem extends SubsystemBase {

  private static RollersSubsystem instance;
  private RollerState currentState = RollerState.OFF;
  private final TalonFX motor;
  private final LinearSystem<N2, N1, N2> plant;
private final DCMotorSim motorSim;

  public static RollersSubsystem getInstance() { 
  if (instance == null) {
      instance = new RollersSubsystem();
    }
    return instance;
  }

  public enum RollerState {
    ON, OFF
  }

  private RollersSubsystem() {
    motor = new TalonFX(1, "rio");
    plant = LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60(1), 0.01, 0.01);
    motorSim = new DCMotorSim(plant, DCMotor.getKrakenX60(1), 0.1);
    
  }

  public void setState(RollerState targetState) {
    this.currentState = targetState;
    switch (targetState) {
      case ON:
        motor.set(0.5);
        motorSim.setInput(0.5);
        break;
      case OFF:
        motor.stopMotor();
        motorSim.setInput(0.0);
        break;
    }
  }

  
  
  public RollerState getState() {
    return this.currentState;
  }


  public Command RollersCommand(RollerState desiredState) {
      return run(() -> setState(desiredState));
  }

  @Override
  public void periodic() {
    motor.getSimState().setRawRotorPosition(motorSim.getAngularPosition());
    motorSim.update(0.02);
    super.periodic();
  }
}
