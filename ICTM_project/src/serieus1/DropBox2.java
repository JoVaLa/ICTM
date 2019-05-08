package serieus1;
import lejos.hardware.port.MotorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class DropBox2 implements Behavior {
	
	boolean suppressed= false;
	
	public boolean takeControl(){
		return (Main.flags.getBoxVast() && Main.flags.getDropBox());
//		return true;
	}
	//handeling
		public void action(){
			//LCD.drawString("dropbox", 1, 1);
			Main.Drive.stop();
			Main.Grab.setSpeed(200);
			Main.Lift.setSpeed(200);
			//Main.Lift.rotate(-180);
			Main.Grab.rotate(470);
			Main.Lift.rotate(180);
			Main.Grab.rotate(-470);
			Main.Lift.rotate(-180);
			if(Main.flags.getDumb()==true) {
				Main.flags.setDump(false);
			}
			else if(Main.flags.getVakVol()==false) {
				Main.flags.setVakVol(true);
			}
			Main.flags.setBoxVast(false);
			Main.flags.setDropBox(false);
			
			
			
		}
		//uitstap
		public void suppress(){
			
			suppressed=true;
		}
	
}

//package serieus1;
//
//import lejos.hardware.motor.EV3LargeRegulatedMotor;
//import lejos.hardware.sensor.EV3ColorSensor;
//import lejos.robotics.subsumption.Behavior;
//
//public class DropBox implements Behavior{
//	private EV3ColorSensor color1;
//	private EV3LargeRegulatedMotor Lift;
//	private EV3LargeRegulatedMotor Grab;
//	private EV3LargeRegulatedMotor Drive;
//	boolean vakVol=true;
//	
//
//	public DropBox(EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G) {
//		// TODO Auto-generated constructor stub
//	}
//	public boolean takeControl(){
//		return true;
//	}
//	//handeling
//	public void action(){
//		
//	}
//	//uitstap
//	public void suppress(){
//		this.suppress();
//		
//	}
//
//}