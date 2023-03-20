package test.cosc202.andie;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import cosc202.andie.ImagePanel;

public class ImagePanelTest {
    /*
     * Just chcking that the testing framework works
     */
    @Test
    public void initialDummyTest() {

    }

    /*
     * checking that the initial zoom value is 100
     */
    @Test 
    public void getZoomInInitialValue() {
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100.0, testPanel.getZoom());
    }
    
    /*
     * checking that change zoom worked, mostly useless test but hey
     */
    @Test
    public void getZoomAftersetZoom() {
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(10.0);

        Assertions.assertTrue(testPanel.getZoom() == 10.0);
        //Assertions.assertFalse(testPanel.getZoom() == 100.0);
    }
}
