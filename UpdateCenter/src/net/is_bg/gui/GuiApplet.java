package net.is_bg.gui;



import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GuiApplet extends JApplet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 6835831113643715324L;
	JButton jbtnOne; 
	JButton jbtnTwo; 
	 
	JLabel jlab; 
	 
	  public void init() { 
	    try { 
	      SwingUtilities.invokeAndWait(new Runnable () { 
	        public void run() { 
	          guiInit(); // initialize the GUI 
	        } 
	      }); 
	    } catch(Exception exc) { 
	      System.out.println("Can't create because of "+ exc); 
	    } 
	  } 
	 
	  // Called second, after init().  Also called 
	  // whenever the applet is restarted.  
	  public void start() { 
	    // Not used by this applet. 
	  } 
	 
	  // Called when the applet is stopped. 
	  public void stop() { 
	    // Not used by this applet. 
	  } 
	 
	  // Called when applet is terminated.  This is 
	  // the last method executed. 
	  public void destroy() { 
	    // Not used by this applet. 
	  } 
	 
	  // Setup and initialize the GUI.  
	  private void guiInit() { 
	    // Set the applet to use flow layout. 
	    setLayout(new FlowLayout()); 
	 
	    // Create two buttons and a label. 
	    jbtnOne = new JButton("One"); 
	    jbtnTwo = new JButton("Two"); 
	 
	    jlab = new JLabel("Press a button."); 
	 
	    // Add action listeners for the buttons. 
	    jbtnOne.addActionListener(new ActionListener() {      
	      public void actionPerformed(ActionEvent le) {  
	        jlab.setText("Button One pressed.");  
	      }      
	    });      
	 
	    jbtnTwo.addActionListener(new ActionListener() {      
	      public void actionPerformed(ActionEvent le) {  
	        jlab.setText("Button Two pressed.");  
	      }      
	    });      
	 
	    // Add the components to the applet's content pane. 
	    getContentPane().add(jbtnOne); 
	    getContentPane().add(jbtnTwo); 
	    getContentPane().add(jlab);     
	  } 

	
	
}
