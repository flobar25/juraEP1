import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class LogicalCover2 extends PApplet {
    private ControlP5 controls;
    boolean draw = false;

    @Override
    public void settings() {
        size(1080, 1080);
        smooth();

    }

    @Override
    public void setup() {
        colorMode(HSB, 360, 100, 100, 100);
        rectMode(CENTER);
        controls = new ControlP5(this);

        // shapes inputs
        controls.addTextfield("rotations").setPosition(20, 100);
        controls.addTextfield("divisions").setPosition(20, 150);
        controls.addTextfield("pointsMax").setPosition(20, 200);
        controls.addTextfield("noiseSeed").setPosition(20, 250);
        controls.addTextfield("noiseXRatio").setPosition(20, 300);
        controls.addTextfield("noiseYRatio").setPosition(20, 350);
        controls.addTextfield("strokeWeight").setPosition(20, 400);

        // colors inputs
        controls.addTextfield("backgroundH").setPosition(320, 100);
        controls.addTextfield("backgroundS").setPosition(320, 150);
        controls.addTextfield("backgroundB").setPosition(320, 200);
        controls.addTextfield("startFillH").setPosition(320, 250);
        controls.addTextfield("startFillS").setPosition(320, 300);
        controls.addTextfield("startFillB").setPosition(320, 350);
        controls.addTextfield("endFillH").setPosition(320, 400);
        controls.addTextfield("endFillS").setPosition(320, 450);
        controls.addTextfield("endFillB").setPosition(320, 500);
        controls.addTextfield("startLineH").setPosition(320, 550);
        controls.addTextfield("startLineS").setPosition(320, 600);
        controls.addTextfield("startLineB").setPosition(320, 650);
        controls.addTextfield("endLineH").setPosition(320, 700);
        controls.addTextfield("endLineS").setPosition(320, 750);
        controls.addTextfield("endLineB").setPosition(320, 800);
    }

    @Override
    public void draw() {
        if (!draw) {
            return;
        }
        var FRAME_SIZE = width;
        var ROTATIONS_MAX = parseIntegerInput("rotations", 100);
        var DIVISION = parseIntegerInput("divisions", 2);
        var POINTS_MAX = parseIntegerInput("pointsMax", 20);
        var NOISE_SEED = parseLongInput("noiseSeed", 78950423890l);
        var NOISE_X_RATIO = parseFloatInput("noiseXRatio", 0.4f);
        var NOISE_Y_RATIO = parseFloatInput("noiseYRatio", 0.2f);
        var STROKE_WEIGHT = parseIntegerInput("strokeWeight", 1);
        var BACKGROUND_H = parseIntegerInput("backgroundH", 217);
        var BACKGROUND_S = parseIntegerInput("backgroundS", 72);
        var BACKGROUND_B = parseIntegerInput("backgroundB", 76);
        var START_FILL_H = parseIntegerInput("startFillH", 0);
        var START_FILL_S = parseIntegerInput("startFillS", 0);
        var START_FILL_B = parseIntegerInput("startFillB", 0);
        var START_FILL_A = parseIntegerInput("startFillA", 3);
        var END_FILL_H = parseIntegerInput("endFillH", 50);
        var END_FILL_S = parseIntegerInput("endFillS", 70);
        var END_FILL_B = parseIntegerInput("endFillB", 100);
        var END_FILL_A = parseIntegerInput("endFillA", 3);
        var START_LINE_H = parseIntegerInput("startLineH", 0);
        var START_LINE_S = parseIntegerInput("startLineS", 0);
        var START_LINE_B = parseIntegerInput("startLineB", 0);
        var START_LINE_A = parseIntegerInput("startLineA", 60);
        var END_LINE_H = parseIntegerInput("endLineH", 50);
        var END_LINE_S = parseIntegerInput("endLineS", 70);
        var END_LINE_B = parseIntegerInput("endLineB", 100);
        var END_LINE_A = parseIntegerInput("startLineA", 60);

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
                inputParameters.strokeWeight = STROKE_WEIGHT;
                inputParameters.backgroundColor = new HSB(BACKGROUND_H, BACKGROUND_S, BACKGROUND_B);
                inputParameters.startFillColor = new HSB(START_FILL_H, START_FILL_S, START_FILL_B, START_FILL_A);
                inputParameters.startLineColor = new HSB(START_LINE_H, START_LINE_S, START_LINE_B, START_LINE_A);
                inputParameters.endFillColor = new HSB(END_FILL_H, END_FILL_S, END_FILL_B, END_FILL_A);
                inputParameters.endLineColor = new HSB(END_LINE_H, END_LINE_S, END_LINE_B, END_LINE_A);
                drawInstance(inputParameters);
                popMatrix();
            }
            popMatrix();
        }

        draw = false;
    }

    private void drawInstance(InputParameters input) {
        noiseSeed(input.noiseSeed);
        translate(input.frameSize / 2.0f, input.frameSize / 2.0f);
        noStroke();
        fill(input.backgroundColor.h, input.backgroundColor.s, input.backgroundColor.b);
        square(0, 0, input.frameSize);

        strokeWeight(input.strokeWeight);
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
    public void keyReleased(KeyEvent event) {
        switch (event.getKey()) {
            case 'z':
                draw = true;
                break;
            case 'x':
                draw = true;
                if (controls.isVisible()) {
                    controls.hide();
                } else {
                    controls.show();
                }
                break;
            case 'a':
                controls.hide();
                save("/Users/florentbariod/temp/output.tif");
                controls.show();
                break;
            default:
                break;
        }
    }

    private int parseIntegerInput(String inputName, int defaultValue) {
        try {
            return Integer.valueOf(controls.get(Textfield.class, inputName).getText());
        } catch (Exception e){
            return defaultValue;
        }
    }

    private long parseLongInput(String inputName, long defaultValue) {
        try {
            return Long.valueOf(controls.get(Textfield.class, inputName).getText());
        } catch (Exception e){
            return defaultValue;
        }
    }

    private float parseFloatInput(String inputName, float defaultValue) {
        try {
            return Float.valueOf(controls.get(Textfield.class, inputName).getText());
        } catch (Exception e){
            return defaultValue;
        }
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
        int strokeWeight;
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

