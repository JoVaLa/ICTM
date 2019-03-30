package serieus1;
public class BrolWarehouse {
	public double[] coordinates;
	public int colorShelf;
	public int colorBlock;
	public BrolWarehouse(double[] coordinates,int colorShelf,int colorBlock) {
		this.coordinates=coordinates;
		this.colorShelf=colorShelf;
		this.colorBlock=colorBlock;
	}
	
	public void setCoordinates(double[] newCoord)
	{
		coordinates[0]=newCoord[0];
		coordinates[1]=newCoord[1];
		coordinates[2]=newCoord[2];
		//0=x-coordinate, 1=y-coordinate, 2=z-coordinate;	
	}
	public double[] getCoordinates()
	{
		return coordinates;
	}
	public void setColorShelf(int newColor)
	{
		colorShelf=newColor;
		// 1: red, 2: green, 3: blue, 4: white, 5:nothing (black)
	}
	public int getColorShelf()
	{
		return colorShelf;
	}
	public void setColorBlock(int newColor)
	{
		colorBlock=newColor;
		// 1: red, 2: green, 3: blue, 4: white, 5:nothing (black)
	}
	public int getColorBlock()
	{
		return colorBlock;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] coord=new double[3];
		coord[0]=0.2;
		coord[1]=0.05;
		coord[2]=0.05;
		BrolWarehouse rack=new BrolWarehouse(coord,1,5);
		double[] coordin=rack.getCoordinates();
		rack.setColorBlock(2);
		if(rack.getColorShelf()==rack.getColorBlock())
		{
			System.out.println("The block is in the right shelve");
		}
		else
		{
			System.out.println(coordin[0]);
		}

	}

}