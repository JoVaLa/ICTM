package serieus1;

import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;



public class BringToRack3 implements Behavior{
	//private Flags flags;kkk
//	private  EV3ColorSensor color1;
//	private  EV3LargeRegulatedMotor Lift;
//	private  EV3LargeRegulatedMotor Grab;
//	private  EV3LargeRegulatedMotor Drive;
//	private  EV3UltrasonicSensor usWall;
//	private  EV3UltrasonicSensor usDump;
//	public boolean boxVast;
	
	
	public boolean vakVol=true;
	private boolean fromRep=true;
	private boolean fromFix=false;
//	private double distanceWall=100;//continue meetwaarde
//	private double distanceDump=100;//continue meetwaarde
//	private int teller=0;
//	private double beginPos = 0;//positie waar 'BringtoRek' is opgeroepen
//	private double startPos=0.3;//vaste afstand van dump tot startpositie rek (vakje 1 vd 16)
	private double range=0.01;
//	float wallPosition=(float)0.20;
//	char position;
//	double distRtoRack=0.3;//angle ingeven
//	double distGtoRack=0.25;
//	double distBtoRack=0.2;
//	double distWtoRack=0.15;
//	static double[] positionFork;
	// red column
/*	private static double[] shelf11= {0.2,0.05,0.05};
	static Warehouse rack11=new Warehouse(shelf11,1,5);
	private static double[] shelf21= {0.2,0.05,0.1};
	static Warehouse rack21=new Warehouse(shelf21,1,5);
	private static double[] shelf31= {0.2,0.05,0.15};
	static Warehouse rack31=new Warehouse(shelf31,1,5);
	private static double[] shelf41= {0.2,0.05,0.2};
	static Warehouse rack41=new Warehouse(shelf41,1,5);
	// green column
	private static double[] shelf12= {0.3,0.05,0.05};
	static Warehouse rack12=new Warehouse(shelf12,2,5);
	private static double[] shelf22= {0.3,0.05,0.1};
	static Warehouse rack22=new Warehouse(shelf22,2,5);
	private static double[] shelf32= {0.3,0.05,0.15};
	static Warehouse rack32=new Warehouse(shelf32,2,5);
	private static double[] shelf42= {0.3,0.05,0.2};
	static Warehouse rack42=new Warehouse(shelf42,2,5);
	
	// blue column
	private static double[] shelf13= {0.4,0.05,0.05};
	static Warehouse rack13=new Warehouse(shelf13,3,5);
	private static double[] shelf23= {0.4,0.05,0.1};
	static Warehouse rack23=new Warehouse(shelf23,3,5);
	private static double[] shelf33= {0.4,0.05,0.15};
	static Warehouse rack33=new Warehouse(shelf33,3,5);
	private static double[] shelf43= {0.4,0.05,0.2};
	static Warehouse rack43=new Warehouse(shelf43,3,5);
	// white column
	private static double[] shelf14= {0.5,0.05,0.05};
	static Warehouse rack14=new Warehouse(shelf14,4,5);
	private static double[] shelf24= {0.5,0.05,0.1};
	static Warehouse rack24=new Warehouse(shelf24,4,5);
	private static double[] shelf34= {0.5,0.05,0.15};
	static Warehouse rack34=new Warehouse(shelf34,4,5);
	private static double[] shelf44= {0.5,0.05,0.2};
	static Warehouse rack44=new Warehouse(shelf44,4,5);
	
	static ArrayList<Warehouse> posFork=new ArrayList<Warehouse>(16);*/
	boolean suppressed=false;
	
	public BringToRack3(Flags2 flags,boolean boxVast,EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G,EV3LargeRegulatedMotor D, EV3UltrasonicSensor Uwall,EV3UltrasonicSensor Udump) {
		Main.color1=c1;
		Main.Lift=L;
		Main.Grab=G;
		Main.Drive=D;
		Main.usWall=Uwall;
		Main.usDump=Udump;
		Main.boxVast=boxVast;
		Main.flags=flags;
	}

