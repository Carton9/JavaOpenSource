package adv.map;

public class Point3D implements Cloneable{
	int x,y,z;
	public Point3D(int x,int y,int z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public Point3D[] getSurround(){
		Point3D[] output={new Point3D(x-1,y-1,z-1),new Point3D(x,y-1,z-1),new Point3D(x+1,y-1,z-1),
						   new Point3D(x-1,y,z-1),new Point3D(x,y,z-1),new Point3D(x+1,y-1,z-1),
						   new Point3D(x-1,y+1,z-1),new Point3D(x,y-1,z-1),new Point3D(x+1,y-1,z-1),
						   new Point3D(x-1,y-1,z),new Point3D(x,y-1,z),new Point3D(x+1,y-1,z),
						  new Point3D(x-1,y,z)                       ,new Point3D(x+1,y-1,z),
						  new Point3D(x-1,y+1,z),new Point3D(x,y-1,z),new Point3D(x+1,y-1,z),
						  new Point3D(x-1,y-1,z+1),new Point3D(x,y-1,z+1),new Point3D(x+1,y-1,z+1),
						   new Point3D(x-1,y,z+1) ,new Point3D(x,y,z+1),new Point3D(x+1,y-1,z+1),
		           		   new Point3D(x-1,y+1,z+1),new Point3D(x,y-1,z+1),new Point3D(x+1,y-1,z+1)};
		return output;
	}
	
	@Override
	public Point3D clone() throws CloneNotSupportedException{
		return (Point3D)super.clone();
	}
}
