import java.awt.*;
import java.util.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D;

enum Tool {
    select, erase, line, circle, rectangle, bucket;
}

//Reference: code taken from sample code hellomvc1

class JShape implements Serializable {
    Shape shape;
    Color strokeColor;
    Color fillColor;
    Integer thickness;

    JShape(Shape s, Color sc, Integer t) {
        shape = s;
        strokeColor = sc;
        thickness = t;
        fillColor = null;
    }
}

public class Model {
    public Tool currentTool = Tool.select;
    public Color currentColor = Color.BLACK;
    public Integer currentThickness;
    public JShape currentShape = null;
	public JShape selectedShape = null;
    public ArrayList<IView> views;
    public ArrayList<JShape> shapes;
    
    public double displacementX;
    public double displacementY;

    public Model() {
        views = new ArrayList<IView>();
        shapes = new ArrayList<JShape>();

        currentTool = Tool.select;
        currentColor = Color.BLACK;
        currentThickness = 10;
    }

    public void addView(IView view) {
		views.add(view);
		view.updateView();
	}

    public void removeView(IView view) {
		views.remove(view);
		view.updateView();
	}

    public void notifyViews() {
        for (IView view: this.views) {
            view.updateView();
        }
    }

    public Integer convertThicknessToPx (Integer t) {
        if (t == 1) {return 3;}
        else if (t == 2) {return 6;}
        else if (t == 3) {return 11;}
        else {return 1;} //random thickness, should not happen
    }

    public void setCurrentTool(Tool t) {
        currentTool = t;
        notifyViews();
    }

    public void setCurrentShape(JShape s) {
		currentShape = s;
		notifyViews();
	}

    public void setCurrentColor(Color c) {
        currentColor = c;
        if (selectedShape != null) { selectedShape.fillColor = c;}
        if (currentShape != null) { currentShape.fillColor = c;}
        notifyViews();
    }

    public void setCurrentThickness(Integer t) {
        currentThickness = t;
        notifyViews();
    }

    public void setSelectedShapeStrokeColor(Color c) {
        if (selectedShape != null) {selectedShape.strokeColor = c;}
        notifyViews();
    }

	public void setSelectedShapeThickness(int t) {
		if(selectedShape != null) {
			selectedShape.thickness = convertThicknessToPx(t);
		}
		notifyViews();
	}

	public void loadShapes(ArrayList<JShape> s) {
		shapes = s;
		notifyViews();
	}

    public void drawShape(int x1, int y1, int x2, int y2) {
		int originX = Math.min(x1, x2);
		int originY = Math.min(y1, y2);
		int width = Math.abs(x1 - x2);
		int height = Math.abs(y1 - y2);

		if (currentTool == Tool.rectangle) {
            Shape rect = new Rectangle2D.Float(originX, originY, width, height);
			currentShape = new JShape(rect, currentColor, currentThickness);
		} else if (currentTool == Tool.circle) {
            int radius = Math.max(width, height);
            Shape circle = new Ellipse2D.Float(originX, originY, radius, radius);
			currentShape = new JShape(circle, currentColor, currentThickness);
		} else if (currentTool == Tool.line) {
            Shape line = new Line2D.Float(x1, y1, x2, y2);
			currentShape = new JShape(line, currentColor, currentThickness);
		}
		notifyViews();
	}

    public void addShape() {
		shapes.add(currentShape);
		currentShape = null;
		notifyViews();
	}

    public void finishedDrawingShape() {
        shapes.add(currentShape);
        currentShape = null;
        notifyViews();
    }

    public void clearSelectedShape() {
        selectedShape = null;
        notifyViews();
    }

    public void selectShape(int x, int y) {
        
        for (int i = 0; i < shapes.size(); i++) {
            Shape c = shapes.get(i).shape;
            if (c.contains(x, y) || c.intersects(x-1, y-1, 2, 2)) {
                clearSelectedShape();
                selectedShape = shapes.get(i);
                currentColor = selectedShape.strokeColor;
                currentThickness = selectedShape.thickness;
            }
        }

        if (selectedShape != null) {
            displacementX = selectedShape.shape.getBounds().getX() - x;
            displacementY = selectedShape.shape.getBounds().getY() - y;
        }
        notifyViews();
    }

    public void moveShape(int x, int y) {
        if (selectedShape != null) {
			double oldX = selectedShape.shape.getBounds().getX();
			double oldY = selectedShape.shape.getBounds().getY();

			AffineTransform tx = new AffineTransform();
			tx.translate(x - oldX + displacementX, y - oldY + displacementY);
			selectedShape.shape = tx.createTransformedShape(selectedShape.shape);
		}
		notifyViews();
    }

    public void eraseShape(int x, int y) {
        JShape temp = null;
        for (int i = 0; i < shapes.size(); i++) {
            Shape c = shapes.get(i).shape;
            if (c.contains(x, y) || c.intersects(x-1, y-1, 2, 2)) {
                temp = shapes.get(i);
            }
        }

        if (temp != null) {
            shapes.remove(temp);
        }

        notifyViews();
    }

    public void fillShape(int x, int y) {
        JShape temp = null;
        for (int i = 0; i < shapes.size(); i++) {
            Shape c = shapes.get(i).shape;
            if (c.contains(x, y) || c.intersects(x-1, y-1, 2, 2)) {
                temp = shapes.get(i);;
            }
        }

        if (temp != null) {
            temp.fillColor = currentColor;
        }
        
        notifyViews();
    }
}

