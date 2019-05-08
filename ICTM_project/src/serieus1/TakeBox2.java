package serieus1;
	import lejos.hardware.port.MotorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
	import lejos.hardware.motor.EV3MediumRegulatedMotor;
	import lejos.robotics.subsumption.Behavior;

public class TakeBox2 implements Behavior{


	boolean suppressed= false;
	
	public boolean takeControl(){
				return (Main.flags.getTakeBox() && !Main.flags.getBoxVast());
				//return true;
	}

	//handeling
	public void action(){
		//LCD.drawString("takebox", 1, 7);
		Main.Drive.stop();
		Main.Grab.setSpeed(200);
		Main.Lift.setSpeed(200);
		Main.Grab.rotate(470);
		Main.Lift.rotate(-180);
		Main.Grab.rotate(-470);
		Main.Lift.rotate(180);
		//Main.Lift.rotate(-180); //om geen hoogteverschil te hebben met beginpositie
		Main.flags.setBoxVast(true);
		Main.flags.setTakeBox(false);
		Main.flags.setBtr(true);
	}
	//uitstap
	public void suppress(){
		suppressed = true;
		
	}

}






//package serieus1;
//
//import lejos.hardware.motor.EV3LargeRegulatedMotor;
//import lejos.hardware.sensor.EV3ColorSensor;
//import lejos.robotics.subsumption.Behavior;
//
//public class TakeBox implements Behavior{
//	private EV3ColorSensor color1;
//	private EV3LargeRegulatedMotor Lift;
//	private EV3LargeRegulatedMotor Grab;
//	private EV3LargeRegulatedMotor Drive;
//	boolean vakVol=true;
//	
//
//	public TakeBox(EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G) {
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
