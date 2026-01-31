// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.climber;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.DeviceTempReporter;
import frc.robot.utils.SubsystemStatusManager;

public class Climber extends SubsystemBase {
  private final TalonFX sampleMotor;
  private TalonFXConfiguration config;
  private turret_state currentState;

  public enum turret_state {
    STOP(Rotations.of(0)),
    START(Rotations.of(0)),
    HOME(Rotations.of(0));

    private final Angle targetPosition;
    private final Angle tolerance;

    turret_state(Angle targetPosition){
      this.targetPosition = targetPosition;
      this.tolerance = Degrees.of(3);

    }

    public Angle getTargetPosition(){
      return targetPosition;
    }

    public Angle getTolerance(){
      return tolerance;
    }
  }

  public Climber() {
    currentState = turret_state.HOME;
    sampleMotor = new TalonFX(1);

    config = new TalonFXConfiguration();
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    config.CurrentLimits.StatorCurrentLimit = 40;
    // Apply config multiple times to ensure application
    for (int i = 0; i < 2; ++i){
      var status = sampleMotor.getConfigurator().apply(config);
      if(status.isOK()) break;
    }

    SubsystemStatusManager.addSubsystem(getName(), sampleMotor);
    DeviceTempReporter.addDevices(sampleMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    super.periodic();
  }

  public Angle getPosition(){
    return sampleMotor.getPosition().getValue();
  }

  public Angle getTargetPosition(){
    return currentState.getTargetPosition();
  }

  public Angle getTolerance(){
    return currentState.getTolerance();
  }

  public void stop(){
    sampleMotor.stopMotor();
  }

  public turret_state getCurrentState(){
    return currentState;
  }

  public boolean isAtTarget(){
    return getPosition().isNear(getTargetPosition(), getTolerance());
  }
  




}

