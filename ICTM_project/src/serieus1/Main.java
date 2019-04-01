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
		static EV3LargeRegulatedMotor Drive=new EV3LargeRegulatedMotor(MotorPort.B);
		 //Drive.setSpeed(80);
		static EV3LargeRegulatedMotor Lift=new EV3LargeRegulatedMotor(MotorPort.A);
		 //Lift.setSpeed(20);
		static EV3LargeRegulatedMotor Grab=new EV3LargeRegulatedMotor(MotorPort.C);
		 //Grab.setSpeed(10);
		static EV3ColorSensor color1=new EV3ColorSensor(SensorPort.S1);
		static EV3UltrasonicSensor usWall=new EV3UltrasonicSensor(SensorPort.S3);
		static EV3UltrasonicSensor usDump=new EV3UltrasonicSensor(SensorPort.S4);
		static boolean boxVast= false;
		static boolean vakVol=false;
		static boolean dropBox= false;
		static boolean takeBox=false;
		static boolean dump=false;
		static double range=0.01;
		static Flags flags=new Flags(boxVast,vakVol,dump, dropBox,takeBox);
		
		public void goToDumpDist(double afst) {
			while((this.getDumpDist()-afst)>Main.range ) {
	        	Main.Drive.forward();
	        }
	    	while((this.getDumpDist()-afst)<0 ) {
	        	Main.Drive.backward();
	        }
	        {
	        	Main.Drive.stop();
	        	//this.vakVol=false;
	        	
	        	
	        }
		}
		public void goToWallDist(double afst) {
			while((this.getWallDist()-afst)>Main.range ) {
	        	Main.Drive.backward();
	        }
	    	while((this.getWallDist()-afst)<0 ) {
	        	Main.Drive.forward();
	        }
	        {
	        	Main.Drive.stop();
	        	//this.vakVol=false;
	        	
	        	
	        }
		}
		public  double getWallDist() {
		 	SampleProvider spWall = Main.usWall.getDistanceMode();
	     	float [] sampleWall = new float[spWall.sampleSize()];
	        spWall.fetchSample(sampleWall, 0);
	        double distanceWall = (float)sampleWall[0];
	        return distanceWall;
	        }
		 
		public  double getDumpDist() {
			SampleProvider spDump = Main.usDump.getDistanceMode();
			float [] sampleDump = new float[spDump.sampleSize()];
	        spDump.fetchSample(sampleDump, 0);
	        double distanceDump = (float)sampleDump[0];
		    return distanceDump;
		    }
		
		
		
	public static void main(String[]args)throws InterruptedException{
		
		 
		
		
		Behavior [] behaviors = new Behavior[4];
		behaviors[0]= new BringToRack(flags,boxVast,color1, Lift, Grab, Drive, usWall, usDump);
		behaviors[1]= new GoToRep(flags,vakVol,color1, Lift, Grab, Drive, usWall, usDump);
		
//		behaviors[0]= new ScanRek(color1, Lift, Grab);
		behaviors[2]= new TakeBox();
		behaviors[3]= new DropBox();
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
