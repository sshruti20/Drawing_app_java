import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.event.*;

/*
enum Tool {
    select, erase, line, circle, rectangle, bucket;
}
*/

public class CanvasView extends JPanel implements IView {
    private Model model;
	
	private double width = 800;
	private double height = 700;

	private int originX;
	private int originY;

	public void drawAllShapes(Graphics2D gc) {

		for (JShape s: model.shapes) {
			if (s.fillColor != null) {
				gc.setColor(s.fillColor);
				gc.fill(s.shape);
			}

			gc.setStroke(new BasicStroke(model.convertThicknessToPx(s.thickness)));
			gc.setColor(s.strokeColor);
			//Reference: https://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
			if (model.selectedShape == s) {
				float dash1[] = {10.0f};
    			gc.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER,
				10.0f, dash1, 0.0f));
			}

			gc.draw(s.shape);
		}

		if (model.currentShape != null) {
			gc.setStroke(new BasicStroke(model.convertThicknessToPx(model.currentShape.thickness)));
			gc.setColor(model.currentColor);
			gc.draw(model.currentShape.shape);
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D gc = (Graphics2D) g;
		drawAllShapes(gc);
	}

	public void updateView() {
		this.repaint();
	}

    public CanvasView(Model model1) {
        model = model1;

        this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setPreferredSize(new Dimension(700, 400));

		Tool currentTool = model.currentTool;

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				originX = (int) e.getX();
				originY = (int) e.getY();

				System.out.println("Mouse Pressed");

				if (currentTool == Tool.select) {
					model.selectShape((int)originX, (int)originY);
				} else if (model.currentTool == Tool.erase) {
					model.eraseShape(originX, originY);
				} else if (model.currentTool == Tool.bucket) {
					model.fillShape(originX, originY);
				} else if (model.currentTool == Tool.rectangle || 
							model.currentTool == Tool.circle || 
							model.currentTool == Tool.line) {
					model.drawShape(0, 0, 0, 0);
				}
			}

			//DEBUGGING TESTING 
			@Override
			public void mouseEntered (MouseEvent evt) {
				System.out.println("mouseEntered");
			}
			@Override
			public void mouseExited (MouseEvent evt) {
				System.out.println("mouseExited");
			}
		
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Mouse Released");
				if (model.currentTool == Tool.rectangle || 
					model.currentTool == Tool.circle || 
					model.currentTool == Tool.line) {
						model.addShape();
					}
			}
		});

		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println("Mouse Dragged");
				int x = (int) e.getX();
				int y = (int) e.getY();

				if (model.currentTool == Tool.select) {
					model.moveShape(x, y);
				} else if (model.currentTool == Tool.rectangle || 
							model.currentTool == Tool.circle || 
							model.currentTool == Tool.line) {
								model.drawShape((int)originX, (int)originY, x, y);
						}
			}
		});
		
	}
}
