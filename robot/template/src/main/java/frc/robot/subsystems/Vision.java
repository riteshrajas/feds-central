package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import javax.xml.transform.Result;

import org.photonvision.*;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;


public class Vision extends SubsystemBase{

    private PhotonCamera camera;
    private double cameraYaw;
    private double cameraPitch;
    private PhotonPipelineResult result;
    private double cameraRoll;
    private double distance;
    //List<PhotonTrackedTarget> target = result.getTargets();
    public Vision(){
        camera = new PhotonCamera("limelightCamera");
        PhotonTrackedTarget target = new PhotonTrackedTarget();
    }

    public void getResult(){
        result = camera.getLatestResult();
    }

    public boolean hasTarget(){
        return result.hasTargets();
    }

    /*public double [] getTarget(){
        return target;
    }*/

    public double getYaw(){
        return cameraYaw;
    }

    public double getPitch(){
        return cameraPitch;
    }

    public double getDistance(){
        return distance;
    }

    public void periodic(){
        
    }

    public void strafeAlign(){

    }

    
}
