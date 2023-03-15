package frc.robot.commands.drive;

import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.SwerveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class PreciseSwerve extends CommandBase {    
    private SwerveSubsystem s_Swerve;    
    private double translationSup;
    //private double strafeSup;
    private double rotationSup;
    private BooleanSupplier robotCentricSup;

    public PreciseSwerve(SwerveSubsystem s_Swerve, double translationSup,double rotationSup, BooleanSupplier robotCentricSup) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        //this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband*/

        //SlewRateLimiter translationLimiter = new SlewRateLimiter(0.5);
        //SlewRateLimiter strafeLimiter = new SlewRateLimiter(0.5);
        /* Drive */
        s_Swerve.setModuleStates(
                      new SwerveModuleState[] {
                          new SwerveModuleState(translationSup, Rotation2d.fromDegrees(rotationSup)),
                          new SwerveModuleState(translationSup, Rotation2d.fromDegrees(rotationSup)),
                          new SwerveModuleState(translationSup, Rotation2d.fromDegrees(rotationSup)),
                          new SwerveModuleState(translationSup, Rotation2d.fromDegrees(rotationSup))
                      });
    }
}


