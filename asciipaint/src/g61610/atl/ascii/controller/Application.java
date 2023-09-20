package g61610.atl.ascii.controller;

import g61610.atl.ascii.model.AsciiPaint;

public class Application {
    private AsciiPaint paint;

    public Application(int width, int height) {
        this.paint = new AsciiPaint(width,height);
    }
}
