package test;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

import java.util.ArrayList;

public class Beneden {

	
		// TODO Auto-generated constructor stub
		static EV3LargeRegulatedMotor motorLift=new EV3LargeRegulatedMotor(MotorPort.A);

		
		
		public static void main(String[] args){
			motorLift.setSpeed(720);
			double rotationLift=(-0.01)*90/0.01;
			motorLift.rotate(-(int)rotationLift);
		}
			
		
	

}
