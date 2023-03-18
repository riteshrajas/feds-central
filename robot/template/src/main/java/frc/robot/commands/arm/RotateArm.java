package frc.robot.commands.arm;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ArmSubsystem4;


public class RotateArm extends SequentialCommandGroup {
    private final ArmSubsystem4 s_arm;
    private final DoubleSupplier rotateDegrees;
    
    public RotateArm(ArmSubsystem4 s_arm, DoubleSupplier rotateDegrees) {
     this.s_arm = s_arm;
     this.rotateDegrees = rotateDegrees;
     addRequirements(s_arm);
    }
}
