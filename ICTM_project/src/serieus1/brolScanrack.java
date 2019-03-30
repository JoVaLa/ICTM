package serieus1;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import java.util.ArrayList;

public class brolScanrack {
	// x direction is driving direction, z is lifting direction
	// We need to keep track of the location of the fork of the robot at any instant. This is done via the array underneath to test
	static double[] positionFork;
	// red column
	private static double[] shelf11= {0.2,0.05,0.05};
	static BrolWarehouse rack11=new BrolWarehouse(shelf11,1,5);
	private static double[] shelf21= {0.2,0.05,0.1};
	static BrolWarehouse rack21=new BrolWarehouse(shelf21,1,5);
	private static double[] shelf31= {0.2,0.05,0.15};
	static BrolWarehouse rack31=new BrolWarehouse(shelf31,1,5);
	private static double[] shelf41= {0.2,0.05,0.2};
	static BrolWarehouse rack41=new BrolWarehouse(shelf41,1,5);
	// green column
	private static double[] shelf12= {0.3,0.05,0.05};
	static BrolWarehouse rack12=new BrolWarehouse(shelf12,2,5);
	private static double[] shelf22= {0.3,0.05,0.1};
	static BrolWarehouse rack22=new BrolWarehouse(shelf22,2,5);
	private static double[] shelf32= {0.3,0.05,0.15};
	static BrolWarehouse rack32=new BrolWarehouse(shelf32,2,5);
	private static double[] shelf42= {0.3,0.05,0.2};
	static BrolWarehouse rack42=new BrolWarehouse(shelf42,2,5);
	
	// blue column
	private static double[] shelf13= {0.4,0.05,0.05};
	static BrolWarehouse rack13=new BrolWarehouse(shelf13,3,5);
	private static double[] shelf23= {0.4,0.05,0.1};
	static BrolWarehouse rack23=new BrolWarehouse(shelf23,3,5);
	private static double[] shelf33= {0.4,0.05,0.15};
	static BrolWarehouse rack33=new BrolWarehouse(shelf33,3,5);
	private static double[] shelf43= {0.4,0.05,0.2};
	static BrolWarehouse rack43=new BrolWarehouse(shelf43,3,5);
	// white column
	private static double[] shelf14= {0.5,0.05,0.05};
	static BrolWarehouse rack14=new BrolWarehouse(shelf14,4,5);
	private static double[] shelf24= {0.5,0.05,0.1};
	static BrolWarehouse rack24=new BrolWarehouse(shelf24,4,5);
	private static double[] shelf34= {0.5,0.05,0.15};
	static BrolWarehouse rack34=new BrolWarehouse(shelf34,4,5);
	private static double[] shelf44= {0.5,0.05,0.2};
	static BrolWarehouse rack44=new BrolWarehouse(shelf44,4,5);
	