	public boolean takeControl(){
		return Main.flags.getBoxVast();
	}
	public void action(){
		suppressed=false;
		int tellerVol=0; // telt hoeveel vakken in een kolom al volzitten
		LCD.drawString("in BringToRack", 1, 1);
		Main.Drive.setSpeed(80);
        	
//		positionFork=new double[3];
//		positionFork[0]=0.30;
//		positionFork[1]=0.05;
//		positionFork[2]=0.05;
    	
/*    	posFork.add(rack11); posFork.add(rack21);posFork.add(rack31);posFork.add(rack41);
		posFork.add(rack42); posFork.add(rack32);posFork.add(rack22);posFork.add(rack12);
		posFork.add(rack13); posFork.add(rack23);posFork.add(rack33);posFork.add(rack43);
		posFork.add(rack44); posFork.add(rack34);posFork.add(rack24);posFork.add(rack14);
		*/
//    		positionFork=new double[3];
//    		positionFork[0]=0.30;
//    		positionFork[1]=0.05;
//    		positionFork[2]=0.05;

		//this.goToInitialState();
		  while(!suppressed) {
	        	
	        	
	        	if(Main.colorRep==1) {
	        		if((Math.abs(Main.positionFork[0]-Main.rack12.getCoordinates()[0])>range)&&(Main.positionFork[0]-Main.rack12.getCoordinates()[0]<0)) {
	                	Main.Drive.backward();
	                	
	                }
	            	else if((Math.abs(Main.positionFork[0]-Main.rack12.getCoordinates()[0])>range)&&(Main.positionFork[0]-Main.rack12.getCoordinates()[0]>0)) {
	                	Main.Drive.forward();
	                }
	                else {
	                	Main.Drive.stop();
	                	LCD.drawString("Juiste rij bereikt", 1, 1);
	                	//this.vakVol=false; 
	                	Main.Lift.rotate(-(int)(0.03*10000));
	                	Main.makeUpdate(2,Main.positionFork[2]+0.03);
	                	while(!suppressed && Main.flags.dropBox!=true)
	                	{
	                		
	                		SampleProvider colorSample = Main.color1.getRGBMode();

	                		int sampleSize = colorSample.sampleSize();
	                		float[] sample = new float[sampleSize];
	                		colorSample.fetchSample(sample, 0);
	                    	float red=(float) sample[0];
	                    	float green=(float) sample[1];
	                    	float blue=(float) sample[2];
	                    	if(red<0.08 && green<0.08 && blue<0.08) // dan zal er geen blok instaan
	                    	{
	                    		Main.Lift.rotate((int)(0.03*10000));
	                    		Main.makeUpdate(2,Main.positionFork[2]-0.03);
	                    		Main.flags.setDropBox(true);
	                    		
	                    	}
	                    	else if(red>0.08 || green>0.08 || blue>0.08) // er staat een blok
	                    	{
	                    		tellerVol++;
	                    		Main.Lift.rotate(-(int)(0.05*10000));
	                    		Main.makeUpdate(2,Main.positionFork[2]+0.05);
	                    		if(tellerVol==4) // als de kolom vol staat moet hij naar de dump gaan
	                    		{
	                    			suppressed=true;
	                    			Main.flags.setDump(true);
	                    			
	                    		}
	                    	}
	                	}
	                	
	                }
	        	}
		  }
//		Main.goToDumpDist(0.1);
//		Main.moveFork(Main.positionFork,posFork.get(6).getCoordinates()); 
//		Main.flags.setDropBox(true);
//		Main.boxVast=false;
//		Main.flags.setBoxVast(false);
//		Main.vakVol=true;
//		Main.flags.setVakVol(true);
        }
		
//		
		
	//}
	//uitstap
	public void suppress(){
		//this.suppress();
		suppressed=true;
		
	}
	
//	
//	public void goToShelf() {
//		
//	}
//	

	 
//	 public  void moveFork(double[] posOld,double[] posNew)
//		{
//			double posXold=posOld[0];
//			double posZold=posOld[2];
//			double posXnew=posNew[0];
//			double posZnew=posNew[2];
//			// change x-position
//			// measure the conversion from degrees to height! Now I chose a hypothetical conversion of 5 cm per 360 degree for driving and 1 cm per 360 degrees for lifting
//			double rotationDrive=(posXnew-posXold)*90/0.05;
//			double rotationLift=(posZnew-posZold)*90/0.01;
//			LCD.drawInt((int)rotationLift, 1, 1);
//			Main.Drive.setSpeed(100);
//			Main.Lift.setSpeed(720);
//			Main.Drive.rotate(-(int)rotationDrive);
//			Main.Lift.rotate(-(int)rotationLift);
////			positionFork[0]=posXnew;
////			positionFork[2]=posZnew;
//		}
/*		public  void goToInitialState()
		{
			Main.Drive.setSpeed(100);
			Main.Lift.setSpeed(200);
			double rotationDrive=(rack11.coordinates[0]-positionFork[0])*360/0.05;
			double rotationLift=(rack11.coordinates[2]-positionFork[2])*360/0.01;
//			positionFork[0]=rack11.coordinates[0];
//			positionFork[2]=rack11.coordinates[2];
		}
*/		
/*		
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
		
*/	 
	 
//	
//	public void driveWall() {
//		this.Drive.backward();
//	}
//	public void driveDump() {
//		this.Drive.forward();
//	}
//	
	
}





//
//if(position=='r') {
//	this.Drive.rotate(distRtoRek);
//}
//if(position=='g') {
//	this.Drive.rotate(distGtoRek);
//}
//if(position=='b') {
//	this.Drive.rotate(distBtoRek);
//}
//if(position=='w') {
//	this.Drive.rotate(distWtoRek);
//}







//
//if(fromRep) {
//	
//	float [] sampleWall = new float[spWall.sampleSize()];
//    spWall.fetchSample(sampleWall, 0);
//    distanceWall = (float)sampleWall[0];
//    float [] sampleDump = new float[spDump.sampleSize()];
//    spDump.fetchSample(sampleDump, 0);
//    distanceDump = (float)sampleDump[0];
//    teller++;
//    if(teller==1) {
//    	beginPos=distanceWall;
//    }
//    
//    if(beginPos>0.1 &&beginPos<0.15 ) {//rood
//    	this.Drive.rotate((int)distRtoRack);
//    }
//    if(beginPos>0.17 && beginPos<0.2) {//groen
//    	this.Drive.rotate((int)distGtoRack);
//    }
//    if(beginPos>0.22 && beginPos<.25) {//blauw
//    	this.Drive.rotate((int)distBtoRack);
//    }
//    if(beginPos>0.27 && beginPos<0.30) {//wit
//    	this.Drive.rotate((int)distWtoRack);
//    }
//    
//    //bijsturen naar exacte startlocatie
//    if((distanceDump-startPos)>range && distanceDump>startPos) {
//    	this.Drive.forward();
//    	if(distanceDump==startPos) {
//    		this.Drive.stop();
//    	}
//    }
//    if((distanceDump-startPos)<0 ) {
//    	this.Drive.backward();
//    	if(distanceDump==startPos) {
//    		this.Drive.stop();
//    	}
//    }
//    else {
//    	this.Drive.stop();
//    }
//    //Nu staat hij normaal aan vakje 1
//    
//    
//    
//    
//    
//	
//}
//if(fromFix) {
//	
//}
//
//
//