package serieus1;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class BringToRek implements Behavior{
	private EV3ColorSensor color1;
	private EV3LargeRegulatedMotor Lift;
	private EV3LargeRegulatedMotor Grab;
	private EV3LargeRegulatedMotor Drive;
	private EV3UltrasonicSensor usWall;
	private EV3UltrasonicSensor usDump;
	boolean boxVast=true;
	boolean fromRep=true;
	boolean fromFix=false;
	
	final SampleProvider spWall = usWall.getDistanceMode();
	final SampleProvider spDump = usDump.getDistanceMode();
	double distanceWall=0;//continue meetwaarde
	double distanceDump=0;//continue meetwaarde
	int teller=0;
	double beginPos = 0;//positie waar 'BringtoRek' is opgeroepen
	double startPos=0.3;//vaste afstand van dump tot startpositie rek (vakje 1 vd 16)
	
	
	double range=0.05;
	
	float wallPosition=(float)0.20;
	char position;
	double distRtoRack=0.3;//angle ingeven
	double distGtoRack=0.25;
	double distBtoRack=0.2;
	double distWtoRack=0.15;
	
	
	
	
	public BringToRek(EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G,EV3LargeRegulatedMotor D, EV3UltrasonicSensor Uwall,EV3UltrasonicSensor Udump) {
		// TODO Auto-generated constructor stub
		this.color1=c1;
		this.Lift=L;
		this.Grab=G;
		this.Drive=D;
		this.usWall=Uwall;
		this.usDump=Udump;
		
	}
	
	
	
	
	public boolean takeControl(){
		return boxVast;
	}
	//handeling
	public void action(){
		if(fromRep) {
			
			float [] sampleWall = new float[spWall.sampleSize()];
            spWall.fetchSample(sampleWall, 0);
            distanceWall = (float)sampleWall[0];
            float [] sampleDump = new float[spDump.sampleSize()];
            spDump.fetchSample(sampleDump, 0);
            distanceDump = (float)sampleDump[0];
            teller++;
            if(teller==1) {
            	beginPos=distanceWall;
            }
            
            if(beginPos>0.1 &&beginPos<0.15 ) {//rood
            	this.Drive.rotate((int)distRtoRack);
            }
            if(beginPos>0.17 && beginPos<0.2) {//groen
            	this.Drive.rotate((int)distGtoRack);
            }
            if(beginPos>0.22 && beginPos<.25) {//blauw
            	this.Drive.rotate((int)distBtoRack);
            }
            if(beginPos>0.27 && beginPos<0.30) {//wit
            	this.Drive.rotate((int)distWtoRack);
            }
            
            //bijsturen naar exacte startlocatie
            if((distanceDump-startPos)>range && distanceDump>startPos) {
            	this.Drive.forward();
            	if(distanceDump==startPos) {
            		this.Drive.stop();
            	}
            }
            if((distanceDump-startPos)<0 ) {
            	this.Drive.backward();
            	if(distanceDump==startPos) {
            		this.Drive.stop();
            	}
            }
            else {
            	this.Drive.stop();
            }
            //Nu staat hij normaal aan vakje 1
            
            
            
            
            
			
		}
		if(fromFix) {
			
		}
		
		
		
		
	}
	//uitstap
	public void suppress(){
		this.suppress();
		
	}
	
	
	public void goToShelf() {
		
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