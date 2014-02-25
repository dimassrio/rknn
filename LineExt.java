import java.awt.geom.Line2D;
public class LineExt extends Line2D.Double{
	public LineExt(){}
	public LineExt(double a, double b, double c, double d){
	 	this.setLine(a,b,c,d);
	 }
	public String name;
	public static void main(String[] args) {
		
	}
	public String getName(){
		return this.name;
	}
	public void setName(String a){
		this.name = a;
	}
	public double getM(){
		double m = ((this.getP2().getY()-this.getP1().getY())/(this.getP2().getX()-this.getP1().getX()));
		return m;
	}
	public boolean linesIntersect(LineExt a){
		if(Line2D.linesIntersect(this.getP1().getX(), this.getP1().getY(), this.getP2().getX(), this.getP2().getY(), a.getP1().getX(), a.getP1().getY(), a.getP2().getX(), a.getP2().getY())){
			return true;
		}else{
			return false;
		}
	}

	public PointExt getIntersectionPoint(LineExt b){
		double x1 = this.getX1();
		double y1 = this.getY1();
		double x2 = this.getX2();
		double y2 = this.getY2();
		double x3 = b.getX1();
		double y3 = b.getY1();
		double x4 = b.getX2();
		double y4 = b.getY2();

			PointExt p = null;

			double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
			if (d != 0) {
				double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
				double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

				p = new PointExt(xi, yi);
			}
			p.setName("v("+this.name+","+b.name+")");
			return p;
	}

	public String[] getLineName(){
		String[] a = this.name.split(":");
		a[0] = a[0].substring(5, a[0].length);
		a[1] = a[1].substring(0, a[0].length-1)
		return a;
	}

}