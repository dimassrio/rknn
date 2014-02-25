import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.*;
import java.awt.event.*;

/* UI CLASS */
public class App extends JApplet implements Runnable, ActionListener, MouseListener{
	
	/* CONST */
	public final static String WND_TITLE ="RkNN Region"; // title
	public final static int WND_WIDTH = 1000; // width
	public final static int WND_HEIGHT = 1000; // height

	/* PANEL */
	private JPanel basePanel = new JPanel();
	private JScrollPane staticcrollPanel = new JScrollPane();
	private AppPanel appPanel = new AppPanel(this);
	/* BUTTON */
	private JButton newButton = new JButton("Clear");
	private JButton loadButton = new JButton("Load Points");
	private JComboBox<String> levelDrop = new JComboBox<String>();
	private JButton processButton1 = new JButton("Process1");
	private JComboBox<String> listDrop = new JComboBox<String>();
	private JComboBox<String> kDrop = new JComboBox<String>();
	private JButton processButton2 = new JButton("Process2");
	/* DIALOG */
	final JFileChooser fc = new JFileChooser();
	public static void main(String[] args) {
		App app = new App();
		app.init();
		JFrame mainWindow = new JFrame();
		mainWindow.setSize(WND_WIDTH, WND_HEIGHT);
		mainWindow.setTitle(WND_TITLE);
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.add(app, "Center");
		mainWindow.setVisible(true);

	}

	public void init(){
		try {SwingUtilities.invokeAndWait(this);}
		catch (Exception e) {System.err.println("Initialization Failure");}
	}
	public void run(){
		setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newButton);
		buttonPanel.add(loadButton);
		buttonPanel.add(levelDrop);
		buttonPanel.add(processButton1);
		buttonPanel.add(listDrop);
		buttonPanel.add(kDrop);
		buttonPanel.add(processButton2);

		newButton.addActionListener(this);
		processButton1.addActionListener(this);
		appPanel.setPreferredSize(new Dimension(WND_WIDTH-100, WND_HEIGHT-100));
		appPanel.setBackground(Color.white);
		appPanel.addMouseListener(this);
		this.add(appPanel, "Center");
		this.add(buttonPanel, "North");
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newButton){
			appPanel.getPointContainer().clear();
			repaint();
		}else if(e.getSource() == loadButton){
			int returnVal = fc.showOpenDialog(this);
			if(returnVal==JFileChooser.APPROVE_OPTION){
				appPanel.processFile(fc.getSelectedFile());
			}
			repaint();
		}else if(e.getSource() == processButton1){
			appPanel.processPoint();
			repaint();
		}
	}

	public void mousePressed(MouseEvent e){
		if(e.getButton()!=MouseEvent.BUTTON1){
			return;
		}
		PointExt inputPoint = new PointExt(appPanel.nextName(), e.getX(), e.getY());
		Integer a = appPanel.getPointContainer().size()+1;
		String newL = a.toString();
		levelDrop.addItem(newL);
		appPanel.addPoint(inputPoint);
	}


	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}


}

class AppPanel extends JPanel{
	private App controller;
	private PointList pointContainer = new PointList();
	private PointList vertexContainer = new PointList();
	private LineList bisectContainer = new LineList();
	private Graphics2D g;
	/* Constructor*/
	public AppPanel(App controller){
		this.controller = controller;
	}
	public PointList getPointContainer(){
		return this.pointContainer;
	}
	public void addPoint(PointExt p){
		pointContainer.add(p);
	}
	public void draw(PointExt p){
		int r = 3;
		int x = (int) p.getX();
		int y = (int) p.getY();

		g.setColor(Color.red);
		g.fillOval(x-r, y-r, r+r, r+r);
		g.setColor(Color.black);
		if (p.getName()==null) {
			g.drawString("null", x+5, y);
		}else{
			g.drawString(p.getName(), x+5, y);
		}
	}

	public void draw(LineExt a){
		g.setColor(Color.blue);
		int x1 = (int) a.getP1().getX();
		int y1 = (int) a.getP1().getY();
		int x2 = (int) a.getP2().getX();
		int y2 = (int) a.getP2().getY();
		g.drawLine(x1, y1, x2, y2);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.g = (Graphics2D) g;

		if (this.pointContainer.size()>0) {
			for (PointExt p : pointContainer) {
				draw(p);
			}
		}
		if (this.bisectContainer.size()>0) {
			for (LineExt p : bisectContainer) {
				draw(p);
			}
		}
		if(this.vertexContainer.size()>0){
			for (PointExt p : vertexContainer) {
				draw(p);
			}	
		}
		repaint();

	}

	public static void main(String[] args){

	}

	public void processFile(File a){
		try{
			pointContainer.clear();
			processFile(a.getAbsolutePath());
		}
	}

	public PointList processFile(String a){
		Scanner s = null;
		PointList l = new PointList();
		String[] temp1;
		PointExt p;

		try{
			s = new Scanner(new BufferedReader(new FileReader(a)));
			while(s.hasNext()){
				temp1 = s.nextLine().split(",");
				double x = Double.parseDouble(temp1[0]);
				double y = Double.parseDouble(temp1[1]);
				p = new PointExt(nextName, x, y);
				pointContainer.add(p);
			}
		}finally{
			if(s!=null){
				s.close();
			}
		}

	}

	public void processPoint(){
		ProcessApp thread = new ProcessApp(pointContainer.get(0), controller.WND_WIDTH, controller.WND_HEIGHT, pointContainer);
		thread.processPoint();
		bisectContainer = thread.getBisectContainer();
		vertexContainer = thread.getVertexContainer();
		bisectContainer.printList();
		repaint();
	}

	public String[] getContainerList(){
		String[] a = new String[pointContainer.size()];
		for(int i=0; i<pointContainer.size(); i++){
			a[i] = pointContainer.get(i).getName();
		}
		return a;
	}

	public String nextName(){
		char[] ls = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		String r = "";
		
		int number = this.pointContainer.size();
		while(true) {
			r = ls[number % 26] + r;
			if(number < 26) {
				break;
			}
			if (number>=26) {
					number /= 26;	
				number -=1;			
			}else{
				number /= 26;
				number -=1;			
			}

		}
			return r;
	}


}