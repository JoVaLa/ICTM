package serieus1;

import java.util.ArrayList;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Device;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
// port A: motor to drive the vehicle
// port B: motor to lift
// port C: motor for gear and rack

import lejos.hardware.port.SensorPort; 
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.UARTSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.robotics.subsumption.*;

public class Main {
	
	public static void main(String[]args)throws InterruptedException{
		 EV3LargeRegulatedMotor Drive=new EV3LargeRegulatedMotor(MotorPort.B);
		 Drive.setSpeed(80);
		 EV3LargeRegulatedMotor Lift=new EV3LargeRegulatedMotor(MotorPort.A);
		 Lift.setSpeed(20);
		 EV3LargeRegulatedMotor Grab=new EV3LargeRegulatedMotor(MotorPort.C);
		 Grab.setSpeed(10);
		 EV3ColorSensor color1=new EV3ColorSensor(SensorPort.S1);
		 EV3UltrasonicSensor usWall=new EV3UltrasonicSensor(SensorPort.S3);
		 EV3UltrasonicSensor usDump=new EV3UltrasonicSensor(SensorPort.S4);
		 boolean boxVast= false;
		 boolean vakVol=false;
		 boolean dump = false;
		 boolean dropBox = false;
		 boolean takeBox;
		 Flags flags=new Flags(boxVast,vakVol,dump,dropBox,takeBox);
		 
		
		
		Behavior [] behaviors = new Behavior[2];
		behaviors[0]= new BringToRack(flags,boxVast,color1, Lift, Grab, Drive, usWall, usDump);
		behaviors[1]= new GoToRep(flags,vakVol,color1, Lift, Grab, Drive, usWall, usDump);
//		behaviors[0]= new ScanRek(color1, Lift, Grab);
//		behaviors[1]= new TakeBox(color1, Lift, Grab);
//		behaviors[2]= new DropBox(color1, Lift, Grab);
////		behaviors[3]= new BringToRepository(color1, Lift, Grab, Drive, us1);
////		behaviors[]= new GetFromRep
////		behaviors[]= new FixError
//		behaviors[4]= new BringToRek(color1, Lift, Grab, Drive, usWall, usDump);
//		behaviors[5]= new Dump(color1, Lift, Grab, Drive, usDump);
//		behaviors[6]= new SeeHuman(color1,Drive);
		
		
		Arbitrator a = new Arbitrator (behaviors);
		LCD.clear();
		LCD.drawString("duw op een knop om te starten", 1, 1);
		Button.waitForAnyPress();
		Thread.sleep(1000);
		LCD.clear();
		a.go();//let the fun begin
	}

}
