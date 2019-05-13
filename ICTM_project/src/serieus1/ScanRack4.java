package serieus1;
import lejos.hardware.lcd.LCD;
import java.util.ArrayList;
import lejos.robotics.SampleProvider;

import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;


public class ScanRack4 implements Behavior {
	boolean suppressed=false;
	int initLift=-350;

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
		//float[][] allSamples
		int sampleSize = colorSample.sampleSize();
		float[] sample = new float[sampleSize];
		colorSample.fetchSample(sample, 0);
    	float red=(float) sample[0];
    	float green=(float) sample[1];
    	float blue=(float) sample[2];
    	// check if there's a block on the shelf and which one
//    	if(red>0.07 && green<0.045 && blue<0.045)
//    	{
//    		Main.posFork.get(position).setColorBlock(1);
//    		Main.currentColor=1;
//    	}
//    	else if(red<0.05 && green>0.10 && blue<0.05)
//    	{
//    		Main.posFork.get(position).setColorBlock(2);
//    		Main.currentColor=2;
//    	}
//    	else if(red<0.05 && green<0.5 && blue>0.10)
//    	{
//    		Main.posFork.get(position).setColorBlock(3);
//    		Main.currentColor=3;
//    	}
//    	else if(red>0.08 && green>0.08 && blue>0.08)
//    	{
//    		Main.posFork.get(position).setColorBlock(4);
//    		Main.currentColor=4;
//    	}
//    	else
//    	{
//    		Main.posFork.get(position).setColorBlock(5);
//    		Main.currentColor=5;
//    	}
    	if(red+blue+green>0.06 && red>blue && red> green)
    	{
    		Main.posFork.get(position).setColorBlock(1);
    		Main.currentColor="red";
    	}
    	else if(red+blue+green>0.05 && green>blue && green>red)
    	{
    		Main.posFork.get(position).setColorBlock(2);
    		Main.currentColor="green";
    	}
    	else if(red+green+blue>0.06 && blue>green && blue>red)
    	{
    		Main.posFork.get(position).setColorBlock(3);
    		Main.currentColor="blue";
    	}
   
