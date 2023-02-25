package frc.robot.commands.sensor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;

public class RotateAlign extends CommandBase{

    private final VisionSubsystem VISION_S;

    public RotateAlign(VisionSubsystem VISION_S, boolean isTargetLow){
        VISION_S = new VisionSubsystem(isTargetLow);
        this.VISION_S = VISION_S;


        addRequirements(VISION_S);

    }

    @Override
    public void initialize(){
        VISION_S.getResult();
        VISION_S.setTargets();
        VISION_S.setTarget();
    }

    @Override
    public void execute(){
       
        VISION_S.getTargetYaw();
        //VISION_S.getHorizontalDistanceToTarget();
        VISION_S.rotateToTarget();

    }

    @Override
    public boolean isFinished(){
        return VISION_S.rotateFinished();
    }

    @Override
    public void end(boolean interrupted){

    }

}
