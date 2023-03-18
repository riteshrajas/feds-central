package frc.robot.commands.arm;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem4;

public class RotateArmPosition extends CommandBase {
    private final double m_angleRadians;
    private final ArmSubsystem4 s_arm;

    public RotateArmPosition(ArmSubsystem4 s_arm, double angleRadians) {
        this.s_arm = s_arm;
        addRequirements(s_arm);

        this.m_angleRadians = angleRadians;
    }

    
    @Override
    public void initialize() {
        s_arm.getRotationPIDController().reset();
    }

    @Override
    public void execute() {
        PIDController rotationController = s_arm.getRotationPIDController();
        rotationController.setSetpoint(m_angleRadians);
        s_arm.rotateClosedLoop(rotationController.calculate(s_arm.getArmAngleRadians()));
    }

    @Override
    public void end(boolean interrupted) {
        s_arm.rotateClosedLoop(0);
    }

    @Override
    public boolean isFinished() {
        return s_arm.getRotationPIDController().atSetpoint();
    }
}
