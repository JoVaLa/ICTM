package test;


import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import serieus1.Main;
public class Pakken {
	static EV3LargeRegulatedMotor Drive=new EV3LargeRegulatedMotor(MotorPort.D);
	static EV3LargeRegulatedMotor Lift=new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor Grab=new EV3LargeRegulatedMotor(MotorPort.C);
	public Pakken() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LCD.drawString("brol", 1, 5);
//		Grab.setSpeed(300);
//		Lift.setSpeed(300);
//		Grab.rotate(470); 	//DEZE WAARDE IS GOED
//		Lift.rotate(-200);	//DEZE WAARDE IS GOED
		Grab.rotate(-470);
//		Lift.rotate(200);
//		//Main.Lift.rotate(-180); //om geen hoogteverschil te hebben met beginpositie
//		//double rotationDrive=(posXnew-posXold)*2000;
//		double rotationLift=(0.05)*10000;
//		Lift.rotate(-(int)rotationLift);
//		
//		Grab.rotate(400);
//		Lift.rotate(-180);
//		Grab.rotate(-400);
//		Lift.rotate(180);
		
	}

}
