package cosc202.andie;

public class SelectedArea {
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public SelectedArea(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX  = endX;
        this.endY = endY;
    }

    public SelectedArea() {
        this(0, 0, 0, 0);
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY){
        this.startY = startY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY){
        this.endY = endY;
    }

    /**
     * Get the start point of the selected area
     * @return
     */
    public int[] getStart() {
        return new int[] {startX, startY};
    }

    /**
     * Get the end point of the selected area
     * @return
     */
    public int[] getEnd() {
        return new int[] {endX, endY};
    }

    /**
     * Get the width of the selected area
     * @return
     */
    public int getWidth() {
        return endX - startX;
    }

    /**
     * Get the height of the selected area
     * @return
     */
    public int getHeight() {
        return endY - startY;
    }

    /**
     * Checking if input point is inside the selected area
     * @param x x-coorrinate of point
     * @param y y-coordinate of point
     * @return if point is inside the selected area
     */
    public boolean contains(int x, int y) {
        return (x >= startX && x <= endX && y >= startY && y <= endY);
    }
}
