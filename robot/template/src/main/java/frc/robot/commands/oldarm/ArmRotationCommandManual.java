/*package frc.robot.commands.oldarm;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.OIConstants;
import frc.robot.subsystems.ArmSubsystem3;

public class ArmRotationCommandManual extends CommandBase {
    /** Creates a new ArmControl. 
    private DoubleSupplier m_rotationSupplier;
    
    private ArmSubsystem3 m_arm;

    public ArmRotationCommandManual(ArmSubsystem3 arm, DoubleSupplier rotationSupplier) {
        m_arm = arm;
        addRequirements(arm);
        
        m_rotationSupplier = rotationSupplier;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double rotatePercent = -modifyAxis(m_rotationSupplier.getAsDouble(), 2) / 2;
        m_arm.rotate(rotatePercent);
    }
    
    private static double modifyAxis(double value, int exponent) {
        // Deadband
        value = MathUtil.applyDeadband(value, OIConstants.kDriverDeadzone);

        value = Math.copySign(Math.pow(value, exponent), value);

        return value;
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
    
}
*/