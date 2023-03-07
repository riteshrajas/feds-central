package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase{
    private double cameraYaw;
    private double cameraPitch;
    private boolean isTargetLow;
    private double cameraDistanceToTarget;
    private double horizontalDistance;


    public LimelightSubsystem(){

    }

    /*public void setResult(){
        if(NetworkTableInstance.getDefault().getTable("limelight").getEntry("<tv>") == 1){
            cameraYaw = 
        }
    }*/

}
