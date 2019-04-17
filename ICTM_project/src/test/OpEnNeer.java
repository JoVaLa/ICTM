package test;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

import java.util.ArrayList;

public class OpEnNeer {

	
		// TODO Auto-generated constructor stub
		static EV3LargeRegulatedMotor motorLift=new EV3LargeRegulatedMotor(MotorPort.A);

		
		
		public static void main(String[] args) throws InterruptedException{
			motorLift.setSpeed(720);
			double rotationLift=(0.15)*10000;
			motorLift.rotate(-(int)rotationLift);
			Thread.sleep(3000);
			
			motorLift.rotate((int)rotationLift);
		}
			
		
	

}
