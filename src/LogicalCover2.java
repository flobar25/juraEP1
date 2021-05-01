import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class LogicalCover2 extends PApplet {
    private PImage bg;

    @Override
    public void settings() {
        size(1080, 1080);
        smooth();
    }

    @Override
    public void setup() {
        colorMode(HSB, 360, 100, 100, 100);
//        noStroke();
        noLoop();
        rectMode(CENTER);
        frameRate(1);
        bg = loadImage("light-blue-paper-texture.jpg").get(0, 0, width, height);
    }

    @Override
    public void draw() {
        var ROTATIONS_MAX = 1000.0f;
        var DIVISION = 4.0f;
        var FRAME_SIZE = width;
        var POINTS_MAX = 20;
        var NOISE_SEED = 78950423890l;
        var NOISE_X_QUOTIENT = 0.4f;
        var NOISE_Y_QUOTIENT = 0.2f;

        noiseSeed(NOISE_SEED);
        strokeWeight(1);

        // draw background
        blendMode(BLEND);
//        background(0, 0, 0);
        background(217,72,76);
//        background(bg);

        translate(width / 2.0f, height / 2.0f);
//        noFill();
        noStroke();
        blendMode(BLEND);

        for (int rotationCount = 0; rotationCount < ROTATIONS_MAX; rotationCount++) {
//            stroke(map(rotationCount, 0, ROTATIONS_MAX, 0, 360),
//                    map(rotationCount, 0, ROTATIONS_MAX, 0, 100),
//                    map(rotationCount, 0, ROTATIONS_MAX, 0, 100),
//                    60);
            fill(map(rotationCount, 0, ROTATIONS_MAX, 0, 50),
                    map(rotationCount, 0, ROTATIONS_MAX, 0, 70),
                    map(rotationCount, 0, ROTATIONS_MAX, 0, 100),
                    3);
            pushMatrix();
            var rotateRatio = map(rotationCount, 0, ROTATIONS_MAX, 0.0f, 1.0f);
            rotate(TWO_PI * rotateRatio);
            var radiusBase = FRAME_SIZE * 0.5f * rotateRatio;
            PShape ellipse = this.createShape(31, new float[]{0.0F, 0.0F, FRAME_SIZE * 0.9f * rotateRatio, FRAME_SIZE * 0.045f});
            this.shape(ellipse);

//            beginShape();
//            for (int pointsCount = 0; pointsCount < POINTS_MAX; ++pointsCount) {
//                float pointRatio = map(pointsCount, 0, POINTS_MAX, 0.0f, 1.0f);
//                float noiseValX = map(noise(pointsCount * NOISE_X_QUOTIENT * rotationCount), 0.0f, 1.0f, 0.6f, 1.4f);
//                float noiseValY = map(noise(pointsCount * NOISE_Y_QUOTIENT * rotationCount), 0.0f, 1.0f, 0.6f, 1.4f);
//                float x = radiusBase * noiseValX * rotateRatio * cos(TWO_PI * pointRatio);
//                float y = radiusBase * noiseValY * rotateRatio * sin(TWO_PI * pointRatio);
//                vertex(x, y);
//            }
//            endShape(CLOSE);

            popMatrix();
        }

    }

    @Override
    public void mouseClicked() {
        save("/Users/florentbariod/temp/output.tif");
    }

    public static void main(String... args) {
        LogicalCover2 pt = new LogicalCover2();
        PApplet.runSketch(new String[]{"LogicalCover2"}, pt);
    }
}

