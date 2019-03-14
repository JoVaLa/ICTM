package test;


import java.util.ArrayList;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Device;
import lejos.hardware.port.MotorPort;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

import lejos.hardware.motor.Motor;
// port A: motor to drive the vehicle
// port B: motor to lift
// port C: motor for gear and rack

import lejos.hardware.port.SensorPort; 
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.UARTSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;

import lejos.hardware.Button;

import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class afstandsSensor {
	
	
	
	private static EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S4);//JOHN

	public static void main(String[] args) {
		//EV3LargeRegulatedMotor motorDrive=new EV3LargeRegulatedMotor(MotorPort.B);
		//EV3LargeRegulatedMotor motorLift=new EV3LargeRegulatedMotor(MotorPort.A);
		//EV3LargeRegulatedMotor motorGrab=new EV3LargeRegulatedMotor(MotorPort.C);
		//EV3ColorSensor sensorColor=new EV3ColorSensor(SensorPort.S1);
		//EV3UltrasonicSensor sensorBack=new EV3UltrasonicSensor(SensorPort.S4);
		//ArrayList<String> a=sensorBack.getAvailableModes();
		//Available modes are distance (0) and listen(1)
		//System.out.println(a);
		//Button.waitForAnyPress();
		
		
//		EV3UltrasonicSensor sensorBack=new EV3UltrasonicSensor(SensorPort.S4);
//		sensorBack.setCurrentMode(1);
//		
//		SampleProvider distance=sensorBack.getDistanceMode();
//		
//		
//		
//		while(Button.readButtons()==0) {
//			int afstand=distance.getDistance();
//			System.out.println(afstand);
//			
//		}
//		
//		Button.waitForAnyPress();
//		
		
	

			final SampleProvider sp = us1.getDistanceMode();
			float distanceValue = 0;

			while(Button.readButtons()==0) {

	        	float [] sample = new float[sp.sampleSize()];
	            sp.fetchSample(sample, 0);
	            distanceValue = (float)sample[0];

				System.out.println("Distance: " + distanceValue);

				Delay.msDelay(500);
	        }

		
		
		
		//int afstand=distance.hashCode();
		//System.out.println(afstand);
		//Button.waitForAnyPress();
		//motorDrive.setSpeed(400);
		//motorLift.setSpeed(1000);
		//motorGrab.setSpeed(300);
		
		//motorDrive.rotate(1000);
		//motorLift.rotate(3600);
		//motorGrab.rotate(500);
		//SensorMode modeSensor=sensorColor.getAmbientMode();
		//int colorIden=sensorColor.getColorID();
		//SensorMode rgbvalues=sensorColor.getRGBMode();
		//System.out.println("ambientmode is "+modeSensor+"    coloidentiteit is "+colorIden+"   rgbvalues are "+rgbvalues);
		//Button.waitForAnyPress();
	}

}

