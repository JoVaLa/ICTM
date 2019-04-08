package serieus1;
import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;

import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;


public class ScanRek implements Behavior {
	boolean suppressed=false;
	public static void moveFork(double[] posOld,double[] posNew)
	{
		double posXold=posOld[0];
		double posZold=posOld[2];
		double posXnew=posNew[0];
		double posZnew=posNew[2];
		// change x-position
		// measure the conversion from degrees to height! Now I chose a hypothetical conversion of 5 cm per 360 degree for driving and 1 cm per 360 degrees for lifting
		double rotationDrive=(posXnew-posXold)*10/0.05;
		double rotationLift=(posZnew-posZold)*100/0.01;
		Main.Drive.setSpeed(100);
		Main.Lift.setSpeed(100);
		Main.Drive.rotate(-(int)rotationDrive);
		Main.Lift.rotate(-(int)rotationLift);
		Main.makeUpdate(0,posXnew);
		Main.makeUpdate(2,posZnew);
	}
	public static void goToInitialState()
	{
		Main.Drive.setSpeed(100);
		Main.Lift.setSpeed(100);
		double rotationDrive=(Main.rack11.coordinates[0]-Main.positionFork[0])*10/0.05;
		double rotationLift=(Main.rack11.coordinates[2]-Main.positionFork[2])*100/0.01;
		Main.Drive.rotate(-(int)rotationDrive);
		Main.Lift.rotate(-(int)rotationLift);
		Main.makeUpdate(0,Main.rack11.coordinates[0]);
		Main.makeUpdate(2,Main.rack11.coordinates[2]);
	}
	public static void scanPos(int position)
	{
		SampleProvider colorSample = Main.color1.getRGBMode();

		int sampleSize = colorSample.sampleSize();
		float[] sample = new float[sampleSize];
		colorSample.fetchSample(sample, 0);
    	float red=(float) sample[0];
    	float green=(float) sample[1];
    	float blue=(float) sample[2];
    	// check if there's a block on the shelf and which one
    	if(red>0.10 && green<0.05 && blue<0.05)
    	{
    		Main.posFork.get(position).setColorBlock(1);
    	}
    	else if(red<0.05 && green>0.10 && blue<0.05)
    	{
    		Main.posFork.get(position).setColorBlock(2);
    	}
    	else if(red<0.05 && green<0.5 && blue>0.10)
    	{
    		Main.posFork.get(position).setColorBlock(3);
    	}
    	else if(red>0.08 && green>0.08 && blue>0.08)
    	{
    		Main.posFork.get(position).setColorBlock(4);
    	}
    	else
    	{
    		Main.posFork.get(position).setColorBlock(5);
    	}
	}
	public boolean takeControl()
	{
		// define when to take control
		return true; // 
	}
	
	public void action() {
		suppressed=false;
		int tellerR=0;
		int tellerG=0;
		int tellerB=0;
		int tellerW=0;
		boolean columnR=false;//true als volledige kolom leeg is
		boolean columnG=false;
		boolean columnB=false;
		boolean columnW=false;
		//goToInitialState();
		Main.Drive.setSpeed(100);
		LCD.drawString("in scanrek", 1, 1);
		//Main.moveFork(Main.positionFork, Main.posFork.get(0).getCoordinates());//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Main.goToDumpDist(0.2);
		LCD.drawString("net voor while", 1, 3);
		int i=0;
		while(i<=15 && !suppressed) // additional conditions e.g. stop if an emty row is discovered)
		{
			scanPos(i);
			Delay.msDelay(500);
			
			int[] emptyOrMistake=new int[16];
			if(Main.posFork.get(i).getColorBlock()!=Main.posFork.get(i).getColorShelf() && Main.posFork.get(i).getColorBlock()!=5 )//FOUT
			{
				LCD.drawString("FOUT GEDETECTEERD", 1, 1);
				emptyOrMistake[i]=2;
				Main.Lift.rotate(250);//MOET NOG GEKALIBREERD WORDEN
				Main.flags.setTakeBox(true);
				Main.flags.setDump(true);
				LCD.clear();
				
			}
			if(Main.posFork.get(i).getColorBlock()==5)//LEEG
			{
				LCD.drawString("LEEG", 1, 1);
				emptyOrMistake[i]=1;
				if(i<=3) {
					tellerR++;
					if(tellerR==1) {
						columnR=true;
						tellerR=0;
						Main.colorRep=1;
						Main.flags.setVakVol(false);
						LCD.drawString("TEST", 1, 1);//test
						//suppressed=true;
	
					}
				}
				else if(i<8) {
					tellerG++;
					if(tellerG==4) {
						columnG=true;
						Main.colorRep=2;
						Main.vakVol=false;
						tellerG=0;
					}
				}
				else if(i<12) {
					tellerB++;
					if(tellerB==4) {
						columnB=true;
						Main.colorRep=3;
						Main.vakVol=false;
						tellerB=0;
					}
				}
				else if(i<16) {
					tellerW++;
					if(tellerW==4) {
						columnW=true;
						Main.colorRep=4;
						Main.vakVol=false;
						tellerW=0;
					}
				}
				
			}
			if(i<15) {
				moveFork(Main.posFork.get(i).getCoordinates(),Main.posFork.get(i+1).getCoordinates());
				}
			i++;
		}
		
	}
	
	public void suppress() {
		suppressed = true;
		
	}
}



//package serieus1;
//
//import lejos.robotics.subsumption.*;
//import lejos.hardware.motor.EV3LargeRegulatedMotor;
//import lejos.hardware.sensor.EV3ColorSensor;
//
//public class ScanRek implements Behavior{
//	private EV3ColorSensor color1;
//	private EV3LargeRegulatedMotor Lift;
//	private EV3LargeRegulatedMotor Grab;
//	private EV3LargeRegulatedMotor Drive;
//	boolean vakVol=true;
//	
//	public ScanRek(EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G) {
//		// TODO Auto-generated constructor stub
//	}
//	
//	//de IF
//	public boolean takeControl(){
//		return true;
//	}
//	//handeling
//	public void action(){
//		for(int i=1;i<=16&&vakVol;i++) {
//			
//		}
//
//	}
//	//uitstap
//	public void suppress(){
//		this.suppress();
//		
//	}
//	
//
//
//}
