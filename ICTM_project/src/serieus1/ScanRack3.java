package serieus1;
import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;

import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;


public class ScanRack3 implements Behavior {
	boolean suppressed=false;
	int initLift=-350;
//	public static void moveFork(double[] posOld,double[] posNew)
//	{
//		double posXold=posOld[0];
//		double posZold=posOld[2];
//		double posXnew=posNew[0];
//		double posZnew=posNew[2];
//		// change x-position
//		// measure the conversion from degrees to height! Now I chose a hypothetical conversion of 5 cm per 360 degree for driving and 1 cm per 360 degrees for lifting
//		double rotationDrive=(posXnew-posXold)*10/0.05;
//		double rotationLift=(posZnew-posZold)*100/0.01;
//		Main.Drive.setSpeed(100);
//		Main.Lift.setSpeed(100);
//		Main.Drive.rotate(-(int)rotationDrive);
//		Main.Lift.rotate(-(int)rotationLift);
//		Main.makeUpdate(0,posXnew);
//		Main.makeUpdate(2,posZnew);
//	}
	public static void goToInitialState()
	{
		
		double rotationDrive=(Main.rack12.coordinates[0]-Main.positionFork[0])*10/0.05;
		double rotationLift=(Main.rack12.coordinates[2]-Main.positionFork[2])*100/0.01;
		Main.Drive.rotate(-(int)rotationDrive);
		Main.Lift.rotate(-(int)rotationLift);
		Main.makeUpdate(0,Main.rack12.coordinates[0]);
		Main.makeUpdate(2,Main.rack12.coordinates[2]);
	}
	public static void scanPos(int position)
	{
		SampleProvider colorSample = Main.color1.getRGBMode();

		int sampleSize = colorSample.sampleSize();
		float[] sample = new float[sampleSize];
		colorSample.fetchSample(sample, 0);
    	float red=(float) sample[0];
    	float green=(float) sample[1];
    	float blue=(float) sample[2];
    	// check if there's a block on the shelf and which one
    	if(red>0.10 && green<0.05 && blue<0.05)
    	{
    		Main.posFork.get(position).setColorBlock(1);
    	}
    	else if(red<0.05 && green>0.10 && blue<0.05)
    	{
    		Main.posFork.get(position).setColorBlock(2);
    	}
    	else if(red<0.05 && green<0.5 && blue>0.10)
    	{
    		Main.posFork.get(position).setColorBlock(3);
    	}
    	else if(red>0.08 && green>0.08 && blue>0.08)
    	{
    		Main.posFork.get(position).setColorBlock(4);
    	}
    	else
    	{
    		Main.posFork.get(position).setColorBlock(5);
    	}
	}
	public boolean takeControl()
	{
		// define when to take control
		return true; // 
	}
	
