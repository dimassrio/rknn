import java.awt.Polygon;
public class Region extends Polygon{
	public String first;
	public String second;
	public Polygon area;
	
	public Region(){

	}

	public Region(PointList p){
		PointList q = this.jarvisMarch(p);
		int n = q.size();
		int[] x = new int[n];
		int[] y = new int[n];
		for (int i=0;i<n ;i++ ) {
			x[i] = q.get(i);
			y[i] = q.get(i);
		}

		this = new super(x,y,n);

		return z;

	}
	
	public PointList jarvisMarch(PointList vertexContainer){
	int currPoint = 0, minPoint = 0, maxPoint = 0, minAngle = 0, maxAngle = 0;
	PointList sortedVertex = new PointList();
			int[] usedPoint = new int[vertexContainer.size()];
			for (int i=0;i<usedPoint.length;i++) {
				usedPoint[i] = -1;
			}
			for (int i=0;i<vertexContainer.size();i++) {
				if (vertexContainer.get(i).getY() < vertexContainer.get(minPoint).getY()){
					minPoint = i;		
				}
			}
			for (int i=0;i<vertexContainer.size();i++) {
				if (vertexContainer.get(i).getY() > vertexContainer.get(maxPoint).getY()){
					maxPoint = i;		
				}
			}
			
			addUsedPoint(usedPoint, minPoint);
			currPoint = minPoint;
			int test = 0;
			
			while ((currPoint!=maxPoint)) {
				maxAngle = currPoint;
				for (int i=0;i<vertexContainer.size();i++) {
					if ((findAngle(vertexContainer.get(currPoint), vertexContainer.get(maxAngle))<findAngle(vertexContainer.get(currPoint), vertexContainer.get(i))) && (notUsed(usedPoint, i) || i == maxPoint) && (findAngle(vertexContainer.get(currPoint), vertexContainer.get(i))<=180)){
						maxAngle = i;
					}else if ((findAngle(vertexContainer.get(currPoint), vertexContainer.get(maxAngle)) == findAngle(vertexContainer.get(currPoint), vertexContainer.get(i))) && (notUsed(usedPoint, i) || i == maxPoint) && (findAngle(vertexContainer.get(currPoint), vertexContainer.get(i))<=180)) {
						if(vertexContainer.get(i).getX()<vertexContainer.get(maxAngle).getX()){
							maxAngle = i;
						}
					}
				}
				currPoint = maxAngle;
				addUsedPoint(usedPoint, currPoint);
			}
			currPoint = maxPoint;
			while(currPoint!=minPoint){
				
				minAngle = minPoint;
				for (int i = 0;i<vertexContainer.size();i++) {
					if ((findAngle(vertexContainer.get(currPoint), vertexContainer.get(minAngle))<findAngle(vertexContainer.get(currPoint), vertexContainer.get(i))) && (notUsed(usedPoint, i)) && (findAngle(vertexContainer.get(currPoint), vertexContainer.get(i))>=180)){
						minAngle = i;						
					}else if ((findAngle(vertexContainer.get(currPoint), vertexContainer.get(minAngle))==findAngle(vertexContainer.get(currPoint), vertexContainer.get(i))) && (notUsed(usedPoint, i)) && (findAngle(vertexContainer.get(currPoint), vertexContainer.get(i))>=180)) {
						if(vertexContainer.get(i).getX()>vertexContainer.get(minAngle).getX()){
							minAngle = i;
						}
					}
				}
				currPoint = minAngle;
				addUsedPoint(usedPoint, currPoint);
			}
			sortedVertex.clear();
			
			for (int i=0;i<usedPoint.length;i++) {
				if (usedPoint[i]!=-1) {
					if (sortedVertex.indexOf(vertexContainer.get(usedPoint[i]))==-1) {
						sortedVertex.add(vertexContainer.get(usedPoint[i]));
					}	
				}
				
			}
			return sortedVertex;
	}

	public static void main(String[] args) {
		
	}
	public int checkSimilar(Region a){
		if(this.first.equals(a.first)){
			return 1;
		}else if(this.second.equals(a.second)){
			return 2;
		}else{
			return 0;
		}

	}

	
}