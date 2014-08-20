package com.muhavision.control;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.NavData;
import com.codeminders.ardrone.NavDataListener;
import com.codeminders.ardrone.util.BufferedImageVideoListener;
import com.muhavision.cv.OpticalFlowCalculator;
import com.muhavision.cv.image.VisualRenderer;
import com.muhavision.pid.PID;

public class DroneController {
	
	PID roll = new PID(1, 1, 0, 10, PID.Direction.NORMAL);
	
	OpticalFlowCalculator calc = new OpticalFlowCalculator();
	
	ARDrone drone = null;
	
	public DroneController(final VisualRenderer visual) {
		System.out.println("Drone controller loading...");
		try {
			
			drone = new ARDrone();
			drone.connect();

			drone.addImageListener(new BufferedImageVideoListener() {
				
				@Override
				public void imageReceived(BufferedImage image) {
					/*try {
						drone.sendAllNavigationData();
					} catch (IOException e) {
						e.printStackTrace();
					}*/
					visual.reloadDatas(image);
					//calc.getFlowData(image);
				}
			});
			
			drone.addNavDataListener(new NavDataListener() {
				
				@Override
				public void navDataReceived(NavData data) {
					
				}
			});
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ARDrone getDrone(){
		return drone;
	}

}