	public void action() 
	{
//		int tellerR=0;
//		int tellerG=0;
//		int tellerB=0;
		// We make an array out of it
		int[] newboxCounter=new int[3];
		newboxCounter=Main.boxCounter;
		int[] colorCounter=new int[3];
		suppressed=false;
		int j=1;
		// j telt met welke kolom we bezig zijn. De eerste kolom wordt van onder naar boven gescand, de tweede van boven naar onder,...
		int[] emptyOrMistake=new int[13];
		while(j<=3 && !suppressed)
		{
			int l=0;
			if(j==1)
			{
				int rowToScan=getIndexOfLargest(Main.boxCounter);
				int boxToScan=0;
				if(rowToScan%2==0) {boxToScan=rowToScan*4;}//////////////
				else {boxToScan=rowToScan*4+3;}
				
				double[] initialSpot={Main.posFork.get(boxToScan).getCoordinates()[0]+0.035,Main.posFork.get(boxToScan).getCoordinates()[1],Main.posFork.get(boxToScan).getCoordinates()[2]};
				Main.moveFork(Main.positionFork,initialSpot); // Now we are at the initial spot of the first row we want to scan
				
				while(l<=3 &&!suppressed)
				{
					if(l<=3 && (rowToScan)%2==0) 
					{
						scanPos(boxToScan+l);
						if(Main.posFork.get(boxToScan+l).getColorBlock()!=Main.posFork.get(boxToScan+l).getColorShelf() && Main.posFork.get(boxToScan+l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							emptyOrMistake[boxToScan+l]=2;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan+l).getColorBlock()==5)//LEEG
						{
							emptyOrMistake[boxToScan+l]=1;
							colorCounter[rowToScan]++;
						}
						
						
					}
					if(l<=3 && (rowToScan)%2==1) 
					{
						scanPos(boxToScan-l);
						if(Main.posFork.get(boxToScan-l).getColorBlock()!=Main.posFork.get(boxToScan-l).getColorShelf() && Main.posFork.get(boxToScan-l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							emptyOrMistake[boxToScan-l]=2;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan-l).getColorBlock()==5)//LEEG
						{
							emptyOrMistake[boxToScan-l]=1;
							colorCounter[rowToScan]++;
						}
						
					}
					if(l<3 && (rowToScan)%2==0)
					{
						double[] followingSpot={Main.posFork.get(boxToScan+l+1).getCoordinates()[0]+0.035,Main.posFork.get(boxToScan+l+1).getCoordinates()[1],Main.posFork.get(boxToScan+l+1).getCoordinates()[2]};
						Main.moveFork(Main.positionFork,followingSpot);	// if the colorscanner scans to fast, add delay here
					}
					if(l<3 && (rowToScan)%2==1) 
					{
						double[] followingSpot={Main.posFork.get(boxToScan-l-1).getCoordinates()[0]+0.035,Main.posFork.get(boxToScan-l-1).getCoordinates()[1],Main.posFork.get(boxToScan-l-1).getCoordinates()[2]};
						Main.moveFork(Main.positionFork,followingSpot);
						// if the colorscanner scans to fast, add delay here	
					}
					// now we can check whether there is an empty column and act accordingly
					if(colorCounter[rowToScan]==4)
					{
						Main.colorRep=rowToScan+1;
						Main.flags.setVakVol(false);
					}
				l++;	
				}
				newboxCounter[rowToScan]=-1; // That way, the same column will never be scanned twice
			}
			// Now a first column is scanned and we want to scan a second column (idea: keep track of all the colected boxes in boxcounter but make a new variable each time this action is called)
			if(j==2)
			{
				int rowToScan=getIndexOfLargest(newboxCounter);
				int boxToScan=0;
				if(rowToScan%2==0) {boxToScan=rowToScan*4+3;}
				else {boxToScan=rowToScan*4;}
				double[] initialSpot={Main.posFork.get(boxToScan).getCoordinates()[0]+0.035,Main.posFork.get(boxToScan).getCoordinates()[1],Main.posFork.get(boxToScan).getCoordinates()[2]};
				Main.moveFork(Main.positionFork,initialSpot); // Now we are at the initial spot of the second row we want to scan
				while(l<=3 &&!suppressed)
				{
					if(l<=3 && (rowToScan)%2==1) 
					{
						scanPos(boxToScan+l);
						if(Main.posFork.get(boxToScan+l).getColorBlock()!=Main.posFork.get(boxToScan+l).getColorShelf() && Main.posFork.get(boxToScan+l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							emptyOrMistake[boxToScan+l]=2;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan+l).getColorBlock()==5)//LEEG
						{
							emptyOrMistake[boxToScan+l]=1;
							colorCounter[rowToScan]++;
						}
						
						
					}
					if(l<=3 && (rowToScan)%2==0) 
					{
						scanPos(boxToScan-l);
						if(Main.posFork.get(boxToScan-l).getColorBlock()!=Main.posFork.get(boxToScan-l).getColorShelf() && Main.posFork.get(boxToScan-l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							emptyOrMistake[boxToScan-l]=2;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan-l).getColorBlock()==5)//LEEG
						{
							emptyOrMistake[boxToScan-l]=1;
							colorCounter[rowToScan]++;
						}
						
					}
					if(l<3 && (rowToScan)%2==1)
					{
						double[] followingSpot={Main.posFork.get(boxToScan+l+1).getCoordinates()[0]+0.035,Main.posFork.get(boxToScan+l+1).getCoordinates()[1],Main.posFork.get(boxToScan+l+1).getCoordinates()[2]};
						Main.moveFork(Main.positionFork,followingSpot);	// if the colorscanner scans to fast, add delay here
					}
					if(l<3 && (rowToScan)%2==0) 
					{
						double[] followingSpot={Main.posFork.get(boxToScan-l-1).getCoordinates()[0]+0.035,Main.posFork.get(boxToScan-l-1).getCoordinates()[1],Main.posFork.get(boxToScan-l-1).getCoordinates()[2]};
						Main.moveFork(Main.positionFork,followingSpot);
						// if the colorscanner scans to fast, add delay here	
					}
					// now we can check whether there is an empty column and act accordingly
					if(colorCounter[rowToScan]==4)
					{
						Main.colorRep=rowToScan+1;
						Main.vakVol=false;
						// If the colorcounter is 4, a column is empty so columns should not further be checked --> make sure the action gets interrupted
					}
					l++;	
				}
				
				newboxCounter[rowToScan]=-1; // That way, the same column will never be scanned twice
			}
			// Last column to scan
			if(j==3)
			{
				int rowToScan=getIndexOfLargest(newboxCounter);
				int boxToScan=0;
				if(rowToScan%2==0) {boxToScan=rowToScan*4;}
				else {boxToScan=rowToScan*4+3;}
				double[] initialSpot={Main.posFork.get(boxToScan).getCoordinates()[0]+0.035,Main.posFork.get(boxToScan).getCoordinates()[1],Main.posFork.get(boxToScan).getCoordinates()[2]};
				Main.moveFork(Main.positionFork,initialSpot); // Now we are at the initial spot of the second row we want to scan
				while(l<=3 &&!suppressed)
				{
					if(l<=3 && (rowToScan)%2==0) 
					{
						scanPos(boxToScan+l);
						if(Main.posFork.get(boxToScan+l).getColorBlock()!=Main.posFork.get(boxToScan+l).getColorShelf() && Main.posFork.get(boxToScan+l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							emptyOrMistake[boxToScan+l]=2;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan+l).getColorBlock()==5)//LEEG
						{
							emptyOrMistake[boxToScan+l]=1;
							colorCounter[rowToScan]++;
						}
						
						
					}
					if(l<=3 && (rowToScan)%2==1) 
					{
						scanPos(boxToScan-l);
						if(Main.posFork.get(boxToScan-l).getColorBlock()!=Main.posFork.get(boxToScan-l).getColorShelf() && Main.posFork.get(boxToScan-l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							emptyOrMistake[boxToScan-l]=2;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan-l).getColorBlock()==5)//LEEG
						{
							emptyOrMistake[boxToScan-l]=1;
							colorCounter[rowToScan]++;
						}
						
					}
					if(l<3 && (rowToScan)%2==0)
					{
						double[] followingSpot={Main.posFork.get(boxToScan+l+1).getCoordinates()[0]+0.035,Main.posFork.get(boxToScan+l+1).getCoordinates()[1],Main.posFork.get(boxToScan+l+1).getCoordinates()[2]};
						Main.moveFork(Main.positionFork,followingSpot);	// if the colorscanner scans to fast, add delay here
					}
					if(l<3 && (rowToScan)%2==1) 
					{
						double[] followingSpot={Main.posFork.get(boxToScan-l-1).getCoordinates()[0]+0.035,Main.posFork.get(boxToScan-l-1).getCoordinates()[1],Main.posFork.get(boxToScan-l-1).getCoordinates()[2]};
						Main.moveFork(Main.positionFork,followingSpot);
						// if the colorscanner scans to fast, add delay here	
					}
					// now we can check whether there is an empty column and act accordingly
					if(colorCounter[rowToScan]==4)
					{
						Main.colorRep=rowToScan+1;
						Main.vakVol=false;
						
					}
				l++;	
				}
				newboxCounter[rowToScan]=-1; // That way, the same column will never be scanned twice
			}
			j++;
		}
		// Now we are outside of the while, we can say based on the color counter which row is most empty or we can correct for a mistake if there is one
		// First we check here where the mistake is and send this to the behavior 'fix error'
		
		// if there is no mistake we check which color should be taken in the repository, therefore: adapt the takeControl of goToRep: not only vakvol, choose a new boolean that says to go to the repository
		Main.colorRep=getIndexOfSmallest(colorCounter);
		//set a specific boolean true or false
		
	}
		
			
	
	
	public void suppress() {
		suppressed = true;
		
	}


// Make an array in the mainfile that counts how much boxes are taken of each color:

// Each time a box is taken in go to repository: increment the corresponding color
// The goal is now to check the rack for which most boxes are brought first
// If each rack has at least one box --> get a box of the color that is least present

public int getIndexOfLargest(int[] array)
{
	int largest=0;
	for(int k=1;k<array.length;k++)
		if(array[k]>array[largest])
		{
			largest=k;
		}
	return largest;
}
public int getIndexOfSmallest(int[] array)
{
	int smallest=0;
	for(int k=1;k<array.length;k++)
		if(array[k]<array[smallest])
		{
			smallest=k;
		}
	return smallest;
}
}
//package serieus1;
//
//import lejos.robotics.subsumption.*;
//import lejos.hardware.motor.EV3LargeRegulatedMotor;
//import lejos.hardware.sensor.EV3ColorSensor;
//
//public class ScanRek implements Behavior{
//	private EV3ColorSensor color1;
//	private EV3LargeRegulatedMotor Lift;
//	private EV3LargeRegulatedMotor Grab;
//	private EV3LargeRegulatedMotor Drive;
//	boolean vakVol=true;
//	
//	public ScanRek(EV3ColorSensor c1, EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G) {
//		// TODO Auto-generated constructor stub
//	}
//	
//	//de IF
//	public boolean takeControl(){
//		return true;
//	}
//	//handeling
//	public void action(){
//		for(int i=1;i<=16&&vakVol;i++) {
//			
//		}
//
//	}
//	//uitstap
//	public void suppress(){
//		this.suppress();
//		
//	}
//	
//
//
