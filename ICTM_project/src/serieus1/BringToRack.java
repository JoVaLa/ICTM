package serieus1;

import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;



public class BringToRack implements Behavior{
	private Flags flags;
	private  EV3ColorSensor color1;
	private  EV3LargeRegulatedMotor Lift;
	private  EV3LargeRegulatedMotor Grab;
	private  EV3LargeRegulatedMotor Drive;
	private  EV3UltrasonicSensor usWall;
	private  EV3UltrasonicSensor usDump;
	public boolean boxVast;
	
	
	public boolean vakVol=true;
	private boolean fromRep=true;
	private boolean fromFix=false;
	private double distanceWall=100;//continue meetwaarde
	private double distanceDump=100;//continue meetwaarde
	private int teller=0;
	private double beginPos = 0;//positie waar 'BringtoRek' is opgeroepen
	private double startPos=0.3;//vaste afstand van dump tot startpositie rek (vakje 1 vd 16)
	private double range=0.02;
//	float wallPosition=(float)0.20;
//	char position;
//	double distRtoRack=0.3;//angle ingeven
//	double distGtoRack=0.25;
//	double distBtoRack=0.2;
//	double distWtoRack=0.15;
	static double[] positionFork;
	// red column
	private static double[] shelf11= {0.2,0.05,0.05};
	static BrolWarehouse rack11=new BrolWarehouse(shelf11,1,5);
	private static double[] shelf21= {0.2,0.05,0.1};
	static BrolWarehouse rack21=new BrolWarehouse(shelf21,1,5);
	private static double[] shelf31= {0.2,0.05,0.15};
	static BrolWarehouse rack31=new BrolWarehouse(shelf31,1,5);
	private static double[] shelf41= {0.2,0.05,0.2};
	static BrolWarehouse rack41=new BrolWarehouse(shelf41,1,5);
	// green column
	private static double[] shelf12= {0.3,0.05,0.05};
	static BrolWarehouse rack12=new BrolWarehouse(shelf12,2,5);
	private static double[] shelf22= {0.3,0.05,0.1};
	static BrolWarehouse rack22=new BrolWarehouse(shelf22,2,5);
	private static double[] shelf32= {0.3,0.05,0.15};
	static BrolWarehouse rack32=new BrolWarehouse(shelf32,2,5);
	private static double[] shelf42= {0.3,0.05,0.2};
	static BrolWarehouse rack42=new BrolWarehouse(shelf42,2,5);
	
	// blue column
	private static double[] shelf13= {0.4,0.05,0.05};
	static BrolWarehouse rack13=new BrolWarehouse(shelf13,3,5);
	private static double[] shelf23= {0.4,0.05,0.1};
	static BrolWarehouse rack23=new BrolWarehouse(shelf23,3,5);
	private static double[] shelf33= {0.4,0.05,0.15};
	static BrolWarehouse rack33=new BrolWarehouse(shelf33,3,5);
	private static double[] shelf43= {0.4,0.05,0.2};
	static BrolWarehouse rack43=new BrolWarehouse(shelf43,3,5);
	// white column
	private static double[] shelf14= {0.5,0.05,0.05};
	static BrolWarehouse rack14=new BrolWarehouse(shelf14,4,5);
	private static double[] shelf24= {0.5,0.05,0.1};
	static BrolWarehouse rack24=new BrolWarehouse(shelf24,4,5);
	private static double[] shelf34= {0.5,0.05,0.15};
	static BrolWarehouse rack34=new BrolWarehouse(shelf34,4,5);
	private static double[] shelf44= {0.5,0.05,0.2};
	static BrolWarehouse rack44=new BrolWarehouse(shelf44,4,5);
	
	static ArrayList<BrolWarehouse> posFork=new ArrayList<BrolWarehouse>(16);
	
	public BringToRack(Flags flags,boolean boxVast,EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G,EV3LargeRegulatedMotor D, EV3UltrasonicSensor Uwall,EV3UltrasonicSensor Udump) {
		this.color1=c1;
		this.Lift=L;
		this.Grab=G;
		this.Drive=D;
		this.usWall=Uwall;
		this.usDump=Udump;
		this.boxVast=boxVast;
		this.flags=flags;
	}
	public boolean takeControl(){
		return flags.getBoxVast();
	}
	public void action(){
		
		this.Drive.setSpeed(80);
        while(this.getWallDist()>0.04 && this.getDumpDist()>0.04) {
        	LCD.drawString("Pos"+(this.getDumpDist()-startPos), 1, 1);
        	if((this.getDumpDist()-startPos)>range ) {
            	this.Drive.forward();
            	
            }
        	else if((this.getDumpDist()-startPos)<0 ) {
            	this.Drive.backward();
            	
            }
            else {
            	this.Drive.stop();
            	
            }
        	
        	
        	
        	posFork.add(rack11); posFork.add(rack21);posFork.add(rack31);posFork.add(rack41);
    		posFork.add(rack42); posFork.add(rack32);posFork.add(rack22);posFork.add(rack12);
    		posFork.add(rack13); posFork.add(rack23);posFork.add(rack33);posFork.add(rack43);
    		posFork.add(rack44); posFork.add(rack34);posFork.add(rack24);posFork.add(rack14);
//    		positionFork=new double[3];
//    		positionFork[0]=0.30;
//    		positionFork[1]=0.05;
//    		positionFork[2]=0.05;

    		goToInitialState();
    		moveFork(posFork.get(0).getCoordinates(),posFork.get(6).getCoordinates()); 
    		boxVast=false;
    		vakVol=true;
        }
		
//		
		
	}
	//uitstap
	public void suppress(){
		this.suppress();
		
	}
	
//	
//	public void goToShelf() {
//		
//	}
//	
	 public  double getWallDist() {
		 	SampleProvider spWall = this.usWall.getDistanceMode();
	     	float [] sampleWall = new float[spWall.sampleSize()];
	        spWall.fetchSample(sampleWall, 0);
	        double distanceWall = (float)sampleWall[0];
	        return distanceWall;
	  }
		 
	 public  double getDumpDist() {
			SampleProvider spDump = this.usDump.getDistanceMode();
			float [] sampleDump = new float[spDump.sampleSize()];
	        spDump.fetchSample(sampleDump, 0);
	        double distanceDump = (float)sampleDump[0];
		    return distanceDump;
	 }
	 
	 public  void moveFork(double[] posOld,double[] posNew)
		{
			double posXold=posOld[0];
			double posZold=posOld[2];
			double posXnew=posNew[0];
			double posZnew=posNew[2];
			// change x-position
			// measure the conversion from degrees to height! Now I chose a hypothetical conversion of 5 cm per 360 degree for driving and 1 cm per 360 degrees for lifting
			double rotationDrive=(posXnew-posXold)*90/0.05;
			double rotationLift=(posZnew-posZold)*90/0.01;
			this.Drive.setSpeed(360);
			this.Lift.setSpeed(720);
			this.Drive.rotate(-(int)rotationDrive);
			this.Lift.rotate(-(int)rotationLift);
			positionFork[0]=posXnew;
			positionFork[2]=posZnew;
		}
		public  void goToInitialState()
		{
			this.Drive.setSpeed(100);
			this.Lift.setSpeed(200);
			double rotationDrive=(rack11.coordinates[0]-positionFork[0])*360/0.05;
			double rotationLift=(rack11.coordinates[2]-positionFork[2])*360/0.01;
			positionFork[0]=rack11.coordinates[0];
			positionFork[2]=rack11.coordinates[2];
		}
	 
	 
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