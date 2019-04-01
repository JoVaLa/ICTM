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
		static int colorRep;
	

	
	
	
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
		static boolean vakVol=true;
		static boolean dropBox= false;
		static boolean takeBox=false;
		static boolean dump=false;
		static double range=0.01;
		static Flags flags=new Flags(boxVast,vakVol,dump, dropBox,takeBox);
		
		private static double[] shelf11= {0.2,0.05,0.02};
		static Warehouse rack11=new Warehouse(shelf11,1,5);
		private static double[] shelf21= {0.2,0.05,0.04};
		static Warehouse rack21=new Warehouse(shelf21,1,5);
		private static double[] shelf31= {0.2,0.05,0.06};
		static Warehouse rack31=new Warehouse(shelf31,1,5);
		private static double[] shelf41= {0.2,0.05,0.08};
		static Warehouse rack41=new Warehouse(shelf41,1,5);
		// green column
		private static double[] shelf12= {0.25,0.05,0.02};
		static Warehouse rack12=new Warehouse(shelf12,2,5);
		private static double[] shelf22= {0.25,0.05,0.04};
		static Warehouse rack22=new Warehouse(shelf22,2,5);
		private static double[] shelf32= {0.25,0.05,0.06};
		static Warehouse rack32=new Warehouse(shelf32,2,5);
		private static double[] shelf42= {0.25,0.05,0.08};
		static Warehouse rack42=new Warehouse(shelf42,2,5);
		
		// blue column
		private static double[] shelf13= {0.3,0.05,0.02};
		static Warehouse rack13=new Warehouse(shelf13,3,5);
		private static double[] shelf23= {0.3,0.05,0.04};
		static Warehouse rack23=new Warehouse(shelf23,3,5);
		private static double[] shelf33= {0.3,0.05,0.06};
		static Warehouse rack33=new Warehouse(shelf33,3,5);
		private static double[] shelf43= {0.3,0.05,0.08};
		static Warehouse rack43=new Warehouse(shelf43,3,5);
		// white column
		private static double[] shelf14= {0.34,0.05,0.02};
		static Warehouse rack14=new Warehouse(shelf14,4,5);
		private static double[] shelf24= {0.34,0.05,0.04};
		static Warehouse rack24=new Warehouse(shelf24,4,5);
		private static double[] shelf34= {0.34,0.05,0.06};
		static Warehouse rack34=new Warehouse(shelf34,4,5);
		private static double[] shelf44= {0.34,0.05,0.08};
		static Warehouse rack44=new Warehouse(shelf44,4,5);
		
		
		static ArrayList<Warehouse> posFork=new ArrayList<Warehouse>(16);
		static double[] positionFork=new double[3];
		
		static Position positionUpdater=new Position(usWall,usDump); // we can also just do this: new Position(...).start() in the main method
		
		public static synchronized void makeUpdate(int i,double posNew)
		{
			positionFork[i]=posNew;
			
		}
		
		
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
		
		 
		posFork.add(rack11); posFork.add(rack21);posFork.add(rack31);posFork.add(rack41);
		posFork.add(rack42); posFork.add(rack32);posFork.add(rack22);posFork.add(rack12);
		posFork.add(rack13); posFork.add(rack23);posFork.add(rack33);posFork.add(rack43);
		posFork.add(rack44); posFork.add(rack34);posFork.add(rack24);posFork.add(rack14);
		
		Behavior [] behaviors = new Behavior[5]; //test
		behaviors[0]= new ScanRek();
		behaviors[1]= new BringToRack(flags,boxVast,color1, Lift, Grab, Drive, usWall, usDump);
		behaviors[2]= new GoToRep(flags,vakVol,color1, Lift, Grab, Drive, usWall, usDump);
		
		
		behaviors[3]= new TakeBox();
		behaviors[4]= new DropBox();
		//behaviors[2]= new ScanRek();
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

//import java.util.ArrayList;
//import lejos.hardware.BrickFinder;
//import lejos.hardware.Button;
//import lejos.hardware.Device;
//import lejos.hardware.lcd.LCD;
//import lejos.hardware.port.MotorPort;
//import lejos.hardware.motor.BaseRegulatedMotor;
//import lejos.hardware.motor.EV3LargeRegulatedMotor;
//import lejos.hardware.motor.EV3MediumRegulatedMotor;
//import lejos.hardware.motor.Motor;
//// port A: motor to drive the vehicle
//// port B: motor to lift
//// port C: motor for gear and rack
//
//import lejos.hardware.port.SensorPort; 
//import lejos.hardware.sensor.BaseSensor;
//import lejos.hardware.sensor.UARTSensor;
//import lejos.robotics.SampleProvider;
//import lejos.hardware.sensor.EV3ColorSensor;
//import lejos.hardware.sensor.EV3UltrasonicSensor;
//import lejos.hardware.sensor.SensorMode;
//import lejos.hardware.Button;
//import lejos.hardware.port.SensorPort;
//import lejos.robotics.SampleProvider;
//import lejos.utility.Delay;
//import lejos.robotics.subsumption.*;
//
//public class Main {
//	static EV3LargeRegulatedMotor Drive=new EV3LargeRegulatedMotor(MotorPort.B);
//	static EV3LargeRegulatedMotor Lift=new EV3LargeRegulatedMotor(MotorPort.A);
//	static EV3LargeRegulatedMotor Grab=new EV3LargeRegulatedMotor(MotorPort.C);
//	static EV3ColorSensor color1=new EV3ColorSensor(SensorPort.S1);
//	static EV3UltrasonicSensor usWall=new EV3UltrasonicSensor(SensorPort.S3);
//	static EV3UltrasonicSensor usDump=new EV3UltrasonicSensor(SensorPort.S4);
//	
//	
//	public static void main(String[]args)throws InterruptedException{
//		posFork.add(rack11); posFork.add(rack21);posFork.add(rack31);posFork.add(rack41);
//		posFork.add(rack42); posFork.add(rack32);posFork.add(rack22);posFork.add(rack12);
//		posFork.add(rack13); posFork.add(rack23);posFork.add(rack33);posFork.add(rack43);
//		posFork.add(rack44); posFork.add(rack34);posFork.add(rack24);posFork.add(rack14);
////		Move move= new Move(null, 0, 0, 0, 0, false);
////		maakMap map=new maakMap(0,0,0);
//		positionUpdater.start();
//		
//		
//		Behavior [] behaviors = new Behavior[1];
//		behaviors[0]= new scanRack2();
////		behaviors[1]= new TakeBox(color1, Lift, Grab);
////		behaviors[2]= new DropBox(color1, Lift, Grab);
////		behaviors[3]= new BringToRepository(color1, Lift, Grab, Drive, us1);
////		behaviors[]= new GetFromRep
////		behaviors[]= new FixError
////		behaviors[4]= new BringToRek(color1, Lift, Grab, Drive, us1);
////		behaviors[5]= new Dump(color1, Lift, Grab, Drive, us1);
////		behaviors[6]= new SeeHuman(color1,Drive);
//		
//		
//		Arbitrator a = new Arbitrator (behaviors);
//		LCD.clear();
//		LCD.drawString("duw op een knop om te starten", 1, 1);
//		Button.waitForAnyPress();
//		Thread.sleep(1000);
//		a.go();//let the fun begin
//	}
//
//}

