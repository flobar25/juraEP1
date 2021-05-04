import controlP5.ControlP5;
import processing.core.PApplet;

public class LogicalCover2 extends PApplet {
    private ControlP5 controls;

    @Override
    public void settings() {
        size(1080, 1080);
        smooth();

    }

    @Override
    public void setup() {
        colorMode(HSB, 360, 100, 100, 100);
//        noLoop();
        rectMode(CENTER);
//        frameRate(1);
        controls = new ControlP5(this);
        controls.addSlider("sliderValue")
                .setPosition(100, 50)
                .setRange(0, 255)
        ;
    }

    @Override
    public void draw() {
        var ROTATIONS_MAX = 100.0f;
        var DIVISION = 2;
        var FRAME_SIZE = width;
        var POINTS_MAX = 200;
        var NOISE_SEED = 78950423890l;
        var NOISE_X_RATIO = 0.4f;
        var NOISE_Y_RATIO = 0.2f;

        noiseSeed(NOISE_SEED);
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
                inputParameters.noiseXRatio = NOISE_X_RATIO;
                inputParameters.noiseYRatio = NOISE_Y_RATIO;
                inputParameters.pointsMax = POINTS_MAX;
                inputParameters.backgroundColor = new HSB(217, 72, 76);
                inputParameters.startFillColor = new HSB(0, 0, 0);
                inputParameters.startLineColor = new HSB(0, 0, 0);
                inputParameters.endFillColor = new HSB(50, 70, 100, 3);
                inputParameters.endLineColor = new HSB(50, 70, 100, 60);
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
        fill(input.backgroundColor.h, input.backgroundColor.s, input.backgroundColor.b);
        square(0, 0, input.frameSize);

        for (int rotationCount = 0; rotationCount < input.rotationsMax; rotationCount++) {
            stroke(map(rotationCount, 0, input.rotationsMax, input.startLineColor.h, input.endLineColor.h),
                    map(rotationCount, 0, input.rotationsMax, input.startLineColor.s, input.endLineColor.s),
                    map(rotationCount, 0, input.rotationsMax, input.startLineColor.b, input.endLineColor.b),
                    input.endLineColor.a);
            fill(map(rotationCount, 0, input.rotationsMax, input.startFillColor.h, input.endFillColor.h),
                    map(rotationCount, 0, input.rotationsMax, input.startFillColor.s, input.endFillColor.s),
                    map(rotationCount, 0, input.rotationsMax, input.startFillColor.b, input.endFillColor.b),
                    input.endFillColor.a);
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

    class InputParameters {
        float rotationsMax;
        int frameSize;
        long noiseSeed;
        int pointsMax;
        float noiseXRatio;
        float noiseYRatio;
        HSB backgroundColor;
        HSB startLineColor;
        HSB startFillColor;
        HSB endLineColor;
        HSB endFillColor;
    }

    class HSB {
        int h;
        int s;
        int b;
        int a = 100;

        public HSB(int h, int s, int b) {
            this.h = h;
            this.s = s;
            this.b = b;
        }

        public HSB(int h, int s, int b, int a) {
            this.h = h;
            this.s = s;
            this.b = b;
            this.a = a;
        }
    }

    public static void main(String... args) {
        LogicalCover2 pt = new LogicalCover2();
        PApplet.runSketch(new String[]{"LogicalCover2"}, pt);
    }
}

