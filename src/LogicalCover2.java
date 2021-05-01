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
        var ROTATIONS_MAX = 100.0f;
        var DIVISION = 2;
        var FRAME_SIZE = width;
        var POINTS_MAX = 2000;
        var NOISE_SEED = 78950423890l;
        var NOISE_X_QUOTIENT = 0.4f;
        var NOISE_Y_QUOTIENT = 0.2f;

        noiseSeed(NOISE_SEED);
        strokeWeight(1);

        // draw background
        blendMode(BLEND);
//        background(0, 0, 0);
//        background(217,72,76);
//        background(bg);

//        translate(width / 2.0f, height / 2.0f);
//        noFill();
//        noStroke();
        blendMode(BLEND);


        for (int i = 0; i < DIVISION / 2; i++) {
            pushMatrix();
            translate(i * (width / (DIVISION / 2)), 0);
            for (var j = 0; j < DIVISION / 2; j++) {
                pushMatrix();
                translate(0, j * (height / (DIVISION / 2)));
                InputParameters inputParameters = new InputParameters();
                inputParameters.frameSize = FRAME_SIZE / (DIVISION / 2);
                inputParameters.noiseSeed = NOISE_SEED + i + j;
                inputParameters.rotationsMax = ROTATIONS_MAX;
                inputParameters.noiseXRatio = NOISE_X_QUOTIENT;
                inputParameters.noiseYRatio = NOISE_Y_QUOTIENT;
                inputParameters.pointsMax = POINTS_MAX;
                inputParameters.h = 217;
                inputParameters.s = 72;
                inputParameters.b = 76;
                drawInstance(inputParameters);
                popMatrix();
            }
            popMatrix();
        }
    }

    private void drawInstance(InputParameters input) {
        noiseSeed(input.noiseSeed);
        translate(input.frameSize / 2.0f, input.frameSize / 2.0f);
        noStroke();
        fill(input.h, input.s, input.b);
        square(0, 0, input.frameSize);

        for (int rotationCount = 0; rotationCount < input.rotationsMax; rotationCount++) {
            stroke(map(rotationCount, 0, input.rotationsMax, 0, 50),
                    map(rotationCount, 0, input.rotationsMax, 0, 70),
                    map(rotationCount, 0, input.rotationsMax, 0, 100),
                    60);
            fill(map(rotationCount, 0, input.rotationsMax, 0, 50),
                    map(rotationCount, 0, input.rotationsMax, 0, 70),
                    map(rotationCount, 0, input.rotationsMax, 0, 100),
                    3);
            pushMatrix();
            var rotateRatio = map(rotationCount, 0, input.rotationsMax, 0.0f, 1.0f);
            rotate(TWO_PI * rotateRatio);
            var radiusBase = input.frameSize * 0.5f * rotateRatio;
//            PShape ellipse = this.createShape(31, new float[]{0.0F, 0.0F, frameSize * 0.9f * rotateRatio, frameSize * 0.045f});
//            this.shape(ellipse);

            beginShape();
            for (int pointsCount = 0; pointsCount < input.pointsMax; ++pointsCount) {
                float pointRatio = map(pointsCount, 0, input.pointsMax, 0.0f, 1.0f);
                float noiseValX = map(noise(pointsCount * input.noiseXRatio * rotationCount), 0.0f, 1.0f, 0.6f, 1.4f);
                float noiseValY = map(noise(pointsCount * input.noiseYRatio * rotationCount), 0.0f, 1.0f, 0.6f, 1.4f);
                float x = radiusBase * noiseValX * rotateRatio * cos(TWO_PI * pointRatio);
                float y = radiusBase * noiseValY * rotateRatio * sin(TWO_PI * pointRatio);
                vertex(x, y);
            }
            endShape(CLOSE);

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

    class InputParameters {
        float rotationsMax;
        int frameSize;
        long noiseSeed;
        int pointsMax;
        float noiseXRatio;
        float noiseYRatio;
        int h;
        int s;
        int b;
    }
}

