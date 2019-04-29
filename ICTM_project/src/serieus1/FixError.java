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
	public FixError(EV3ColorSensor c1,EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G, EV3LargeRegulatedMotor D,EV3UltrasonicSensor U) {
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean takeControl(){
		return true;
	}
	//handeling
	public void action(){
		int AOE = 0;//AmountOfErrors
		int i = 0;
		
		while(i < 12) {
			if(Main.emptyOrMistake[i]==2) {
				AOE++;
			}
			i++;
		}
		
		if(AOE==0) {
			Main.flags.error = false;
		}
		
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
		
		double[] FEL = {Main.posFork.get(FE).getCoordinates()[0],Main.posFork.get(FE).getCoordinates()[1],Main.posFork.get(FE).getCoordinates()[2]+0.035};
		Main.moveFork(Main.positionFork,FEL);
		Main.colorRep = Main.posFork.get(FE).getColorBlock();
		Main.takeBox = true;	
	}
	//uitstap
	public void suppress(){
		suppressed=true;
		
	}
}
