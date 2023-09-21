package g61610.atl.ascii.controller;

import g61610.atl.ascii.model.AsciiPaint;

public class Application {
    private final AsciiPaint paint;

    public Application(int width, int height) {
        this.paint = new AsciiPaint(width,height);
    }

    public Application() {
        this.paint = new AsciiPaint();
    }

    public AsciiPaint getPaint() {
        return this.paint;
    }
}
