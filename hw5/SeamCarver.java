import edu.princeton.cs.algs4.Picture;


public class SeamCarver {

    Picture image;
    double[][] energies;
    int width;
    int height;

    public SeamCarver(Picture picture) {
        image = picture;
        width = picture().width();
        height = picture.height();
        calEnergies();
    }

    private void calEnergies() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                energies[i][j] = calPixelEnergy(i, j);
            }
        }
    }

    private double calPixelEnergy(int x, int y) {
        double xGradient = calXGradient(x, y);
        double yGradient = calYGradient(x, y);
        return xGradient + yGradient;
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
        return width;
    }

    /**
     *
     * @return height of current picture
     */
    public int height() {
        return height;
    }

    /**
     *
     * @param x
     * @param y
     * @return energy of pixel at column x and row y
     */
    public double energy(int x, int y) {

        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException();
        }
        return energies[x][y];
    }

    private double calXGradient(int x, int y) {

        int leftX = x - 1;
        int rightX = x + 1;
        if (leftX < 0) {
            leftX = width - 1;
        }
        if (rightX == width) {
            rightX = 0;
        }

        int leftRGB = image.getRGB(leftX, y);
        int rightRGB = image.getRGB(rightX, y);
        int r1 = (leftRGB >> 16) & 0xFF;
        int g1 = (leftRGB >>  8) & 0xFF;
        int b1 = (leftRGB >>  0) & 0xFF;
        int r2 = (rightRGB >> 16) & 0xFF;
        int g2 = (rightRGB >>  8) & 0xFF;
        int b2 = (rightRGB >>  0) & 0xFF;
        double xGradient = (r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2);
        return xGradient;
    }

    private double calYGradient(int x, int y) {
        int upperY = y + 1;
        int lowerY = y - 1;
        if (upperY == height) {
            upperY = 0;
        } else if (upperY < 0) {
            upperY = height - 1;
        }
        int upperRGB = image.getRGB(x, upperY);
        int lowerRGB = image.getRGB(x, lowerY);
        int r1 = (upperRGB >> 16) & 0xFF;
        int g1 = (upperRGB >>  8) & 0xFF;
        int b1 = (upperRGB >>  0) & 0xFF;
        int r2 = (lowerRGB >> 16) & 0xFF;
        int g2 = (lowerRGB >>  8) & 0xFF;
        int b2 = (lowerRGB >>  0) & 0xFF;
        double yGradient = (r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2);
        return yGradient;
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
        int[] verticalSeam = new int[height];
        double[][] minEnergies = new double[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                minEnergies[i][j] = calMinEnergies(i, j, minEnergies);
            }
        }


        return null;
    }

    private int[] buildSeam(double[][] minEnergies) {
        int[] seam = new int[height];
        int column = 0;
        double minEnergy = minEnergies[height - 1][0];

        for (int i = 0; i < width; i++) {
            if (minEnergies[height - 1][i] < minEnergy) {
                column = i;
                minEnergy = minEnergies[height - 1][i];
            }
        }

        seam[height - 1] = column;
        for (int row = height - 2; row >= 0; row--) {
            if (column == 1) {
                if (!(minEnergies[row][column] <= minEnergies[row][column + 1])) {
                    column = column + 1;
                }
                seam[row] = column;
            }
            if (column == width - 1) {
                if (!(minEnergies[row][column] <= minEnergies[row][column - 1])) {
                    column = column - 1;
                }
                seam[row] = column;
            }

        }
        return null;
    }

    private double calMinEnergies(int x, int y, double[][] minEnergies) {

        if (x == 0) {
            return energies[0][y];
        }
        if (y == 0) {
            return energy(x, y) + Double.min(minEnergies[x - 1][y], minEnergies[x - 1][y + 1]);
        }
        if (y == width - 1) {
            return energy(x, y) + Double.min(minEnergies[x - 1][y - 1], minEnergies[x - 1][y]);
        }
        return energy(x, y) + Double.min(minEnergies[x - 1][y - 1], Double.min(minEnergies[x - 1][y], minEnergies[x - 1][y + 1]));
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
