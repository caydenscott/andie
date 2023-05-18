package cosc202.andie.SelectActions;

import cosc202.andie.ImagePanel;
import cosc202.andie.ImageOperations.Transformations.CropImage;

public class SelectCrop extends SelectAction {

    public SelectCrop(ImagePanel target) {
        super(target);

    }

    @Override
    protected void apply(SelectedArea sa) {
        target.getImage().apply(new CropImage(sa));
    }
}
