import javax.swing.*;
import java.awt.Dimension;
import java.awt.*;

import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main {

    static class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JMenuItem mi = (JMenuItem)e.getSource();
            System.out.println("MenuBar item clicked: " + mi.getText());
        }
    }

     // create a menu item listener
     //private static 
    public static void main(String[] args) {

        //Reference: Code taken from sample code folder hellomvc1
        JFrame frame = new JFrame("JSketch");

        MenuItemListener menuItemListener = new MenuItemListener();
		
        Model model = new Model();
        PaletteView pview = new PaletteView(model);
        CanvasView canvasView = new CanvasView(model);
        model.addView(pview);
        model.addView(canvasView);
    

        //View view = new View(model);
        //model.setView(view);
		//model.addView(canvasView);

        /*** Creating Menus -- START */
        JMenu menu = new JMenu("File");		
        for (String s: new String[] {"New", "Load", "Save"})
        {
            JMenuItem mi = new JMenuItem(s);
            mi.addActionListener(menuItemListener);
            menu.add(mi);
        }

        JMenu viewMenu = new JMenu("View");		
        for (String s: new String[] {"New", "Load", "Save"})
        {
            JMenuItem mi = new JMenuItem(s);
            mi.addActionListener(menuItemListener);
            viewMenu.add(mi);
        }

        JMenuBar menubar = new JMenuBar();
        menubar.add(menu);
        menubar.add(viewMenu);

        frame.setJMenuBar(menubar);
        /*** Creating Menus -- END */

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        
        frame.getContentPane().add(canvasView);
        frame.getContentPane().add(pview);

        // setup window
        frame.setMinimumSize(new Dimension(1200,800));
        frame.setPreferredSize(new Dimension(900,800));
        frame.setLayout(new FlowLayout());
        //frame.pack();
        //frame.setFocusable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
    }   
    
}