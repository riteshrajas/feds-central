package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.math.Conversions;
import frc.robot.Constants.ClawConstants;

public class ClawSubsystemWithPID extends SubsystemBase{
    private final int kPIDLoopIdx = 0;
    private final boolean kSensorPhase = true;
    private final double kP = 0.0;
    private final double kI = 0;
    private final double kD = 0.0;
    private final double kF = 0.4;
    private final int kIzone = 0;
    private final double kPeakOutput = 0.40;
    private final int kTimeoutMs = 30;

    private final TalonFX m_clawMotor;

    private final double kOpenClawPosition = -Conversions.degreesToFalcon(40, 25);
    private final double kBallClawPosition = 254;
    private final double kConeClawPosition = 100;

    public ClawSubsystemWithPID(){
        m_clawMotor = new TalonFX(ClawConstants.kClawMotor);

        configMotor(m_clawMotor);
          
    }

    private void configMotor(TalonFX motor) {
        motor.configFactoryDefault();
        motor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, kPIDLoopIdx, kTimeoutMs);

        motor.setSensorPhase(kSensorPhase);

        motor.configNominalOutputForward(0, kTimeoutMs);
        motor.configNominalOutputReverse(0, kTimeoutMs);

        motor.configPeakOutputForward(kPeakOutput, kTimeoutMs);
        motor.configPeakOutputReverse(-kPeakOutput, kTimeoutMs);

        motor.configAllowableClosedloopError(0, kPIDLoopIdx, kTimeoutMs);

        motor.configVoltageCompSaturation(12);
        motor.enableVoltageCompensation(true);
        
        motor.configForwardSoftLimitThreshold(Conversions.degreesToFalcon(70, 25));
        motor.configReverseSoftLimitThreshold(-Conversions.degreesToFalcon(70, 25));
        motor.configForwardSoftLimitEnable(true, 0);
        motor.configReverseSoftLimitEnable(true, 0);

        motor.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
        motor.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
        motor.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
        motor.config_kD(kPIDLoopIdx, kD, kTimeoutMs);

        motor.setSelectedSensorPosition(0);

        motor.configVoltageCompSaturation(12);
        motor.enableVoltageCompensation(true);
    }

    public void openClaw() {
        m_clawMotor.set(TalonFXControlMode.Position, kOpenClawPosition);
    }

    public void kickOutClaw(){
        m_clawMotor.set(TalonFXControlMode.Position, ClawConstants.kKickOutPosition);
    }

    public void holdBall() {
        m_clawMotor.set(TalonFXControlMode.Position, kBallClawPosition);
    }

    public void holdCone() {
        m_clawMotor.set(TalonFXControlMode.Position, kConeClawPosition);
    }

    public void stopClaw() {
        m_clawMotor.set(TalonFXControlMode.PercentOutput, 0);
    }

    public void closeClaw() {
        m_clawMotor.set(TalonFXControlMode.PercentOutput, ClawConstants.kClosePower);
    }

    public double getClawPosition(){
        return m_clawMotor.getSelectedSensorPosition();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Claw Encoder Count", getClawPosition());
    }
}
