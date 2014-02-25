import java.util.ArrayList;

public class PointList extends ArrayList<PointExt>{
	public static void main(String[] args) {
			
	}	
	public boolean add(PointExt p){
		for (PointExt q : this) {
			if(p.getName()==q.getName()){
				p.setName(p.getName()+"-2");
			}
		}
		super.add(p);
		return true;
	}

	public boolean checkExist(PointExt p){
		for(PointExt q:this){
			if(p.getName().equals(q.getName)){
				return true;
			}
		}
		return false;
	}

	public void printList(){
		for (PointExt p : this) {
			System.out.println(p.getName()+"<"+p.getX()+":"+p.getY());
		}
	}
}