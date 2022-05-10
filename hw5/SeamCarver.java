import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    Picture image;

    public SeamCarver(Picture picture) {
        image = picture;
    }

    /**
     *
     * @return current picture
     */
    public Picture picture() {
        return image;
    }

    /**
     *
     * @return width of current picture
     */
    public int width() {
        return image.width();
    }

    /**
     *
     * @return height of current picture
     */
    public int height() {
        return image.height();
    }

    /**
     *
     * @param x
     * @param y
     * @return energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        Color color = image.get(x, y);

        return 0.0;
    }

    /**
     *
     * @return sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        return null;
    }

    /**
     *
     * @return sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        return null;
    }

    /**
     * remove horizontal seam from picture
     * @param seam
     */
    public void removeHorizontalSeam(int[] seam) {

    }

    /**
     * remove vertical seam from picture
     * @param seam
     */
    public void removeVerticalSeam(int[] seam) {

    }

}
