package test;




import java.util.ArrayList;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class AfmetingKast {
	static EV3LargeRegulatedMotor motorLift=new EV3LargeRegulatedMotor(MotorPort.A);

	
		
	public static void main(String[] args){
		motorLift.setSpeed(720);
		double rotationLift=(-0.07)*90/0.01;
		motorLift.rotate(-(int)rotationLift);
	}
		
	
	

}
