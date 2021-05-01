import processing.core.PApplet;

public class LogicalCover extends PApplet {

    @Override
    public void settings() {
        size(1080, 1080);
        smooth(8);
    }

    @Override
    public void setup() {
        colorMode(HSB, 360, 100, 100, 100);
        noStroke();

        //  noLoop();
        frameRate(1);
    }

    @Override
    public void draw() {
        translate(width / 2, height / 2);
        background(100);
        fill(255, 0, 0);
        var shape = createShape(ELLIPSE, 0.0f, 0.0f, 100.0f, 50.0f);
        shape(shape);
//        saveFrame("frames/####.png");
//        exit();
    }

    public static void main(String... args) {
        LogicalCover pt = new LogicalCover();
        PApplet.runSketch(new String[]{"LogicalCover"}, pt);
    }
}

