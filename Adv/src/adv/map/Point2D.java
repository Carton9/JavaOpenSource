package adv.map;

public class Point2D implements Cloneable{
	int x,y;
	public Point2D(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public Point2D[] getSurround(){
		Point2D[] output={new Point2D(x-1,y-1),new Point2D(x,y-1),new Point2D(x+1,y-1),
						  new Point2D(x-1,y)                     ,new Point2D(x+1,y-1),
						  new Point2D(x-1,y+1),new Point2D(x,y-1),new Point2D(x+1,y-1),};
		return output;
	}
	
	@Override
	public Point2D clone() throws CloneNotSupportedException{
		return (Point2D)super.clone();
	}

}
