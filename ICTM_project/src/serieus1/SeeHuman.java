package serieus1;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class SeeHuman implements Behavior{
	private EV3ColorSensor color1;
	private EV3LargeRegulatedMotor Lift;
	private EV3LargeRegulatedMotor Grab;
	private EV3LargeRegulatedMotor Drive;
	private EV3UltrasonicSensor us1;
	
	float distance1;
	float distance2;
	
	
	
	
	
	public SeeHuman(EV3ColorSensor c1, EV3LargeRegulatedMotor D) {
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean takeControl(){
		return (distance1<0.1 &&distance2<0.1);
	}
	//handeling
	public void action(){
		
	}
	//uitstap
	public void suppress(){
		this.suppress();
		
	}
}
