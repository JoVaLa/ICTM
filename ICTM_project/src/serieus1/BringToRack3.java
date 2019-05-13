package serieus1;

import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;



public class BringToRack3 implements Behavior{
	public double lift=0.05;
	public boolean vakVol=true;
	private boolean fromRep=true;
	private boolean fromFix=false;
	private double range=0.01;

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
		return (Main.flags.getBoxVast() && Main.flags.getBtr());
	}
	public void action(){
		LCD.drawString("bringtorack", 1, 1);
		Main.Lift.setSpeed(500);
		Main.Drive.setSpeed(120);
		if(Main.positionFork[0]>0.30) {
		double[] onderKol={0.30,0,0.01};
		Main.moveFork(Main.positionFork, onderKol);
		}
		double[] onderKol2={Main.positionFork[0],0,0.01};
		Main.moveFork(Main.positionFork, onderKol2);
		Main.flags.setSensor(true);
		suppressed=false;
		int tellerVol=0; // telt hoeveel vakken in een kolom al volzitten
		//LCD.drawString("in BringToRack", 1, 1);

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
	                	//LCD.drawString("Juiste rij bereikt", 1, 1);            
	                	//this.vakVol=false; 
	                	Main.Lift.rotate(-(int)(lift*10000));
	                	Main.makeUpdate(2,Main.positionFork[2]+lift);
	                	while(!suppressed && Main.flags.dropBox!=true)
	                	{
	                		Delay.msDelay(1000);
	                		
	                		SampleProvider colorSample = Main.color1.getRGBMode();

	                		int sampleSize = colorSample.sampleSize();
	                		float[] sample = new float[sampleSize];
	                		colorSample.fetchSample(sample, 0);
	                    	float red=(float) sample[0];
	                    	float green=(float) sample[1];
	                    	float blue=(float) sample[2];
	                    	if(red+green+blue<0.06)// && green<0.07 && blue<0.07) // dan zal er geen blok instaan
	                    	{
	                    		Main.Lift.rotate((int)((lift-0.02)*10000));
	                    		Main.makeUpdate(2,Main.positionFork[2]-(lift-0.02));
	                    		Main.flags.setDropBox(true);
	                    		Main.flags.setBtr(false);
	                    		suppressed=true;
	                    	}
	                    	else
	                    		// er staat een blok
	                    	{
	                    		tellerVol++;
	                    		LCD.clear();
	                    		LCD.drawString(""+tellerVol, 1, 6);
	                    		if(tellerVol<4) {
	                    		Main.Lift.rotate(-(int)(0.05*10000));
	                    		Main.makeUpdate(2,Main.positionFork[2]+0.05);
	                    		}
	                    		if(tellerVol==4) // als de kolom vol staat moet hij naar de dump gaan
	                    		{
	                    			suppressed=true;
	                    			Main.flags.setDump(true);
	                    			Main.flags.setBtr(false);
	                    			
	                    		}
	                    	}
	                	}
	                	
	                }
	        	}
	        	if(Main.colorRep==2) {
	        		if((Math.abs(Main.positionFork[0]-Main.rack13.getCoordinates()[0])>range)&&(Main.positionFork[0]-Main.rack13.getCoordinates()[0]<0)) {
	                	Main.Drive.backward();
	                	
	                }
	            	else if((Math.abs(Main.positionFork[0]-Main.rack13.getCoordinates()[0])>range)&&(Main.positionFork[0]-Main.rack13.getCoordinates()[0]>0)) {
	                	Main.Drive.forward();
	                }
	                else {
	                	Main.Drive.stop();
	                	//LCD.drawString("Juiste rij bereikt", 1, 1);
	                	//this.vakVol=false; 
	                	Main.Lift.rotate(-(int)(lift*10000));
	                	Main.makeUpdate(2,Main.positionFork[2]+lift);
	                	while(!suppressed && Main.flags.dropBox!=true)
	                	{
	                		Delay.msDelay(1000);
	                		
	                		SampleProvider colorSample = Main.color1.getRGBMode();

	                		int sampleSize = colorSample.sampleSize();
	                		float[] sample = new float[sampleSize];
	                		colorSample.fetchSample(sample, 0);
	                    	float red=(float) sample[0];
	                    	float green=(float) sample[1];
	                    	float blue=(float) sample[2];
	                    	if(red+green+blue<0.05) // dan zal er geen blok instaan
	                    	{
	                    		Main.Lift.rotate((int)((lift-0.02)*10000));
	                    		Main.makeUpdate(2,Main.positionFork[2]-(lift-0.02));
	                    		Main.flags.setDropBox(true);
	                    		Main.flags.setBtr(false);
	                    		suppressed=true;
	                    	}
	                    	else // er staat een blok
	                    	{
	                    		tellerVol++;
	                    		LCD.clear();
	                    		LCD.drawString(""+tellerVol, 1, 6);
	                    		if(tellerVol<4) {
	                    		Main.Lift.rotate(-(int)(0.05*10000));
	                    		Main.makeUpdate(2,Main.positionFork[2]+0.05);
	                    		}
	                    		if(tellerVol==4) // als de kolom vol staat moet hij naar de dump gaan
	                    		{
	                    			suppressed=true;
	                    			Main.flags.setDump(true);
	                    			Main.flags.setBtr(false);
	                    		}
	                    	}
	                	}
	                	
	                }
	        	}
	        	if(Main.colorRep==3) {
	        		if((Math.abs(Main.positionFork[0]-Main.rack14.getCoordinates()[0])>range)&&(Main.positionFork[0]-Main.rack14.getCoordinates()[0]<0)) {
	                	Main.Drive.backward();
	                	
	                }
	            	else if((Math.abs(Main.positionFork[0]-Main.rack14.getCoordinates()[0])>range)&&(Main.positionFork[0]-Main.rack14.getCoordinates()[0]>0)) {
	                	Main.Drive.forward();
	                }
	                else {
	                	Main.Drive.stop();
	                	//LCD.drawString("Juiste rij bereikt", 1, 1);
	                	//this.vakVol=false; 
	                	Main.Lift.rotate(-(int)(lift*10000));
	                	Main.makeUpdate(2,Main.positionFork[2]+lift);
	                	while(!suppressed && Main.flags.dropBox!=true)
	                	{
	                		Delay.msDelay(1000);
	                		
	                		SampleProvider colorSample = Main.color1.getRGBMode();

	                		int sampleSize = colorSample.sampleSize();
	                		float[] sample = new float[sampleSize];
	                		colorSample.fetchSample(sample, 0);
	                    	float red=(float) sample[0];
	                    	float green=(float) sample[1];
	                    	float blue=(float) sample[2];
	                    	if(red+green+blue<0.06) // dan zal er geen blok instaan
	                    	{
	                    		Main.Lift.rotate((int)((lift-0.02)*10000));
	                    		Main.makeUpdate(2,Main.positionFork[2]-(lift-0.02));
	                    		Main.flags.setDropBox(true);
	                    		Main.flags.setBtr(false);
	                    		suppressed=true;
	                    		
	                    	}
	                    	else // er staat een blok
	                    	{
	                    		tellerVol++;
	                    		LCD.clear();
	                    		LCD.drawString(""+tellerVol, 1, 6);
	                    		if(tellerVol<4) {
	                    		Main.Lift.rotate(-(int)(0.05*10000));
	                    		Main.makeUpdate(2,Main.positionFork[2]+0.05);
	                    		}
	                    		if(tellerVol==4) // als de kolom vol staat moet hij naar de dump gaan
	                    		{
	                    			suppressed=true;
	                    			Main.flags.setDump(true);
	                    			Main.flags.setBtr(false);
	                    		}
	                    	}
	                	}
	                	
	                }
	        	}
	        	}
		  //double[] onderKol={0.30,0,0};
//		  onderKol[0]=Main.positionFork[0];
//		  onderKol[2]=0.35;
//		  Main.moveFork(Main.positionFork, onderKol);
        }
		
//		
		
	//}
	//uitstap
	public void suppress(){
		//this.suppress();
		suppressed=true;
		
	}
	
}