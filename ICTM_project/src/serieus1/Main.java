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
		static EV3LargeRegulatedMotor Drive=new EV3LargeRegulatedMotor(MotorPort.D);
		static EV3LargeRegulatedMotor Lift=new EV3LargeRegulatedMotor(MotorPort.A);
		static EV3LargeRegulatedMotor Grab=new EV3LargeRegulatedMotor(MotorPort.C);
		static EV3ColorSensor color1=new EV3ColorSensor(SensorPort.S4);
		static EV3UltrasonicSensor usWall=new EV3UltrasonicSensor(SensorPort.S1);
		static EV3UltrasonicSensor usDump=new EV3UltrasonicSensor(SensorPort.S3);
		static boolean boxVast= false;
		static boolean vakVol=true;
		static boolean dropBox= false;
		static boolean takeBox=false;
		static boolean dump=false;
		static boolean human=false;
		static boolean error=false;
		static boolean sensor=true;
		static double range=0.01;
		static Flags2 flags=new Flags2(boxVast,vakVol,dump, dropBox,takeBox,human,error,sensor);
		// dump
		private static double[] shelf11= {0.0,0.05,0.00};
		static Warehouse rack11=new Warehouse(shelf11,1,5);
		private static double[] shelf21= {0.0,0.05,0.05};
		static Warehouse rack21=new Warehouse(shelf21,1,5);
		private static double[] shelf31= {0.0,0.05,0.10};
		static Warehouse rack31=new Warehouse(shelf31,1,5);
		private static double[] shelf41= {0.0,0.05,0.15};
		static Warehouse rack41=new Warehouse(shelf41,1,5);
		// red column
		private static double[] shelf12= {0.1,0.05,0.00};
		static Warehouse rack12=new Warehouse(shelf12,1,5);
		private static double[] shelf22= {0.1,0.05,0.05};
		static Warehouse rack22=new Warehouse(shelf22,1,5);
		private static double[] shelf32= {0.1,0.05,0.10};
		static Warehouse rack32=new Warehouse(shelf32,1,5);
		private static double[] shelf42= {0.1,0.05,0.15};
		static Warehouse rack42=new Warehouse(shelf42,1,5);
		
		// green column
		private static double[] shelf13= {0.2,0.05,0.00};
		static Warehouse rack13=new Warehouse(shelf13,2,5);
		private static double[] shelf23= {0.2,0.05,0.05};
		static Warehouse rack23=new Warehouse(shelf23,2,5);
		private static double[] shelf33= {0.2,0.05,0.10};
		static Warehouse rack33=new Warehouse(shelf33,2,5);
		private static double[] shelf43= {0.2,0.05,0.15};
		static Warehouse rack43=new Warehouse(shelf43,2,5);
		// blue column
		private static double[] shelf14= {0.3,0.05,0.00};
		static Warehouse rack14=new Warehouse(shelf14,3,5);
		private static double[] shelf24= {0.3,0.05,0.05};
		static Warehouse rack24=new Warehouse(shelf24,3,5);
		private static double[] shelf34= {0.3,0.05,0.10};
		static Warehouse rack34=new Warehouse(shelf34,3,5);
		private static double[] shelf44= {0.3,0.05,0.15};
		static Warehouse rack44=new Warehouse(shelf44,3,5);
		
		
		static ArrayList<Warehouse> posFork=new ArrayList<Warehouse>(13);
		static String currentColor="";
		static int[] emptyOrMistake=new int[13];
		static int[] boxCounter=new int[3];
		static double[] positionFork=new double[3];
		
		static Position2 positionUpdater=new Position2(usWall,usDump); // we can also just do this: new Position(...).start() in the main method
		
		public static synchronized void makeUpdate(int i,double posNew)
		{
			positionFork[i]=posNew;
			
		}
		
		public static void moveFork(double[] posOld,double[] posNew)
		{
			//LCD.drawString("in movefork", 1, 1);
			double posXold=posOld[0];
			double posZold=posOld[2];
			double posXnew=posNew[0];
			double posZnew=posNew[2];
			// change x-position
			// measure the conversion from degrees to height! Now I chose a hypothetical conversion of 5 cm per 360 degree for driving and 1 cm per 360 degrees for lifting
			double rotationDrive=(posXnew-posXold)*2000;
			double rotationLift=(posZnew-posZold)*10000;

			Main.Drive.rotate(-(int)rotationDrive);
			Main.Lift.rotate(-(int)rotationLift);
			Main.makeUpdate(0,posXnew);
			Main.makeUpdate(2,posZnew);
			//LCD.clear();
		}
		
		public static void goToDumpDist(double afst) {
			while((getDumpDist()-afst)>Main.range ) {
	        	Main.Drive.forward();
	        }
	    	while((getDumpDist()-afst)<0 ) {
	        	Main.Drive.backward();
	        }
	        {
	        	Main.Drive.stop();
	        	//this.vakVol=false;
	        	
	        	
	        }
		}
		public static void goToWallDist(double afst) {
			while((getWallDist()-afst)>Main.range ) {
	        	Main.Drive.backward();
	        }
	    	while((getWallDist()-afst)<0 ) {
	        	Main.Drive.forward();
	        }
	        {
	        	Main.Drive.stop();
	        	//this.vakVol=false;
	        	
	        	
	        }
		}
		public static double getWallDist() {
		 	SampleProvider spWall = Main.usWall.getDistanceMode();
	     	float [] sampleWall = new float[spWall.sampleSize()];
	        spWall.fetchSample(sampleWall, 0);
	        double distanceWall = (float)sampleWall[0];
	        return distanceWall;
	        }

	
		 
		public static double getDumpDist() {
			SampleProvider spDump = Main.usDump.getDistanceMode();
			float [] sampleDump = new float[spDump.sampleSize()];
	        spDump.fetchSample(sampleDump, 0);
	        double distanceDump = (float)sampleDump[0];
		    return distanceDump;
		    }
		public static void goToInitialState()
		{
			
			double rotationDrive=(Main.rack12.coordinates[0]-Main.positionFork[0])*2000;
			double rotationLift=(Main.rack12.coordinates[2]-Main.positionFork[2])*10000;
			Main.Drive.rotate(-(int)rotationDrive);
			Main.Lift.rotate(-(int)rotationLift);
			Main.makeUpdate(0,Main.rack12.coordinates[0]);
			Main.makeUpdate(2,Main.rack12.coordinates[2]);
		}
		
		
		
	public static void main(String[]args)throws InterruptedException{
		Main.Drive.setSpeed(200);
		Main.Lift.setSpeed(300);
		Main.Grab.setSpeed(300);
		positionFork[2]=0;
		//Main.goToDumpDist(0.2);
		//positionFork[0]=0.1;
		positionUpdater.start();
		//color1.setCurrentMode("RGB");
		//color1.setFloodlight(6);
		 
		posFork.add(rack12); posFork.add(rack22);posFork.add(rack32);posFork.add(rack42);
		posFork.add(rack43); posFork.add(rack33);posFork.add(rack23);posFork.add(rack13);
		posFork.add(rack14); posFork.add(rack24);posFork.add(rack34);posFork.add(rack44);
		posFork.add(rack11);
	
		
		
		Behavior [] behaviors = new Behavior[6]; //test
		
		behaviors[0]= new ScanRack4();
		behaviors[1]= new BringToRack3(flags,boxVast,color1, Lift, Grab, Drive, usWall, usDump);
		behaviors[2]= new GoToRepository3(flags,vakVol,color1, Lift, Grab, Drive, usWall, usDump);
		behaviors[3]= new Dump2();
		behaviors[4]= new TakeBox2();
		behaviors[5]= new DropBox2();
		//behaviors[6]= new SeeHuman3();
		
		//behaviors[2]= new ScanRek();
////		behaviors[3]= new BringToRepository(color1, Lift, Grab, Drive, us1);
////		behaviors[]= new GetFromRep
////		behaviors[]= new FixError
//		behaviors[4]= new BringToRek(color1, Lift, Grab, Drive, usWall, usDump);
//		behaviors[5]= new Dump(color1, Lift, Grab, Drive, usDump);
//		behaviors[6]= new SeeHuman(color1,Drive);
		
		
		Arbitrator a = new Arbitrator (behaviors);
		LCD.clearDisplay();
		//LCD.drawString("duw op een knop om te starten", 1, 1);
		//Button.waitForAnyPress();
		//Thread.sleep(1000);
		LCD.clear();
		a.go();//let the fun begin
	}
	
	
	
	
	
	
	
	
}

