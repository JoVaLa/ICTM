package test;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

import java.util.ArrayList;

public class LinksEnRechts{

	
		// TODO Auto-generated constructor stub
		static EV3LargeRegulatedMotor Drive=new EV3LargeRegulatedMotor(MotorPort.B);

		
		
		public static void main(String[] args) throws InterruptedException{
			Drive.setSpeed(100);
			double rotationLift=(0.1)*2000;
			Drive.rotate(-(int)rotationLift);
			Thread.sleep(3000);
			
			Drive.rotate((int)rotationLift);
		}
			
		
	

}
