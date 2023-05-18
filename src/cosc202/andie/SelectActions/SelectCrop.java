package cosc202.andie.SelectActions;

import cosc202.andie.ImagePanel;
import cosc202.andie.ImageOperations.Transformations.CropImage;

/**
 * <p>
 * Listener to get when user selects an area of the image and crops this area.
 * </p>
 * 
 * <p>
 * Extends on {@link SelectAction} which handles when user selecting area of screen.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Daniel Dachs
 * @version 1.0
 */

public class SelectCrop extends SelectAction {

    public SelectCrop(ImagePanel target) {
        super(target);

    }

    @Override
    protected void apply(SelectedArea sa) {
        target.getImage().apply(new CropImage(sa));
    }
}
