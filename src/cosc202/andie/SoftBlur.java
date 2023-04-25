package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.image.*;

public class SoftBlur implements ImageOperation, java.io.Serializable {

    SoftBlur() {}
    
    public BufferedImage apply(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();
    
        int padAmount = 1; // Increase padding size for mirror padding
    
        // Create a new image with mirror padding around the edges
        BufferedImage paddedInput = new BufferedImage(width + padAmount * 2, height + padAmount * 2, input.getType());
        Graphics2D g = paddedInput.createGraphics();
        g.drawImage(input, padAmount, padAmount, null);
        // Mirror left and right edges
        g.drawImage(input, 0, padAmount, padAmount, height + padAmount, 0, 0, padAmount, height, null);
        g.drawImage(input, width + padAmount, padAmount, width + padAmount * 2, height + padAmount, width - padAmount, 0, width, height, null);
        // Mirror top and bottom edges
        g.drawImage(input, padAmount, 0, width + padAmount, padAmount, 0, 0, width, padAmount, null);
        g.drawImage(input, padAmount, height + padAmount, width + padAmount, height + padAmount * 2, 0, height - padAmount, width, height, null);
        // Mirror corners
        g.drawImage(input, 0, 0, padAmount, padAmount, 0, 0, padAmount, padAmount, null);
        g.drawImage(input, width + padAmount, 0, width + padAmount * 2, padAmount, width - padAmount, 0, width, padAmount, null);
        g.drawImage(input, 0, height + padAmount, padAmount, height + padAmount * 2, 0, height - padAmount, padAmount, height, null);
        g.drawImage(input, width + padAmount, height + padAmount, width + padAmount * 2, height + padAmount * 2, width - padAmount, height - padAmount, width, height, null);
        g.dispose();
    
        BufferedImage output = new BufferedImage(width, height, input.getType());
        // The values for the kernel as a 9-element array
        float [] array = { 0 , 1/8.0f, 0 , 1/8.0f, 1/2.0f, 1/8.0f, 0 , 1/8.0f, 0 };
        Kernel kernel = new Kernel(3, 3, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage filteredImage = convOp.filter(paddedInput, null);
        
        // Create a new image to store the filtered pixels without padding
        output = new BufferedImage(width, height, input.getType());
        g = output.createGraphics();
        g.drawImage(filteredImage, -padAmount, -padAmount, null);
        g.dispose();
        
        return output;
    }
}