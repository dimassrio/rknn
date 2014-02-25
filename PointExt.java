import java.awt.geom.Point2D;

public class PointExt extends Point2D.Double{
	public PointExt(){

	}
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public PointExt(String name){
		this.name = name;
	}
	public PointExt(String name, double x, double y){
		this.name = name;
		this.x = x;
		this.y = y;
	}
	public PointExt(double x, double y){
		this.x = x;
		this.y = y;
	}
	public static void main(String[] args) {
		
	}
}