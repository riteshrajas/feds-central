package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.List;

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
    private double objectArea;
    private double distance;
    PhotonTrackedTarget target;
    List<PhotonTrackedTarget> targets;
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

    public void setTargets(){
        if(hasTarget()){
            targets = result.getTargets();
        }
    }

    public void setTarget(){
        if(hasTarget()){
            target = result.getBestTarget();
        }
    }

    public double getYaw(){
        cameraYaw = target.getYaw();
        return cameraYaw;
    }

    public double getPitch(){
        cameraPitch = target.getPitch();
        return cameraPitch;
    }

    public double getDistance(){
        objectArea = target.getArea();
        return objectArea;
    }

    public void periodic(){
        
    }

    public void strafeAlign(){

    }

    
}
