//package serieus1;
//
//public class Flags {
//	public boolean boxVast;
//	public boolean vakVol;
//	public Flags(boolean bv,boolean vv) {
//		this.boxVast=bv;
//		this.vakVol=vv;
//	}
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//	public void setBoxVast(boolean a)
//	{
//			boxVast=a;
//	}
//	public boolean getBoxVast()
//	{
//		return boxVast;
//	}
//	public void setVakVol(boolean a)
//	{
//		vakVol=a;
//	}
//	public boolean getVakVol()
//	{
//		return vakVol;
//	}
//}


package serieus1;

public class Flags2 {
	public boolean boxVast;
	public boolean vakVol;
	public boolean dump;
	public boolean dropBox;
	public boolean takeBox;
	public boolean human;
	public boolean error;
	public boolean sensor;//true is dump
	public boolean btr;
	
	public Flags2(boolean bv,boolean vv, boolean d, boolean db, boolean tb, boolean h,boolean e,boolean s,boolean btr) {
		this.boxVast=bv;
		this.vakVol=vv;
		this.dump = d;
		this.dropBox = db;
		this.takeBox = tb;
		this.human=h;
		this.sensor=s;
		this.btr=btr;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void setSensor(boolean a)
	{
			sensor=a;
	}
	public boolean getSensor()
	{
		return sensor;
	}
	public void setBoxVast(boolean a)
	{
			boxVast=a;
	}
	public boolean getBoxVast()
	{
		return boxVast;
	}
	public void setVakVol(boolean a)
	{
		vakVol=a;
	}
	public boolean getVakVol()
	{
		return vakVol;
	}
	public void setDump (boolean a){
		dump = a;
	}
	public boolean getDumb (){
		return dump;
	}
	public void setDropBox (boolean a){
			dropBox = a;
	}
	public boolean getDropBox()
	{
		return dropBox;
	}
	public void setTakeBox (boolean a){
		takeBox = a;
	}
	public boolean getTakeBox()
	{
	return takeBox;
	}
	public void setHuman(boolean a){
		human = a;
	}
	public boolean getHuman()
	{
	return human;
	}
	public void setError(boolean a)
	{
			error=a;
	}
	public boolean getError()
	{
		return error;
	}
	public void setBtr(boolean a)
	{
			btr=a;
	}
	public boolean getBtr()
	{
		return btr;
	}
	
}





	

	