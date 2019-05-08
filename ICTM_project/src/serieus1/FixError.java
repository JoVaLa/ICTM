package serieus1;


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
		
		double[] FEL = {Main.posFork.get(FE).getCoordinates()[0],Main.posFork.get(FE).getCoordinates()[1],Main.posFork.get(FE).getCoordinates()[2]+0.004};
		Main.moveFork(Main.positionFork,FEL);
		Main.colorRep = Main.posFork.get(FE).getColorBlock();
		Main.emptyOrMistake[FE]=1;
		Main.posFork.get(FE).setColorBlock(5);
		Main.AOE--;
		if(Main.AOE==0) {
			Main.flags.setError(false);;
			suppressed=true;
		}
		Main.flags.setTakeBox(true);	
	}
	//uitstap
	public void suppress(){
		suppressed=true;
		
	}
}
