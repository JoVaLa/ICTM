package serieus1;

import lejos.robotics.subsumption.*;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;

public class ScanRek implements Behavior{
	private EV3ColorSensor color1;
	private EV3LargeRegulatedMotor Lift;
	private EV3LargeRegulatedMotor Grab;
	private EV3LargeRegulatedMotor Drive;
	boolean vakVol=true;
	
	public ScanRek(EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G) {
		// TODO Auto-generated constructor stub
	}
	
	//de IF
	public boolean takeControl(){
		return true;
	}
	//handeling
	public void action(){
		for(int i=1;i<=16&&vakVol;i++) {
			
		}

	}
	//uitstap
	public void suppress(){
		this.suppress();
		
	}
	


}
