package frc.robot.commands.sensor;

import frc.robot.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class StrafeAlign extends CommandBase{   

private final VisionSubsystem VISION_S;

    public StrafeAlign(VisionSubsystem VISION_S, boolean isTargetLow){
        this.VISION_S = new VisionSubsystem(isTargetLow);

        addRequirements(this.VISION_S);
    }

    @Override
    public void initialize(){
        VISION_S.getResult();
        VISION_S.hasTarget();
        VISION_S.setTargets();
        VISION_S.setTarget();
        VISION_S.getTargetDistance();
        VISION_S.getTargetPitch();
        VISION_S.getTargetYaw();
        VISION_S.getHorizontalDistanceToTarget();
    }

    @Override
    public void execute(){
        VISION_S.strafeAlign();
    }

    @Override
    public boolean isFinished(){
        return VISION_S.strafeFinished();
    }

    @Override
    public void end(boolean interrupted){

    }

}