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
    public BufferedImage apply(BrightnessAndContrast brightandcontrast) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apply'");
    }


}
