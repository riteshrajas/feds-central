package frc.robot.commands.arm;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.ArmSubsystem4;

public class RotateArmManual extends CommandBase {
    private final ArmSubsystem4 s_arm;
    private final DoubleSupplier rotatePowerSupplier;

    public RotateArmManual(ArmSubsystem4 s_arm, DoubleSupplier rotatePowerSupplier) {
        this.s_arm = s_arm;
        this.rotatePowerSupplier = rotatePowerSupplier;
        addRequirements(s_arm);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double power = rotatePowerSupplier.getAsDouble();
        power = MathUtil.applyDeadband(power, OIConstants.kDriverDeadzone);
        power = Math.copySign(Math.pow(power, 2), power);
        power /= 2;
        s_arm.rotate(power * ArmConstants.kArmManualLimiter);
    }

}
