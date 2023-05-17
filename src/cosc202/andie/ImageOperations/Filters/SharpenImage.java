package cosc202.andie.ImageOperations.Filters;

import java.awt.image.*;

import cosc202.andie.ImageOperations.ImageOperation;

public class SharpenImage implements ImageOperation, java.io.Serializable {

    public SharpenImage() {}
    
    public BufferedImage apply(BufferedImage input) {
        // The values for the kernel as a 9-element array
        float [] array = { 0 , -1/2.0f, 0, -1/2.0f, 3, -1/2.0f, 0, -1/2.0f, 0 };
        Kernel kernel = new Kernel(3, 3, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);
        return output;
    }
}