package g61610.atl.ascii.controller;

import g61610.atl.ascii.model.AsciiPaint;
import g61610.atl.ascii.model.Shape;
import g61610.atl.ascii.view.View;

import java.util.List;

public class Application {
    private final AsciiPaint paint;

    public Application(int width, int height) {
        this.paint = new AsciiPaint(width,height);
    }

    public Application() {
        this.paint = new AsciiPaint();
    }

    public void moveShape(int index,int offsetX, int offsetY) {
        this.paint.moveShape(index,offsetX,offsetY);
    }

    public void changeShapeColor(int index,char newcolor) {
        this.paint.changeShapeColor(index,newcolor);
    }

    public void newSquare(int x,int y,double side, char color) {
        this.paint.newSquare(x,y,side,color);
    }

    public void newCircle(int x,int y,double radius,char color) {
        this.paint.newCircle(x,y,radius,color);
    }

    public void newRectangle(int x,int y,double width,double height,char color) {
        this.paint.newRectangle(x,y,width,height,color);
    }

    public List<Shape> getShapes() {
        return this.paint.getShapes();
    }

    public void displayBoard() {
        View.show(this.paint);
    }
}
