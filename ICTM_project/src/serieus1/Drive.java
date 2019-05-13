package serieus1;
import lejos.hardware.motor.EV3LargeRegulatedMotor; 
import lejos.hardware.motor.Motor; 
import lejos.hardware.port.MotorPort; 
import lejos.robotics.RegulatedMotor;

public class Drive implements Runnable {
	private double rotationAngle;
	EV3LargeRegulatedMotor motorDrive;
	public Drive(double angle, EV3LargeRegulatedMotor motor) {
		rotationAngle=angle;
		motorDrive=motor;
	}
	public void run(){ 
		motorDrive.rotate((int)rotationAngle);
		
	}
}