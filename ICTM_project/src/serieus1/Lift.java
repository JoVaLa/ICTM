package serieus1;
import lejos.hardware.motor.EV3LargeRegulatedMotor; 
import lejos.hardware.motor.Motor; 
import lejos.hardware.port.MotorPort; 
import lejos.robotics.RegulatedMotor;

public class Lift implements Runnable {
	private double rotationAngle;
	EV3LargeRegulatedMotor motorLift;
	public Lift(double angle, EV3LargeRegulatedMotor motor) {
		rotationAngle=angle;
		motorLift=motor;
	}
	public void run(){ 
		motorLift.rotate((int)rotationAngle);
		
	}
}
