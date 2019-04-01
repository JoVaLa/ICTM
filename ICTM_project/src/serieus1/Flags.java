package serieus1;

public class Flags {
	public boolean boxVast;
	public boolean vakVol;
	public boolean dumb;
	public boolean dropBox;
	public boolean takeBox;
	
	public Flags(boolean bv,boolean vv, boolean d, boolean db, boolean tb) {
		this.boxVast=bv;
		this.vakVol=vv;
		this.dumb = d;
		this.dropBox = db;
		this.takeBox = tb;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
	public void setDumb (boolean a){
		dumb = a;
	}
	public boolean getDumb (){
		return dumb;
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
	
}



	

	