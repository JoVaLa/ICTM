package serieus1;

import java.util.ArrayList;

import lejos.robotics.subsumption.Behavior;



public class SeeHuman3 implements Behavior{
	boolean suppressed= false;
	public SeeHuman3() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean takeControl(){
		return Main.flags.getHuman();
	}
	//handeling
	public void action(){
		
			Main.Drive.stop();
		
	}
	//uitstap
	public void suppress(){
		suppressed = true;
	}

	
}