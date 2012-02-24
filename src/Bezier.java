package src;

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.io.*;

/****************/
/* class Bezier */                                                    
/****************/

public class Bezier extends Applet {
    
    public static final long serialVersionUID = 24362462L;
    static Frame myFrame=null;
    Button bClear;
    Button bRead;
    Button bElevate;
    Button bQuit;
    Panel  mainPanel;
    private MyGraphics myG;
    public Color paintColor, bkColor;
    public int borderSize;
    
    /*************************/
    /* Initialise the applet */
    /*************************/
    
    public Bezier()
    {
        borderSize = 10000;
        paintColor = Color.red;
        bkColor    = Color.lightGray;
        bClear     = new Button("Clear all");
        bRead      = new Button("Read sample points");
        bElevate    = new Button("Elevate once");
    }

    public void clearMe() {
	Graphics g = getGraphics();
        Dimension dimension = getSize();
        Color col = g.getColor();
        g.setColor(getBackground());
        g.fillRect(0, 0, dimension.width, dimension.height);
        g.setColor(col);
    }

    public void init() {
	//	System.out.println("Init method");

	setBackground(Color.gray);
        setLayout(new BorderLayout());
        mainPanel  = new Panel();
	mainPanel.setBackground(Color.lightGray);
        Panel panel2 = new Panel();
	panel2.setBackground(Color.lightGray);
        bRead = new Button("Read sample points");
	panel2.add(bRead);
        bElevate = new Button("Elevate once");
	panel2.add(bElevate);
        bClear = new Button("Clear all");
	panel2.add(bClear);
    if (myFrame != null)
    {
           bQuit = new Button("Quit");
           panel2.add(bQuit);
    }

	add("North",  panel2);
	add("South",  mainPanel);
        myG = new MyGraphics(paintColor, Color.yellow);
    

	bRead.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		int x, y;

		File f = new File(".","/src/sample.data");
		if (!f.exists()) throw new Error("Sample Point File Not Found"); 
		else {
		    // get rid of existing polygon
		    myG.clear(mainPanel, getBackground());
		    // clear screen
		    Graphics g = getGraphics();
		    g.setColor(paintColor);
		    clearMe();
		    // read in points from file
		    try{
			Reader r = new BufferedReader(
				   new InputStreamReader(
				   new FileInputStream(f)));
			StreamTokenizer st = new StreamTokenizer(r);
			
			int i=0;
			while (st.nextToken()!=st.TT_EOF) {
			    x = (int)(st.nval);
			    st.nextToken();
			    y = (int)(st.nval);
			    System.out.println("Read point (" + x + " , "+y+")");
			    myG.addPolyPoint(g, x, y);
			}		
		    }
		    catch(Exception exc){
			System.out.println("Cannot read from file"+f);
		    }
		    g.setPaintMode();
		}
	    }
	});
	
	bElevate.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		Graphics g = getGraphics();
		g.setColor(paintColor);
		clearMe();
		myG.elevateOnce(g);
		g.setPaintMode();
	  }
	});//elevate once

	bClear.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		// get rid of components
		myG.clear(mainPanel, getBackground());
		// clear screen
		Graphics g = getGraphics();
		g.setColor(paintColor);
		//System.out.println("Clear all");
		clearMe();
		g.setPaintMode();
	    }
	});
    if (myFrame != null)
    {//Quit button
      bQuit.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
               System.exit(0);
        }
      });
    }


	this.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		Graphics g = getGraphics();
		g.setColor(paintColor);
		int x = e.getX();
		int y = e.getY();
		// System.out.println("Cick at "+x+" "+y);
		myG.addPolyPoint(g, x, y);
		g.setPaintMode();
	    }
	});
    }

	
	
  /****************************************************************************/
  /* a standard overwriting of update()                                       */
  /****************************************************************************/
  public void update(Graphics g) { 
    paint(g); 
  }
  
  public void paint(Graphics g) {
    paintComponents(g); 
    //    System.out.println("MAIN PAINT");
    myG.redrawThePolygon(g);
  }

  public void stop() {
    // Stop the bouncer thread if necessary.
      System.out.println("stop");
  }  

  public static void main(String[] args) {
    Bezier myBezierApplet = new Bezier(); 
    myFrame = new Frame("Bezier degree application"); // create frame with title
    // Call applet's init method (since Java App does not
    // call it as a browser automatically does)
    myBezierApplet.init(); 

    // add applet to the frame
    myFrame.add(myBezierApplet, BorderLayout.CENTER);
    myFrame.pack(); // set window to appropriate size (for its elements)
    myFrame.setSize(600, 500);
    myFrame.setVisible(true); // usual step to make frame visible

  } // end main

}
