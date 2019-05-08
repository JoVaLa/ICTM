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
	double range=0.005;
	
	boolean suppressed= false;
	
	public boolean takeControl(){
		return Main.flags.dump;
	}
	//handeling
	public void action(){
		//LCD.clear();
		//LCD.drawString("DUMP", 1, 1);
		suppressed=false;
		double[] onderKol={Main.positionFork[0],0,0.025};
		Main.moveFork(Main.positionFork, onderKol);
		while(!suppressed) {
		if((Math.abs(Main.positionFork[0]-Main.rack11.getCoordinates()[0])>range)&&(Main.positionFork[0]-Main.rack11.getCoordinates()[0]<0)) {
        	Main.Drive.backward();
        	
        }
    	else if((Math.abs(Main.positionFork[0]-Main.rack11.getCoordinates()[0])>range)&&(Main.positionFork[0]-Main.rack11.getCoordinates()[0]>0)) {
        	Main.Drive.forward();
        }
    	else {
        	Main.Drive.stop();
        	Main.flags.setDropBox(true);
        	Main.flags.setDump(false);
        	suppressed=true;
    	}
		}
		
		
		
	}
	//uitstap
	public void suppress(){
		this.suppress();
		
	}
}



