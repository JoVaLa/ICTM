package serieus1;
import java.lang.Thread;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import java.util.ArrayList;
// The aim of this class is to constantly monitor the position of the robot
// x-position --> we have got an ultrasonic sensor at each side of the vehicle.
// If we build walls on the left and right side of the setup, we can constantly measure the position of
// the robot with respect to one of these walls
// If the motor for height regulations seems to be inaccurate, we can extend this class by adding a new sensor that measures the height
// Dangers of this idea --> problems with human interaction

// For now we assume the total length of the set up is 1m (distance between the two walls)
// origin is at the left wall
public class Position2 extends Thread {
	
	
	public EV3UltrasonicSensor sensorWall;//=new EV3UltrasonicSensor(SensorPort.S4); // check sensorports
	public EV3UltrasonicSensor sensorDump;//=new EV3UltrasonicSensor(SensorPort.S3);
	public double[] positionVector;
	
	Position2(EV3UltrasonicSensor sensWall, EV3UltrasonicSensor sensDump) //double[] pos, 
	{
		sensorWall=sensWall;
		sensorDump=sensDump;
		//positionVector=pos;
	}
	// initialization here, but should be done in main program
	
	public void run()
	{
		
		while(true) // can be adapted if necessary, but this thread should run the whole time
		{
			SampleProvider rightDistance = sensorWall.getDistanceMode();
			SampleProvider leftDistance = sensorDump.getDistanceMode();
			int sampleSize = leftDistance.sampleSize();
			float[] sampleLeft = new float[sampleSize];
			float[] sampleRight = new float[sampleSize];
			leftDistance.fetchSample(sampleLeft, 0);
			rightDistance.fetchSample(sampleRight, 0);
		
			float positionLeft=sampleLeft[0];
			float positionRight=sampleRight[0];
			// At this point in the while loop we have the position of the vehicle with respect to the left and 
			// right wall. In our main program, we will call the position vector (x,y,z) that is updated in this thread after each measurement
			
			if(Main.flags.getSensor())
			{
				//if(Math.abs((positionLeft-0.08)-Main.positionFork[0])<0.10) {
				Main.makeUpdate(0,positionLeft-0.08);
				//}
			}
			else
			{
				//if(Math.abs((1.245-positionRight-0.05-.32)-Main.positionFork[0])<0.10) {//0.05 is distance between us sensor and scanner
				Main.makeUpdate(0,(1.095-positionRight-0.05-.32));
				//}
			}
			if((positionLeft<0.05 && Main.Drive.getSpeed()<=0) || (positionRight<0.05 && Main.Drive.getSpeed()>=0))
			{
				Main.flags.setHuman(true);
			}
			else
			{
				Main.flags.setHuman(false);
			}
			// add delay or sleep
			LCD.drawString(" "+Main.positionFork[0], 2, 1);
			LCD.drawString(" "+positionRight, 1, 3);
		}
	}
	// synchronization --> write a method for changing the position vector and call this method in both the main thread and this thread here

}