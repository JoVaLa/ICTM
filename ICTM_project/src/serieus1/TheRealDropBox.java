package serieus1;
import lejos.hardware.port.MotorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.subsumption.Behavior;
public class TheRealDropBox implements Behavior {
	
	
	public TheRealDropBox(Flags flags,boolean bv, boolean db,EV3LargeRegulatedMotor L, EV3LargeRegulatedMotor G) {
		// TODO Auto-generated constructor stub
		Main.flags = flags;
		Main.boxVast = bv;
		Main.drobBox = db;
		Main.Lift = L;
		this.Grab = G;
	
	}
	public boolean takeControl(){
		return (boxVast && dropBox)
	}
	//handeling
		public void action(){
			Main.Grab.setSpeed(100);
			Main.Lift.setSpeed(100);
			Main.Lift.rotate(180);
			Main.Grab.rotate(400);
			Main.Lift.rotate(-180);
			Main.Grab.rotate(-400);
			boxVast.setBoxVast(false)
		}
		//uitstap
		public void suppress(){
			this.suppress;
			
		}
	
}