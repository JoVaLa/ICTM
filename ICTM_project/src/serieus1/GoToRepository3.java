package serieus1;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class GoToRepository3 implements Behavior{
//	private Flags flags;
//	private  EV3ColorSensor color1;
//	private  EV3LargeRegulatedMotor Lift;
//	private  EV3LargeRegulatedMotor Grab;
//	private  EV3LargeRegulatedMotor Drive;
//	private  EV3UltrasonicSensor usWall;
//	private  EV3UltrasonicSensor usDump;
//	private boolean vakVol;//GEEN PUBLIC GEBRUIKEN WEL PRIVATE EN DAN VIA CONSTRUCTOR VAN ANDERE KLASSE ERAAN GERAKEN
	boolean suppressed=false;
	
	private boolean boxVast;
	//private int colorRep=4;// 1: red, 2: green, 3: blue, 4: white, 5:nothing (black)
	private boolean fromRep=true;
	private boolean fromFix=false;
//	private double distanceWall=100;//continue meetwaarde
//	private double distanceDump=100;//continue meetwaarde
//	private int teller=0;
//	private double beginPos = 0;//positie waar 'BringtoRek' is opgeroepen
//	private double startPos=0.3;//vaste afstand van dump tot startpositie rek (vakje 1 vd 16)
	private double range=0.01;
	private double distRrep=.46;//angle ingeven
	private double distGrep=.50;
	private double distBrep=.55;
	//private double distWtoWall=.42;

	
	public GoToRepository3(Flags2 flags, boolean vV,EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G,EV3LargeRegulatedMotor D, EV3UltrasonicSensor Uwall,EV3UltrasonicSensor Udump) {
		Main.color1=c1;
		Main.Lift=L;
		Main.Grab=G;
		Main.Drive=D;
		Main.usWall=Uwall;
		Main.usDump=Udump;
		Main.vakVol=vV;
		Main.flags=flags;
		
	}
	
	public boolean takeControl(){
		return (!Main.flags.getVakVol() && !Main.flags.getBoxVast());
	}

	public void action(){
		Main.Drive.setSpeed(75);
		LCD.drawString(Main.colorRep+"", 1, 6);
		suppressed=false;
		//LCD.drawString("in GoToRep", 1, 1);
		double[] onderKol={0.35,0,0};
		Main.moveFork(Main.positionFork, onderKol);
		Main.flags.setSensor(false);
		//Main.Drive.setSpeed(200);
		Main.boxCounter[Main.colorRep-1]++;
		
        while(!suppressed) {
        	
        	
        	if(Main.colorRep==1) {
        		if((Math.abs(Main.positionFork[0]-distRrep)>range) && (Main.positionFork[0]-distRrep)<0) {
                	Main.Drive.backward();
                	
                }
            	else if((Math.abs(Main.positionFork[0]-distRrep)>range) && (Main.positionFork[0]-distRrep)>0) {
                	Main.Drive.forward();
                }
                else {
                	Main.Drive.stop();
                	LCD.drawString("gtr 11111", 1, 1);
                	//this.vakVol=false;  
                	Main.flags.setTakeBox(true);
                	suppressed=true;
                	
                }
        	}
        	if (Main.colorRep==2) {
        		if((Main.positionFork[0]-distGrep)<range) {
                	Main.Drive.backward();
                }
            	else if((Main.positionFork[0]-distGrep)>range ) {
                	Main.Drive.forward();
                }
                else {
                	Main.Drive.stop();
                	//this.vakVol=false;
                	boxVast=true;
                	Main.flags.setBoxVast(true);
                	
                }
        	}
        	if (Main.colorRep==3) {
        		if((Main.positionFork[0]-distBrep)<range ) {
                	Main.Drive.backward();
                }
            	else if((Main.positionFork[0]-distBrep)>0 ) {
                	Main.Drive.forward();
                }
                else {
                	Main.Drive.stop();
                	//this.vakVol=false;
                	boxVast=true;
                	Main.flags.setBoxVast(true);
                	
                }
        	}
  //      	if (Main.colorRep==4 ) {
  //      		if((this.getWallDist()-distWtoWall)>range ) {
  //              	Main.Drive.backward();
  //              }
  //          	else if((this.getWallDist()-distWtoWall)<0 ) {
  //              	Main.Drive.forward();
  //              }
  //              else {
  //              	Main.Drive.stop();
  //              	//this.vakVol=false;
  //              	//LCD.drawString("geen kleur om te halen", 1, 1);
  //              	boxVast=true;
  //              	
  //              	Main.flags.setTakeBox(true);
  //              	LCD.drawString("raus "+Main.flags.getBoxVast(), 1, 1);
  //              	//suppressed=true;
  //              	Main.flags.setBoxVast(true);
  //              	break;
  //              }
  //      		break;
  //      	}
        	
        	
        	
        }
		
//		
		
	}
	
	public void suppress(){
		//this.suppress();
		 suppressed=true;
		
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
	
}

