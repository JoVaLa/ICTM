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

public class rijdAfstandTotMagazijn {
	//private static EV3UltrasonicSensor us1 = new EV3UltrasonicSensor(SensorPort.S4);//JOHN

	static EV3LargeRegulatedMotor Drive=new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor Lift=new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor Grab=new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3ColorSensor color1=new EV3ColorSensor(SensorPort.S1);
	static EV3UltrasonicSensor us1=new EV3UltrasonicSensor(SensorPort.S4);
	
	
	public static void main(String[] args) {

	
		
		Drive.setSpeed(400);
		Lift.setSpeed(400);
		Grab.setSpeed(300);
		//VARIABLES
		//ultrasoon
		final SampleProvider US1 = us1.getDistanceMode();
		
		float distance = 10000;
		int nrOfRotations=(int)(5*360);
		
		//color
		final SampleProvider COL1 = color1.getRGBMode();
		
		
		String color="";
		float redLimit;
		float blueLimit;
		float greenLimit;
		
		
		
		//HIER BEGINT DE ACTIE
		while(Button.readButtons()==0) {
	
		
			float [] us1Values = new float[US1.sampleSize()];
            US1.fetchSample(us1Values, 0);
            distance = (float)us1Values[0];
			System.out.println("Distance: " + distance);
			Delay.msDelay(500);
			//Drive.rotate(nrOfRotations);
//			Drive.forward();
//			
//			
			if(distance<0.20){
				Drive.stop();
				Lift.rotate(-nrOfRotations);//positief is clockwise als je naar scherm kijkt
		
			}
			
//			
//			float[] sample = new float[COL1.sampleSize()];
//			COL1.fetchSample(sample, 0);
//			if(sample[0]>0.1) {
//				
//				
//				
//
//				
//				Grab.rotate(+360); //positief is weg v wagen
//				
//			}
	

	}

}
}








//System.out.print("r=" + sample[0]);
//System.out.print(" g=" + sample[1]);
//System.out.println(" b=" + sample[2]);

