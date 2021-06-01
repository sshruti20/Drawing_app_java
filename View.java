import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class View extends JPanel implements Observer {

    private Model model;

    /**
     * Create a new View.
     */
    public View(Model model) {
        // Set up the window.
        //this.setTitle("Your program title here!");
        this.setMinimumSize(new Dimension(128, 128));
        this.setSize(400, 400);
        //this.setDefaultCloseOperation(JPanel.EXIT_ON_CLOSE);

        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        //model.addObserver(this);

        //Code taken from sample code file WidgetDemo.java
        /*
        JMenu menu = new JMenu("File");		
        for (String s: new String[] {"New", "Load", "Save"})
        {
            JMenuItem mi = new JMenuItem(s);
            mi.addActionListener(menuItemListener);
            menu.add(mi);
        }
        JMenuBar menubar = new JMenuBar();
        menubar.add(menu);
        this.add(menubar);
        */

        setVisible(true);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        System.out.println("Model changed!");
    }

    //Code taken from sample code file WidgetDemo.java
    public class Widgets {

        public void main(String[] args) {
            Widgets demo = new Widgets();
        }
        
        JLabel label;
        
        //Code taken from sample code file WidgetDemo.java
        Widgets()
        {
            JFrame f = new JFrame("View");
            f.setSize(640, 480);
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            /*
            // create a button and add a listener for events
            JButton button = new JButton("My Button");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    label.setText("button");
                }
            });
            */
            
            /*
            // create a menu
            JMenu menu = new JMenu("File");		
            // create some menu choices
            for (String s: new String[] {"New", "Load", "Save"})
            {
                // add this menu item to the menu
                JMenuItem mi = new JMenuItem(s);
                // set the listener when events occur
                mi.addActionListener(menuItemListener);
                // add this menu item to the menu
                menu.add(mi);
            }
            JMenuBar menubar = new JMenuBar();
            menubar.add(menu);
            */
            
            // define the layout
            // http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html

            
            // add the widgets
            //f.add(menubar);

            f.setLayout(new FlowLayout());
                    
            f.setVisible(true);
        }
	
    } //Widgets end
    
}
