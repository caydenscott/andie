package cosc202.andie;

import java.awt.image.BufferedImage;

public class BrightnessAndContrast implements ImageOperation, java.io.Serializable {

    private double brightness;
    private double contrast;

    public BrightnessAndContrast(int brightness, int contrast){
        this.brightness = brightness;
        this.contrast = contrast;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'apply'");
        for(int i = 0; i < input.getHeight(); ++i){
            for(int j = 0; j < input.getWidth(); ++j){
                //check for greyscale?
                int argb = input.getRGB(j, i);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                int adjustR = mathAdjustment(r);
                int adjustG = mathAdjustment(g);
                int adjustB = mathAdjustment(b);

                argb = (a << 24) | (adjustR << 16) | (adjustG << 8) | adjustB;
                input.setRGB(j, i, argb);
            }
        }
        return input;
    }

    private int mathAdjustment(int v){
        int adjustV = 0;
        adjustV = (int) Math.round((1 + contrast/100) * (v - 127.5) + 127.5 * (1 + brightness/100));
        if(adjustV < 0){
            adjustV = 0;
        }else if(adjustV > 255){
            adjustV = 255;   
        }
        return adjustV;
    }




}