    	else
    	{
    		Main.posFork.get(position).setColorBlock(5);
    		Main.currentColor="empty";
    	}
	}
	public boolean takeControl()
	{
		// define when to take control
		return true; // 
	}
	
	public void action() 
	{
		
		Main.Lift.setSpeed(500);
		Boolean chooseColumn=false;
//		int tellerR=0;
//		int tellerG=0;
//		int tellerB=0;
		// We make an array out of it
		int[] newboxCounter=new int[3];
		for(int i=0;i<3;i++)
		{
			newboxCounter[i]=Main.boxCounter[i];
		}
		int[] colorCounter=new int[3];
		suppressed=false;
		int j=1;
		// j telt met welke kolom we bezig zijn. De eerste kolom wordt van onder naar boven gescand, de tweede van boven naar onder,...

		while(j<=3 && !suppressed)
		{
			int l=0;
			if(j==1)
			{
				int rowToScan=getIndexOfLargest(newboxCounter);
				int boxToScan=0;
				if(rowToScan%2==0) {boxToScan=rowToScan*4;}//////////////
				else {boxToScan=rowToScan*4+3;}
				
				double[] initialSpot={Main.posFork.get(boxToScan).getCoordinates()[0],Main.posFork.get(boxToScan).getCoordinates()[1],Main.posFork.get(boxToScan).getCoordinates()[2]+0.045};
				Main.moveFork(Main.positionFork,initialSpot); // Now we are at the initial spot of the first row we want to scan
				
				while(l<=3 &&!suppressed)
				{
					if(l<=3 && (rowToScan)%2==0) 
					{
						Delay.msDelay(1000);
						scanPos(boxToScan+l);
						LCD.drawString(Main.currentColor, 1, 5);
						if(Main.posFork.get(boxToScan+l).getColorBlock()!=Main.posFork.get(boxToScan+l).getColorShelf() && Main.posFork.get(boxToScan+l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							Main.emptyOrMistake[boxToScan+l]=2;
							Main.AOE++;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
							
						}
						if(Main.posFork.get(boxToScan+l).getColorBlock()==5)//LEEG
						{	
//							LCD.clear();
							LCD.drawString(Main.currentColor, 1, 5);
							Main.emptyOrMistake[boxToScan+l]=1;
							colorCounter[rowToScan]++;
						}
						if(Main.posFork.get(boxToScan+l).getColorBlock()==Main.posFork.get(boxToScan+l).getColorShelf() ) {
//							LCD.clearDisplay();
							LCD.drawString(Main.currentColor, 1, 5);
							//Delay.msDelay(5000);
						}
						
						
					}
					if(l<=3 && (rowToScan)%2==1) 
					{
						Delay.msDelay(1000);
						scanPos(boxToScan-l);
						LCD.drawString(Main.currentColor, 1, 5);
						if(Main.posFork.get(boxToScan-l).getColorBlock()!=Main.posFork.get(boxToScan-l).getColorShelf() && Main.posFork.get(boxToScan-l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							Main.emptyOrMistake[boxToScan-l]=2;
							Main.AOE++;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan-l).getColorBlock()==5)//LEEG
						{
//							LCD.clear();
							LCD.drawString(Main.currentColor, 1, 5);
							Main.emptyOrMistake[boxToScan-l]=1;
							colorCounter[rowToScan]++;
							//LCD.drawString(""+colorCounter[rowToScan], 3, 3);
						}
						if(Main.posFork.get(boxToScan-l).getColorBlock()==Main.posFork.get(boxToScan-l).getColorShelf() ) {
//							LCD.clearDisplay();
							LCD.drawString(Main.currentColor, 1, 5);
							//Delay.msDelay(5000);
						}
						
						
					}
					if(l<3 && (rowToScan)%2==0)
					{
						double[] followingSpot={Main.posFork.get(boxToScan+l+1).getCoordinates()[0],Main.posFork.get(boxToScan+l+1).getCoordinates()[1],Main.posFork.get(boxToScan+l+1).getCoordinates()[2]+0.045};
						Main.moveFork(Main.positionFork,followingSpot);	// if the colorscanner scans to fast, add delay here
					}
					if(l<3 && (rowToScan)%2==1) 
					{
						double[] followingSpot={Main.posFork.get(boxToScan-l-1).getCoordinates()[0],Main.posFork.get(boxToScan-l-1).getCoordinates()[1],Main.posFork.get(boxToScan-l-1).getCoordinates()[2]+0.045};
						Main.moveFork(Main.positionFork,followingSpot);
						// if the colorscanner scans to fast, add delay here	
					}
					// now we can check whether there is an empty column and act accordingly
					if(colorCounter[rowToScan]==4)
					{
						Main.colorRep=rowToScan+1;
						Main.flags.setVakVol(false);
						suppressed=true;
						chooseColumn=true;
						//LCD.drawString(""+colorCounter[rowToScan], 1, 3);
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
				double[] initialSpot={Main.posFork.get(boxToScan).getCoordinates()[0],Main.posFork.get(boxToScan).getCoordinates()[1],Main.posFork.get(boxToScan).getCoordinates()[2]+0.045};
				Main.moveFork(Main.positionFork,initialSpot); // Now we are at the initial spot of the second row we want to scan
				while(l<=3 &&!suppressed)
				{
					if(l<=3 && (rowToScan)%2==1) 
					{
						Delay.msDelay(1000);
						scanPos(boxToScan+l);
						LCD.drawString(Main.currentColor, 1, 5);
						if(Main.posFork.get(boxToScan+l).getColorBlock()!=Main.posFork.get(boxToScan+l).getColorShelf() && Main.posFork.get(boxToScan+l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							Main.emptyOrMistake[boxToScan+l]=2;
							Main.AOE++;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan+l).getColorBlock()==5)//LEEG
						{
//							LCD.clear();
							LCD.drawString(Main.currentColor, 1, 5);
							//LCD.clear();
							//LCD.drawString(""+colorCounter[rowToScan], 1, 4);
							Main.emptyOrMistake[boxToScan+l]=1;
							colorCounter[rowToScan]++;
						}
						if(Main.posFork.get(boxToScan+l).getColorBlock()==Main.posFork.get(boxToScan+l).getColorShelf() ) {
//							LCD.clearDisplay();
							LCD.drawString(Main.currentColor, 1, 5);
							//Delay.msDelay(5000);
						}
						
						
					}
					if(l<=3 && (rowToScan)%2==0) 
					{
						Delay.msDelay(1000);
						scanPos(boxToScan-l);
						LCD.drawString(Main.currentColor, 1, 5);
						if(Main.posFork.get(boxToScan-l).getColorBlock()!=Main.posFork.get(boxToScan-l).getColorShelf() && Main.posFork.get(boxToScan-l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							Main.emptyOrMistake[boxToScan-l]=2;
							Main.AOE++;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							//LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan-l).getColorBlock()==5)//LEEG
						{
//							LCD.clear();
							LCD.drawString(Main.currentColor, 1, 5);
							//LCD.drawString(""+colorCounter[rowToScan], 1, 4);
							Main.emptyOrMistake[boxToScan-l]=1;
							colorCounter[rowToScan]++;
						}
						if(Main.posFork.get(boxToScan-l).getColorBlock()==Main.posFork.get(boxToScan-l).getColorShelf() ) {
//							LCD.clearDisplay();
							LCD.drawString(Main.currentColor, 1, 5);
							//Delay.msDelay(5000);
						}
						
					}
					if(l<3 && (rowToScan)%2==1)
					{
						double[] followingSpot={Main.posFork.get(boxToScan+l+1).getCoordinates()[0],Main.posFork.get(boxToScan+l+1).getCoordinates()[1],Main.posFork.get(boxToScan+l+1).getCoordinates()[2]+0.045};
						Main.moveFork(Main.positionFork,followingSpot);	// if the colorscanner scans to fast, add delay here
					}
					if(l<3 && (rowToScan)%2==0) 
					{
						double[] followingSpot={Main.posFork.get(boxToScan-l-1).getCoordinates()[0],Main.posFork.get(boxToScan-l-1).getCoordinates()[1],Main.posFork.get(boxToScan-l-1).getCoordinates()[2]+0.045};
						Main.moveFork(Main.positionFork,followingSpot);
						// if the colorscanner scans to fast, add delay here	
					}
					// now we can check whether there is an empty column and act accordingly
					if(colorCounter[rowToScan]==4)
					{
						
						Main.colorRep=rowToScan+1;
						
						Main.flags.setVakVol(false);
						suppressed=true;
						chooseColumn=true;
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
				double[] initialSpot={Main.posFork.get(boxToScan).getCoordinates()[0],Main.posFork.get(boxToScan).getCoordinates()[1],Main.posFork.get(boxToScan).getCoordinates()[2]+0.045};
				Main.moveFork(Main.positionFork,initialSpot); // Now we are at the initial spot of the second row we want to scan
				while(l<=3 &&!suppressed)
				{
					if(l<=3 && (rowToScan)%2==0) 
					{
						Delay.msDelay(1000);
						scanPos(boxToScan+l);
						LCD.drawString(Main.currentColor, 1, 5);
						if(Main.posFork.get(boxToScan+l).getColorBlock()!=Main.posFork.get(boxToScan+l).getColorShelf() && Main.posFork.get(boxToScan+l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							Main.emptyOrMistake[boxToScan+l]=2;
							Main.AOE++;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan+l).getColorBlock()==5)//LEEG
						{
//							LCD.clear();
							LCD.drawString(Main.currentColor, 1, 5);
							Main.emptyOrMistake[boxToScan+l]=1;
							colorCounter[rowToScan]++;
						}
						if(Main.posFork.get(boxToScan+l).getColorBlock()==Main.posFork.get(boxToScan+l).getColorShelf() ) {
//							LCD.clearDisplay();
							LCD.drawString(Main.currentColor, 1, 5);
							//Delay.msDelay(5000);
						}
						
						
					}
					if(l<=3 && (rowToScan)%2==1) 
					{
						Delay.msDelay(1000);
						scanPos(boxToScan-l);
						LCD.drawString(Main.currentColor, 1, 5);
						if(Main.posFork.get(boxToScan-l).getColorBlock()!=Main.posFork.get(boxToScan-l).getColorShelf() && Main.posFork.get(boxToScan-l).getColorBlock()!=5 )//FOUT
						{
							// Wa is da hier allemaal?
							LCD.drawString("Mistake detected", 1, 1);
							Main.emptyOrMistake[boxToScan-l]=2;
							Main.AOE++;
							//Main.Lift.rotate((int)0.01*10000);//van scanhoogte naar pakhoogte (1cm lager)
							//Main.flags.setTakeBox(true);
							//Main.flags.setDump(true);
							// The only thing that should happen now is the robot needs to remember there is a mistake and he should fix it when he has time so mistake boolean could be set equal to true
							LCD.clear();
							
						}
						if(Main.posFork.get(boxToScan-l).getColorBlock()==5)//LEEG
						{
//							LCD.clear();
							LCD.drawString(Main.currentColor, 1, 5);
							Main.emptyOrMistake[boxToScan-l]=1;
							colorCounter[rowToScan]++;
						}
						if(Main.posFork.get(boxToScan-l).getColorBlock()==Main.posFork.get(boxToScan-l).getColorShelf() ) {

						}
						
					}
					if(l<3 && (rowToScan)%2==0)
					{
						double[] followingSpot={Main.posFork.get(boxToScan+l+1).getCoordinates()[0],Main.posFork.get(boxToScan+l+1).getCoordinates()[1],Main.posFork.get(boxToScan+l+1).getCoordinates()[2]+0.045};
						Main.moveFork(Main.positionFork,followingSpot);	// if the colorscanner scans to fast, add delay here
					}
					if(l<3 && (rowToScan)%2==1) 
					{
						double[] followingSpot={Main.posFork.get(boxToScan-l-1).getCoordinates()[0],Main.posFork.get(boxToScan-l-1).getCoordinates()[1],Main.posFork.get(boxToScan-l-1).getCoordinates()[2]+0.045};
						Main.moveFork(Main.positionFork,followingSpot);
						// if the colorscanner scans to fast, add delay here	
					}
					// now we can check whether there is an empty column and act accordingly
					if(colorCounter[rowToScan]==4)
					{
						Main.colorRep=rowToScan+1;
						Main.flags.setVakVol(false);
						suppressed=true;
						chooseColumn=true;
						
					}
				l++;	
				}
				newboxCounter[rowToScan]=-1; // That way, the same column will never be scanned twice
			}
			j++;
		}

		//Main.flags.setVakVol(false);
		LCD.drawString(Main.boxCounter[0]+" "+Main.boxCounter[1]+" "+Main.boxCounter[2]+" ", 1, 4);
		LCD.drawString(Main.boxCounter[0]+" "+Main.boxCounter[1]+" "+Main.boxCounter[2]+" ", 1, 4);
		LCD.drawString(Main.boxCounter[0]+" "+Main.boxCounter[1]+" "+Main.boxCounter[2]+" ", 1, 4);
		LCD.drawString(Main.boxCounter[0]+" "+Main.boxCounter[1]+" "+Main.boxCounter[2]+" ", 1, 4);
		LCD.drawString(Main.AOE+"", 1, 7);
		if(chooseColumn==false && Main.AOE==0 )
		{
			Main.colorRep=getIndexOfLargest(colorCounter)+1;
			Main.flags.setVakVol(false);
			suppressed=true;
			LCD.clear();
			LCD.drawString("Red "+colorCounter[0], 1, 3);
			LCD.drawString("Groen "+colorCounter[1], 1, 4);
			LCD.drawString("Blauw "+colorCounter[2], 1, 5);
		}
		else if(Main.AOE>0)
		{
			Main.flags.setError(true);
			suppressed=true;
		}
		Delay.msDelay(1000);
		//Main.printRack();
	}
		
		
	
	
	public void suppress() {
		suppressed = true;
		
	}

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

