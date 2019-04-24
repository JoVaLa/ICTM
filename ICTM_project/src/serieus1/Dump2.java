package serieus1;


import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class Dump2 implements Behavior{
	private EV3ColorSensor color1;
	private EV3LargeRegulatedMotor Lift;
	private EV3LargeRegulatedMotor Grab;
	private EV3LargeRegulatedMotor Drive;
	private EV3UltrasonicSensor us1;
	
	
	
	
	public boolean takeControl(){
		return Main.flags.dump;
	}
	//handeling
	public void action(){
		LCD.clear();
		LCD.drawString("DUMP", 1, 1);
		Main.goToInitialState();
		Main.goToDumpDist(0.1);
		Main.flags.setDropBox(true);
		Main.flags.setDump(false);
	}
	//uitstap
	public void suppress(){
		this.suppress();
		
	}
}



