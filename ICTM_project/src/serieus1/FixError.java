package serieus1;


import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class FixError implements Behavior{
	private EV3ColorSensor color1;
	private EV3LargeRegulatedMotor Lift;
	private EV3LargeRegulatedMotor Grab;
	private EV3LargeRegulatedMotor Drive;
	private EV3UltrasonicSensor us1;
	
	boolean suppressed=false;

	
	
	public boolean takeControl(){
		return (Main.flags.error && Main.AOE>0);
	}
	//handeling
	public void action(){
		
		LCD.drawString("fixerror", 1, 1);
		
		
		
		
		
		
		
		
		suppressed=false;
		

		
		
		boolean FirstBlood = false;
		int FE = 0;//FirstError
		int k = 0;
		while(!FirstBlood) {
			if(Main.emptyOrMistake[k]==2) {
				FE=k;
				FirstBlood=true;
			}
			k++;
		}
//		if(Main.AOE>1) {
//			int ff = 0;
//			
//			while(ff!=Main.AOE) {
//				if(Main.emptyOrMistake[k]==2) {
//					if(Main.posFork.get(k).getColorBlock()==1) {//red
//						if(Main.emptyOrMistake[1]==1||Main.emptyOrMistake[2]==1||Main.emptyOrMistake[3]==1||Main.emptyOrMistake[4]==1) {
//							FE = k;
//						}
//					}
//					if(Main.posFork.get(k).getColorBlock()==2) {//green
//						if(Main.emptyOrMistake[5]==1||Main.emptyOrMistake[6]==1||Main.emptyOrMistake[7]==1||Main.emptyOrMistake[8]==1) {
//							FE = k;
//						}
//					}
//					if(Main.posFork.get(k).getColorBlock()==3) {//blue
//						if(Main.emptyOrMistake[9]==1||Main.emptyOrMistake[10]==1||Main.emptyOrMistake[11]==1||Main.emptyOrMistake[12]==1) {
//							FE = k;
//						}
//					}
//					ff++;
//				}
//				k++;
//			}
//			}
		double[] FEL = {Main.posFork.get(FE).getCoordinates()[0],Main.posFork.get(FE).getCoordinates()[1],Main.posFork.get(FE).getCoordinates()[2]+0.004};
//		Main.moveFork(Main.positionFork,FEL);
		if((Math.abs(Main.positionFork[0]-FEL[0])>0.005)&&(Main.positionFork[0]-FEL[0]<0)) {
        	Main.Drive.backward();
        	
        }
    	else if((Math.abs(Main.positionFork[0]-FEL[0])>0.005)&&(Main.positionFork[0]-FEL[0]>0)) {
        	Main.Drive.forward();
        }
        else {
        	Main.Drive.stop();
        }
		Main.moveFork(Main.positionFork,FEL);
		Main.colorRep = Main.posFork.get(FE).getColorBlock();
		Main.emptyOrMistake[FE]=1;
		Main.posFork.get(FE).setColorBlock(5);
		Main.flags.setTakeBox(true);	
		Main.AOE--;
		if(Main.AOE==0) {
			Main.flags.setError(false);
			suppressed=true;
		}
	}
	//uitstap
	public void suppress(){
		suppressed=true;
		
	}
}
