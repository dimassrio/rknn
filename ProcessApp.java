public class ProcessApp {
	private PointExt queryPoint = new PointExt();
	private PointList pointContainer = new PointList();
	private PointList sortedContainer = new PointList();
	private PointList vertexContainer = new PointList();
	private Region focusRegion = new Region();
	private LineList bisectorContainer = new LineList();
	private double maxX;
	private double maxY;
	public ProcessApp(PointExt q, double x, double y, PointList p){
		this.queryPoint = q;
		this.maxX = x;
		this.maxY = y;
		this.pointContainer = p;
	}
	public static void main(String[] args) {
		
	}
	public void processPoint(){
		//sortedContainer = sortPoint(pointContainer);
		bisectorContainer = createAllBisector(pointContainer);
		vertexContainer = generateVertex(bisectorContainer);

		
	}
	public LineList getBisectContainer(){
		return bisectorContainer;
	}
	public PointList getVertexContainer(){
		return vertexContainer;
	}
	public LineList createAllBisector(PointList a){
		LineList tempList = new LineList();
		LineExt tempLine = new LineExt();
		if(a.size()>1){
			int n = a.size();
			for(int i=0;i<n-1;i++){
				for(int j=i+1;j<n;j++){
					if(!pointContainer.get(i).getName().equals(pointContainer.get(j).getName())){						
						tempLine = createBisector(pointContainer.get(i), pointContainer.get(j));
						//System.out.println(tempLine.getName());
						tempList.add(tempLine);
					}		
				}
			}
		}
		
		return tempList;
	};
	public LineList createAllBisector(PointList a, PointExt b){
		LineList tempList = new LineList();
		LineExt tempLine = new LineExt();
		for (PointExt tempA : a) {
			if(!tempA.getName().equals(b.getName())){
				tempLine = createBisector(tempA, b);
				tempList.add(tempLine);
			}
		}
		return tempList;
	}
	public LineExt createBisector(PointExt a, PointExt b){
		//b.X dan b.Y > a.X dan a.Y
		LineExt tempLine = new LineExt();
		PointExt tempPoint = new PointExt(((a.getX()+b.getX())/2),((a.getY()+b.getY())/2));
		
		if(a.getX()==b.getX()){
			tempLine.setLine(0,tempPoint.getY(), maxX, tempPoint.getY());
		}else if (a.getY()==b.getY()) {
			tempLine.setLine(tempPoint.getX(), 0, tempPoint.getY(), maxY);
		}else{
			if(a.getX()>b.getX()){
				tempLine.setLine(a.getX(),a.getY(),b.getX(), b.getY());
			}else{
				tempLine.setLine(b.getX(), b.getY(),a.getX(), a.getY());
			}
			double m = -1/tempLine.getM();
			double y1 = 0;
			double y2 = maxY;
			double x1 = (((y1-tempPoint.getY())+(m*tempPoint.getX()))/m);
			double x2 = (((y2-tempPoint.getY())+(m*tempPoint.getX()))/m);

			tempLine.setLine(x1,y1,x2,y2);
		}
		tempLine.setName("Bis("+a.getName()+":"+b.getName()+")");
		return tempLine;
	}

	public PointList generateVertex(LineList a){
		PointList vertexContainer = new PointList();
		LineList borderLine = new LineList();
		LineExt x1 = new LineExt(0.0,0.0,0.0,maxY);
		x1.setName("bl1");
		LineExt x2 = new LineExt(maxX,0.0, maxX,maxY);
		x2.setName("bl2");
		LineExt y1 = new LineExt(0.0,0.0, maxX,0.0);
		y1.setName("bl3");
		LineExt y2 = new LineExt(0.0,maxY, maxX,0.0);
		y2.setName("bl4");

		borderLine.add(x1);
		borderLine.add(x2);
		borderLine.add(y1);
		borderLine.add(y2);

		int n = bisectorContainer.size();

		for(int i=0;i<n-1;i++){
			for(int j=0; j<n;j++){
				if(!bisectorContainer.get(i).getName().equals(bisectorContainer.get(j).getName())){
					if(bisectorContainer.get(i).linesIntersect(bisectorContainer.get(j))){
						vertexContainer.add(bisectorContainer.get(i).getIntersectionPoint(bisectorContainer.get(j)));
					}
				}
			}
			for(int j =0; j<borderLine.size(); j++){
				if(bisectorContainer.get(i).linesIntersect(borderLine.get(j))){
					vertexContainer.add(bisectorContainer.get(i).getIntersectionPoint(borderLine.get(j)));
				}
			}
		}

		vertexContainer.add(new PointExt(0.0,0.0));
		vertexContainer.add(new PointExt(0.0, maxY));
		vertexContainer.add(new PointExt(maxX, 0.0));
		vertexContainer.add(new PointExt(maxX, maxY));

		return vertexContainer;

	}

	public PointList sortPoint(PointList pointContainer){
		PointList tempContainer = new PointList();
		if(queryPoint.getName()==""||queryPoint.getName() == null){
			return pointContainer;
		}else{
			for (PointExt a : pointContainer ) {
				if(!a.getName().equals(queryPoint.getName())){
					tempContainer.add(a);
				}
			}
			int n = tempContainer.size();

			for(int i = 0; i<n-1; i++){
				for(int j = i+1; j<n; j++){
					if(tempContainer.get(j).distance(queryPoint)<tempContainer.get(i).distance(queryPoint)){
						PointExt p = tempContainer.get(i);
						tempContainer.set(i, tempContainer.get(j));
						tempContainer.set(j, p);
					}
				}
			}
		}
		return tempContainer;
	}

	public RegionList generateRegion(PointList pointContainer, PointList vertexContainer){
		RegionList x = new RegionList();
		for (PointExt a : pointContainer ) {
			PointList p = new PointList();
			for (PointExt b : vertexContainer ) {
				LineExt l = new LineExt(a, b);
				String[] n = b.getLineName();
				for(LineExt c : bisectorContainer){
					if(l.linesIntersect(c)){
						if(c.getName().equals(n[0])||c.getName().equals(n[1])){
							if (p.checkExist(b)) {
								p.add(b);
							}
						}
					}
				}
			}
			
		}
	}
}