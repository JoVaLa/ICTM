package serieus1;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Behavior;

public class TakeBox implements Behavior{
	private EV3ColorSensor color1;
	private EV3LargeRegulatedMotor Lift;
	private EV3LargeRegulatedMotor Grab;
	private EV3LargeRegulatedMotor Drive;
	boolean vakVol=true;
	

	public TakeBox(EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G) {
		// TODO Auto-generated constructor stub
	}
	public boolean takeControl(){
		return true;
	}
	//handeling
	public void action(){
		
	}
	//uitstap
	public void suppress(){
		this.suppress();
		
	}

}
