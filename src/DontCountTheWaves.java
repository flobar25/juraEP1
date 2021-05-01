import processing.core.PApplet;

public class DontCountTheWaves extends PApplet {
    Petals pt;

    @Override
    public void settings() {
        size(1080, 1080);
    }

    @Override
    public void setup() {
        colorMode(HSB, 360, 100, 100, 100);
        blendMode(SCREEN);
        noiseSeed(0);
        smooth();
        noStroke();
        //  noLoop();
        frameRate(1);

        pt = new Petals();
    }

    @Override
    public void draw() {
        background(0, 0, 0);
        translate(width / 2, height / 2);
        pt.drawPetals();
    }

    public static void main(String... args) {
        DontCountTheWaves pt = new DontCountTheWaves();
        PApplet.runSketch(new String[]{"ProcessingTest"}, pt);
    }

    class Petals {
        float divRotate = 8; // divide 360 degree
        float rotateMax = 30 * 360 * divRotate;  // draw while rotating
        float widthMax = 10; // repeat same shape with different ellipse size
        float basePetalSize = 500;
        float baseColor = map(random(1), 0, 1, 210, 360);

        float noiseShpStart = random(100); //random(0.5, 3.0);
        float noiseHueStart = random(100);
        float noiseBriStart = random(100);
        float noiseAlpStart = random(100);
        float noiseSatStart = random(100);

        void drawPetals() {
            for (int currentWidth = 1; currentWidth <= widthMax; ++currentWidth) {

                float noiseHue = noiseHueStart + currentWidth / 300;
                float noiseSat = noiseSatStart;
                float noiseBri = noiseBriStart;
                float noiseAlp = noiseAlpStart;
                float noiseShp = noiseShpStart;
                float sumRotation = 0;

                for (int currentRotate = 0; currentRotate < rotateMax; ++currentRotate) {

                    // rotate fixed degree and calculate the plot point
                    float rotation = 1f / divRotate;
                    canvasRotation(rotation);
                    sumRotation += rotation;
                    float idxW = 0f;
                    float idxH = basePetalSize * sin(radians(sumRotation / (50f + 5 * cos(radians(2 * currentRotate))))) * map(noise(noiseShp), 0.0f, 1.0f, 0.8f, 1.2f);
                    ;

                    float brushHue = (baseColor + 360 + map(noise(noiseHue), 0.0f, 1.0f, -60.0f, 60)) % 360;
                    float brushSat = map(noise(noiseSat), 0.0f, 1.0f, 50.0f, 100.0f);
                    float brushSiz = map(noise(noiseBri), 0.0f, 1.0f, 0.0f, 1.0f * currentWidth);
                    float brushBri = map(noise(noiseBri), 0.0f, 1.0f, 0.0f, 100.0f) / currentWidth;
                    float brushAlp = map(noise(noiseAlp), 0.0f, 1.0f, 0.0f, 100.0f);
                    drawLine(idxW, idxH, brushHue, brushSat, brushBri, brushAlp, brushSiz);

                    noiseHue += 0.001;
                    noiseSat += 0.003;
                    noiseBri += 0.005;
                    noiseAlp += 0.005;
                    noiseShp += 0.002;

                }

                canvasRotation(-rotateMax);

            }


        }

        void drawLine(float idxW, float idxH, float brushHue, float brushSat, float brushBri, float brushAlp, float brushSiz) {
            pushMatrix();
            translate(idxW, idxH);
            fill(brushHue, brushSat, brushBri, brushAlp);
            ellipse(0, 0, brushSiz, brushSiz);
            popMatrix();
        }

        void canvasRotation(float degrees) {
            rotate(radians(degrees));
        }
    }
}