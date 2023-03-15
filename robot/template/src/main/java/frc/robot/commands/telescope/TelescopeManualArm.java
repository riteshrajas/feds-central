package frc.robot.commands.telescope;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.TelescopeConstants;
import frc.robot.subsystems.TelescopeSubsystem;

public class TelescopeManualArm extends CommandBase {

    private final TelescopeSubsystem m_telescopingSubsystem;
    private final DoubleSupplier m_input;

    public TelescopeManualArm(TelescopeSubsystem telescopeSubsystem, DoubleSupplier input) {
        m_telescopingSubsystem = telescopeSubsystem;
        m_input = input;
        addRequirements(telescopeSubsystem);
    }

    @Override
    public void initialize() { 
    }

    @Override
    public void execute() {
        double plant = m_input.getAsDouble();
        if(plant > 0) {
            m_telescopingSubsystem.manuallyMove(-plant * TelescopeConstants.kManualSpeedOut);  // negative makes up go out and down go in for the left stick y value
        } else {
            m_telescopingSubsystem.manuallyMove(-plant * TelescopeConstants.kManualSpeedIn);
        }
        
    }
}
