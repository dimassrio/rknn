import java.util.ArrayList;
class LineList extends ArrayList<LineExt> {
	public static void main(String[] args) {
		
	}

	public void printList(){
		for (LineExt p : this) {
			System.out.println(p.getName());
		}
	}
}