package serieus1;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class GoToRep implements Behavior{
	private Flags flags;
	private  EV3ColorSensor color1;
	private  EV3LargeRegulatedMotor Lift;
	private  EV3LargeRegulatedMotor Grab;
	private  EV3LargeRegulatedMotor Drive;
	private  EV3UltrasonicSensor usWall;
	private  EV3UltrasonicSensor usDump;
	private boolean vakVol;//GEEN PUBLIC GEBRUIKEN WEL PRIVATE EN DAN VIA CONSTRUCTOR VAN ANDERE KLASSE ERAAN GERAKEN
	
	
	private boolean boxVast;
	private int colorRep=4;// 1: red, 2: green, 3: blue, 4: white, 5:nothing (black)
	private boolean fromRep=true;
	private boolean fromFix=false;
	private double distanceWall=100;//continue meetwaarde
	private double distanceDump=100;//continue meetwaarde
	private int teller=0;
	private double beginPos = 0;//positie waar 'BringtoRek' is opgeroepen
	private double startPos=0.3;//vaste afstand van dump tot startpositie rek (vakje 1 vd 16)
	private double range=0.02;
	private double distRtoWall=.300;//angle ingeven
	private double distGtoWall=.250;
	private double distBtoWall=.200;
	private double distWtoWall=.150;

	
	public GoToRep(Flags flags, boolean vV,EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G,EV3LargeRegulatedMotor D, EV3UltrasonicSensor Uwall,EV3UltrasonicSensor Udump) {
		this.color1=c1;
		this.Lift=L;
		this.Grab=G;
		this.Drive=D;
		this.usWall=Uwall;
		this.usDump=Udump;
		this.vakVol=vV;
		this.flags=flags;
		
	}
	
	public boolean takeControl(){
		return (!flags.getVakVol() &&!flags.getBoxVast());
	}
	public void action(){
		
		this.Drive.setSpeed(80);
        while(this.getWallDist()>0.04 && this.getWallDist()>0.04) {
        	
        	
        	if (colorRep==1) {
        		if((this.getWallDist()-distRtoWall)>range ) {
                	this.Drive.backward();
                }
            	else if((this.getWallDist()-distRtoWall)<0 ) {
                	this.Drive.forward();
                }
                else {
                	this.Drive.stop();
                	//this.vakVol=false;                	
                	this.boxVast=true;
                	
                }
        	}
        	else if (colorRep==2) {
        		if((this.getWallDist()-distGtoWall)>range ) {
                	this.Drive.backward();
                }
            	else if((this.getWallDist()-distGtoWall)<0 ) {
                	this.Drive.forward();
                }
                else {
                	this.Drive.stop();
                	//this.vakVol=false;
                	boxVast=true;
                }
        	}
        	else if (colorRep==3) {
        		if((this.getWallDist()-distBtoWall)>range ) {
                	this.Drive.backward();
                }
            	else if((this.getWallDist()-distBtoWall)<0 ) {
                	this.Drive.forward();
                }
                else {
                	this.Drive.stop();
                	//this.vakVol=false;
                	boxVast=true;
                }
        	}
        	else if (colorRep==4) {
        		if((this.getWallDist()-distWtoWall)>range ) {
                	this.Drive.backward();
                }
            	else if((this.getWallDist()-distWtoWall)<0 ) {
                	this.Drive.forward();
                }
                else {
                	this.Drive.stop();
                	//this.vakVol=false;
                	boxVast=true;
                }
        	}
        	else LCD.drawString("geen kleur om te halen", 1, 1);
        	
        	
        }
		
//		
		
	}
	
	public void suppress(){
		this.suppress();
		
	}

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
	
}