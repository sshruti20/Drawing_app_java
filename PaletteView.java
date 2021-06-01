
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

import javax.imageio.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
enum Tool {
    select, erase, line, circle, rectangle, bucket;
} //duplicated!!
*/

public class PaletteView extends JPanel implements IView {

    private Model model;
    private boolean toggled = false;

    private JButton getButton(String path, Tool t) {
        JButton b = new JButton();
        b.setIcon(new ImageIcon(path));
        b.setMargin(new Insets(10, 10, 10, 10));
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.clearSelectedShape();
                model.setCurrentTool(t);
            }
        });
        return b;
    }

    public PaletteView(Model model1) {
        model = model1;

        //this.setBackground(Color.RED); //DEBUGGING TESTING
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(200, 700));

        JToolBar drawingToolBar = new JToolBar(JToolBar.VERTICAL);
        JToolBar colorToolBar = new JToolBar(JToolBar.VERTICAL);
        JToolBar thicknessToolBar = new JToolBar(JToolBar.VERTICAL);
        thicknessToolBar.setPreferredSize(new Dimension(100, 150));
        this.add(drawingToolBar);
        this.add(colorToolBar);
        this.add(thicknessToolBar);

        JPanel drawingTools = new JPanel(new GridLayout(3, 2, 4, 4));
        drawingTools.setPreferredSize(new Dimension(100, 150));

        /**** CREATING DRAWING drawingTools BUTTON -- START ****/
        // create Selection button
        JButton selection = getButton("img/selection.png", Tool.select);
        drawingTools.add(selection);

        // create Eraser button
        JButton eraser = getButton("img/eraser.png", Tool.erase);
        drawingTools.add(eraser);

        // create Line button
        JButton line = getButton("img/line.png", Tool.line);
        drawingTools.add(line);

        // create Circle button
        JButton circle = getButton("img/circle.png", Tool.circle);
        drawingTools.add(circle);

        // create Rectangle button
        JButton rectangle = getButton("img/rectangle.png", Tool.rectangle);
        drawingTools.add(rectangle);

        // create Bucket button
        JButton bucket = getButton("img/bucket.png", Tool.bucket);
        drawingTools.add(bucket);

        /**** CREATING DRAWING drawingTools BUTTON -- END ****/

        // Add color pallete
        // Reference:
        // https://stackoverflow.com/questions/26565166/how-to-display-a-color-selector-when-clicking-a-button
        JButton colorButton = new JButton();
        JColorChooser colorChooser = new JColorChooser();
        colorChooser.setBorder(null);
        int colorButtonWidth = 50;
        int colorButtonHeight = 30;
        colorButton.setMinimumSize(new Dimension(colorButtonWidth, colorButtonHeight));
        colorButton.setPreferredSize(new Dimension(colorButtonWidth, colorButtonHeight));

        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color newColor = colorChooser.getColor();
                colorButton.setBackground(newColor);
            }
        });

        drawingToolBar.add(drawingTools);

        // PaletteView current = this;
        JToolBar current = colorToolBar;
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (toggled) {
                    current.remove(colorChooser);
                } else {
                    colorChooser.setBounds(colorButton.getX(), colorButton.getY() + 20, 600, 300);
                    colorChooser.setVisible(true);
                    current.add(colorChooser);

                    model.setCurrentColor(colorChooser.getColor());
                }
                toggled = !toggled;
                current.validate();
                current.repaint();
            }
        });
        // this.add(colorButton);
        colorToolBar.add(colorButton);

        /**** CODE FOR THICKNESS LINES -- START ****/
        JPanel lines = new JPanel(new GridLayout(3, 1, 4, 4));
        lines.setPreferredSize(new Dimension(100, 150));

        // create Line1 button
        JButton line1 = new JButton();
        line1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Line 1 tool clicked");

                model.setCurrentThickness(1);
				if(model.currentTool == Tool.select) {
					model.setSelectedShapeThickness(1);
				}
            }
        });
        // img = ImageIO.read(getClass().getResource("img/line1.png"));
        line1.setIcon(new ImageIcon("img/line1.png"));
        line1.setMargin(new Insets(10, 0, 10, 0));
        lines.add(line1);

        // create Line2 button
        JButton line2 = new JButton();
        line2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Line 2 tool clicked");

                model.setCurrentThickness(2);
				if(model.currentTool == Tool.select) {
					model.setSelectedShapeThickness(2);
				}
            }
        });
        // img = ImageIO.read(getClass().getResource("img/line2.png"));
        line2.setIcon(new ImageIcon("img/line2.png"));
        line2.setMargin(new Insets(10, 0, 10, 0));
        lines.add(line2);

        // create Line2 button
        JButton line3 = new JButton();
        line3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Line 3 tool clicked");

                model.setCurrentThickness(3);
				if(model.currentTool == Tool.select) {
					model.setSelectedShapeThickness(3);
				}
            }
        });
        // img = ImageIO.read(getClass().getResource("img/line3.png"));
        line3.setIcon(new ImageIcon("img/line3.png"));
        line3.setMargin(new Insets(10, 0, 10, 0));
        lines.add(line3);

        thicknessToolBar.add(lines);
        /**** CODE FOR THICKNESS LINES -- END ****/

        // define the layout
        // http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html

        
        this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                System.out.println("aaaaaaaa");
            }
        });

        this.setVisible(true);

    }

    public void updateView() {

    }

}
