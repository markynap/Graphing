package graphing;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;


public class GraphingPlotter extends Canvas implements Runnable{
	
	private boolean running;
	private Thread thread;
	private int xMax = 200, yMax = 200;
	private int xMin = -xMax, yMin = -yMax;
	public TreeMap<Integer, Integer> popToGenMap;
	private int startX = 125;
	private int startY = Grapher.height - 75;
	private ArrayList<Rectangle> points;
	private ArrayList<Rectangle> secondpoints;
	private ArrayList<Rectangle> thirdpoints;
	private ArrayList<Rectangle> fourthpoints;
	private ArrayList<Rectangle> fifthpoints;
	private String Xlabel;
	private ArrayList<String> yLabels;
	
	private final int spacing = 6;
	private int inc;
	
	private Random random;
	
	private int[] nums;
	
	public GraphingPlotter() {
		popToGenMap = new TreeMap<>();
		points = new ArrayList<>();
		secondpoints = new ArrayList<>();
		thirdpoints = new ArrayList<>();
		fourthpoints = new ArrayList<>();
		fifthpoints = new ArrayList<>();
		Xlabel = "";
		random = new Random();
		nums = new int[101];
		yLabels = new ArrayList<>();
		setXLabel("Random number");
		addYLabel("Red: # of times chosen");
		new Grapher("RNG DATA", this);
		
	}
	
	public void tick() {
		int RNG = (int)(0.5 + (random.nextInt(101) + random.nextInt(101))/2);		
		nums[RNG]++;
		addPoint(RNG, nums[RNG]);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0,0, Grapher.width, Grapher.height);
		drawGraphBackground(g);
		drawNumberLines(g, 4);
		drawLegend(g);
		renderPopulationMapToGraph(g);
	}
	
	public void setXLabel(String label) {
		Xlabel = label;
	}

	public void addYLabel(String label) {
		yLabels.add(label);
	}
	
	
	private void renderPopulationMapToGraph(Graphics g) {
		
		g.setColor(Color.red);
		for (int i = 0; i < points.size(); i++) {
			Rectangle point = points.get(i);
			g.fillRect(point.x, point.y, point.width, point.height);
			drawLineToPreviousPoint(points, g, i);
		}
		g.setColor(Color.blue);
		for (int i = 0; i < secondpoints.size(); i++) {
			Rectangle point = secondpoints.get(i);
			g.fillRect(point.x, point.y, point.width, point.height);
			drawLineToPreviousPoint(secondpoints, g, i);
		}
		g.setColor(Color.green);
		for (int i = 0; i < thirdpoints.size(); i++) {
			Rectangle point = thirdpoints.get(i);
			g.fillRect(point.x, point.y, point.width, point.height);
			drawLineToPreviousPoint(thirdpoints, g, i);
		}
		g.setColor(Color.magenta);
		for (int i = 0; i < fourthpoints.size(); i++) {
			Rectangle point = fourthpoints.get(i);
			g.fillRect(point.x, point.y, point.width, point.height);
			drawLineToPreviousPoint(fourthpoints, g, i);
		}
		g.setColor(Color.orange);
		for (int i = 0; i < fifthpoints.size(); i++) {
			Rectangle point = fifthpoints.get(i);
			g.fillRect(point.x, point.y, point.width, point.height);
			drawLineToPreviousPoint(fifthpoints, g, i);
		}
		g.setColor(Color.black);
	}
	
	private void drawLineToPreviousPoint(ArrayList<Rectangle> points, Graphics g, int index) {
		if (index == 0) return;
		g.drawLine(points.get(index).x, points.get(index).y, points.get(index-1).x, points.get(index-1).y);
	}
	
	private void drawLegend(Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		int inc = 0;
		int spacing = 25;
		for (int i = 0; i < yLabels.size(); i++) {
			g.drawString(yLabels.get(i), 5, 15 + (inc*spacing));
			inc++;
		}
		g.setFont(new Font("Times New Roman", Font.BOLD, 25));
		g.drawString(Xlabel, startX + (Grapher.width - startX)/2, startY + (Grapher.height - startY)/2);
	}
	
	private void drawNumberLines(Graphics g, int scale) {
		int xDist = Grapher.width - startX;
		int yDist = startY;
		g.setFont(new Font("Times New Roman", Font.BOLD, 10));
		for (int i = startX; i < xDist; i = i + scale) {
			g.drawLine(i*spacing, startY + 4, i*spacing, startY - 4);
			g.drawString("" + (i - startX), startX + (i-startX)*spacing - 5, startY + 15);
		}
	/*	for (int i = startY; i >= 0; i = i - scale) {
			g.drawLine(startX - 4, i, startX + 4, i);
			g.drawString("" + Math.abs(i - startY), startX - 20, i*spacing);
		}
	*/	
	}
	
	public void addPoint(int x, int y) {
		points.add(new Rectangle(startX + x*spacing,startY - y, 2, 2));
	}
	
	public void addSecondPoint(int x, int y) {
		secondpoints.add(new Rectangle(startX + x,startY - y, 2, 2));
	}
	public void addThirdPoint(int x, int y) {
		thirdpoints.add(new Rectangle(startX + x,startY - y, 2, 2));
	}
	public void addFourthPoint(int x, int y) {
		fourthpoints.add(new Rectangle(startX + x,startY - y, 2, 2));
	}
	public void addFifthPoint(int x, int y) {
		fifthpoints.add(new Rectangle(startX + x,startY - y, 2, 2));
	}
	
	private void drawGraphBackground(Graphics g) {
		g.setColor(Color.black);
		g.drawLine(startX, 0, startX, Grapher.height);
		g.drawLine(0, startY, Grapher.width, startY);
	}
	
	private void drawLineSlits(Graphics g) {
		double xPos = 0;
		for (int i = 0; i < 2 * xMax; i++) {
			xPos = ((double)Grapher.width / (2*xMax));
			g.drawLine((int)(i * xPos), startY - 4, (int)(i * xPos), startY + 4);
		}
	}
	
	public void run() {
		running = true;
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		// int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				renderGame();
			}
			// frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				// frames = 0;
			}
		}
	}
	
	public void renderGame() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		render(g);
		
		g.dispose();
		bs.show();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.run();
	}

	public static void main(String[] args) {
		new GraphingPlotter();
	}

}
