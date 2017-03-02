package adv.map;
import java.awt.Point;
import java.util.ArrayList;

public class AdvArray2D<Unit_Type>implements Cloneable{
	private ArrayList<Unit_Type> block;
	private int x,y;
	public AdvArray2D(){
		this(3,3);
	}
	public AdvArray2D(int x,int y){
		block=new ArrayList<Unit_Type>(x*y);
		this.x=x;
		this.y=y;
	}
	public AdvArray2D(Point size) {
		this(size.x,size.y);
	}
	public Unit_Type get(Point place){
		return this.get(place.x, place.y);
	}
	public Unit_Type get(int x,int y){
		return block.get(x*this.x+y);
	}
	
	public void set(Point place,Unit_Type object){
		this.set(place.x, place.y, object);
	}
	public void set(int x,int y,Unit_Type object){
		block.set(x*this.x+y, object);
	}
	public Point indexOf(Unit_Type object){
		int place=block.indexOf(object);
		Point output=new Point(place/this.x,place%this.x);
		return output;
	}
	
	@Override
	public AdvArray2D clone() throws CloneNotSupportedException{
		AdvArray2D copy=(AdvArray2D)super.clone();
		copy.setBlock((ArrayList<Unit_Type>)block.clone());
		return copy;
	}
	public void setBlock(ArrayList<Unit_Type> block){
		this.block=block;
	}
}


















