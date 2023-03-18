/*package frc.robot.commands.oldarm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class WaitUntilFullyRotate extends CommandBase {
    private final ArmSubsystem s_arm;
    public WaitUntilFullyRotate(ArmSubsystem arm) {
        this.s_arm = arm;

        // addRequirements(s_arm); // we don't want this to stop the set arm command
                                   // does it necessarily need to be a command? Idk
    } 


    public boolean isFinished() {
        return s_arm.getArmDoneRotating();
    }
}
*/