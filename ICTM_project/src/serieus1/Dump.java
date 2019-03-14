package serieus1;


import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class Dump implements Behavior{
	private EV3ColorSensor color1;
	private EV3LargeRegulatedMotor Lift;
	private EV3LargeRegulatedMotor Grab;
	private EV3LargeRegulatedMotor Drive;
	private EV3UltrasonicSensor us1;
	
	public Dump(EV3ColorSensor c1,EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G, EV3LargeRegulatedMotor D,EV3UltrasonicSensor U) {
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