	static ArrayList<BrolWarehouse> posFork=new ArrayList<BrolWarehouse>(16);
	
	
	private static EV3ColorSensor colorsens = new EV3ColorSensor(SensorPort.S1);
	static EV3LargeRegulatedMotor motorDrive=new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor motorLift=new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3MediumRegulatedMotor motorGrab=new EV3MediumRegulatedMotor(MotorPort.C);
	
	
	public static void moveFork(double[] posOld,double[] posNew)
	{
		double posXold=posOld[0];
		double posZold=posOld[2];
		double posXnew=posNew[0];
		double posZnew=posNew[2];
		// change x-position
		// measure the conversion from degrees to height! Now I chose a hypothetical conversion of 5 cm per 360 degree for driving and 1 cm per 360 degrees for lifting
		double rotationDrive=(posXnew-posXold)*90/0.05;
		double rotationLift=(posZnew-posZold)*90/0.01;
		motorDrive.setSpeed(360);
		motorLift.setSpeed(720);
		motorDrive.rotate(-(int)rotationDrive);
		motorLift.rotate(-(int)rotationLift);
		positionFork[0]=posXnew;
		positionFork[2]=posZnew;
	}
	public static void goToInitialState()
	{
		motorDrive.setSpeed(100);
		motorLift.setSpeed(200);
		double rotationDrive=(rack11.coordinates[0]-positionFork[0])*360/0.05;
		double rotationLift=(rack11.coordinates[2]-positionFork[2])*360/0.01;
		positionFork[0]=rack11.coordinates[0];
		positionFork[2]=rack11.coordinates[2];
	}
	public static void scanPos(int position)
	{
		SampleProvider colorSample = colorsens.getRGBMode();

		int sampleSize = colorSample.sampleSize();
		float[] sample = new float[sampleSize];
		colorSample.fetchSample(sample, 0);
    	float red=(float) sample[0];
    	float green=(float) sample[1];
    	float blue=(float) sample[2];
    	// check if there's a block on the shelf and which one
    	if(red>0.10 && green<0.05 && blue<0.05)
    	{
    		posFork.get(position).setColorBlock(1);
    	}
    	else if(red<0.05 && green>0.10 && blue<0.05)
    	{
    		posFork.get(position).setColorBlock(2);
    	}
    	else if(red<0.05 && green<0.5 && blue>0.10)
    	{
    		posFork.get(position).setColorBlock(3);
    	}
    	else if(red>0.08 && green>0.08 && blue>0.08)
    	{
    		posFork.get(position).setColorBlock(4);
    	}
    	else
    	{
    		posFork.get(position).setColorBlock(5);
    	}
	}
	public static void main(String[] args) {
//		motorLift.setSpeed(720);
//		double rotationLift=(0.23)*90/0.01;
//		motorLift.rotate(-(int)rotationLift);
//		
		motorGrab.setSpeed(360);
		double rotationGrab=(-0.02)*90/0.01;
		motorGrab.rotate(-(int)rotationGrab);
		
		
//		posFork.add(rack11); posFork.add(rack21);posFork.add(rack31);posFork.add(rack41);
//		posFork.add(rack42); posFork.add(rack32);posFork.add(rack22);posFork.add(rack12);
//		posFork.add(rack13); posFork.add(rack23);posFork.add(rack33);posFork.add(rack43);
//		posFork.add(rack44); posFork.add(rack34);posFork.add(rack24);posFork.add(rack14);
//		positionFork=new double[3];
//		positionFork[0]=0.30;
//		positionFork[1]=0.05;
//		positionFork[1]=0.05;
//
//		goToInitialState();
//		int i=0;
//		while(i<15)
//		{
//			scanPos(i);
//			moveFork(posFork.get(i).getCoordinates(),posFork.get(i+1).getCoordinates());
//			i++;
//		}
//		// This moment the full rack is scanned, starting from the first shelf on the first row. The code below, could be written inside the while loop.
//		// Then, we can for example break the while loop immediately when one column seems to be empty.
//		// The code below will also be used to check if the class works
//		// Idea if the movement of the motors is not accurate enough: use the color sensor to decide what the position of the robot is.
//		int[] emptyOrMistake=new int[16];//0: right color, 1: empty, 2: wrong color 
//		for(int j=0;j<16;j++)
//		{
//			if(posFork.get(j).getColorBlock()!=posFork.get(j).getColorShelf() && posFork.get(j).getColorBlock()!=5 )
//			{
//				emptyOrMistake[j]=2;
//			}
//			if(posFork.get(j).getColorBlock()==5)
//			{
//				emptyOrMistake[j]=1;
//			}
//			
//		}
//		while(Button.readButtons()==0) {
//		for(int k=0;k<16;k++)
//		{
//			System.out.println(emptyOrMistake[k]);
//		}
//		}
//		// conditions for this class to be called: the robot shouldn't have a box on its fork, it should not have an assignment to deliver a box
//		// it is not very efficient to scan each time a box is placed in the warehouse --> it could be an idea to give the robot different assignements after scanning one time
//		
	}
}
