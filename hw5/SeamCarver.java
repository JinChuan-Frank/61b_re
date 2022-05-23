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
        energies = new double[width][height];
        calEnergies();
    }

    private void calEnergies() {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energies[i][j] = calPixelEnergy(i, j);
            }
        }
    }

    private double calPixelEnergy(int x, int y) {
        double xGradient = calXGradient(x, y);
        double yGradient = calYGradient(x, y);
        return xGradient + yGradient;
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
        int lowerY = y + 1;
        int upperY = y - 1;
        if (upperY < 0) {
            upperY = height - 1;
        }
        if (lowerY == height) {
            lowerY = 0;
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


    /**
     *
     * @return sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        int[] horizontalSeam = new int[width];
        Picture transposed = transposePicture();
        SeamCarver transposedCarver = new SeamCarver(transposed);
        int[] verticalSeam = transposedCarver.findVerticalSeam();
        int j = width - 1;
        for (int i = 0; i < width; i++) {
            horizontalSeam[i] = verticalSeam[j];
            j--;
        }
        return horizontalSeam;
    }

    private Picture transposePicture() {
        Picture transposed = new Picture(height, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                transposed.setRGB(y, width - 1 - x, rgb);
            }
        }
        return transposed;
    }



    /**
     *
     * @return sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {

        double[][] minEnergies = new double[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                minEnergies[j][i] = calMinEnergies(j, i, minEnergies);
            }
        }

        /**for (int i = 0; i < height; i++) {
            System.out.println();
            for (int j = 0; j < width; j++) {
                System.out.print(minEnergies[j][i] + " ");
            }
        } */

        int[] seam = buildVerticalSeam(minEnergies);

        return seam;
    }

    private int[] buildVerticalSeam(double[][] minEnergies) {
        int[] seam = new int[height];

        if (width == 1) {
            for (int i = 0; i < height; i++) {
                seam[i] = 0;
                return seam;
            }
        }

        int column = 0;
        double minEnergy = minEnergies[0][height - 1];

        for (int i = 0; i < width; i++) {
            if (minEnergies[i][height - 1] < minEnergy) {
                column = i;
                minEnergy = minEnergies[i][height - 1];
            }
        }

        seam[height - 1] = column;

        for (int row = height - 2; row >= 0; row--) {
            int best = column;

            if (column == 0) {
                int possibleBetter = column + 1;
                if (minEnergies[possibleBetter][row] < minEnergies[best][row]) {
                    column = column + 1;
                }
            } else if (column == width - 1) {
                int possibleBetter = column - 1;
                if (minEnergies[possibleBetter][row] < minEnergies[best][row]) {
                    column = column - 1;
                }
            } else {
                int possibleBetter = column + 1;
                if (minEnergies[column - 1][row] < minEnergies[column + 1][row]) {
                    possibleBetter = column - 1;
                }
                if (minEnergies[possibleBetter][row] < minEnergies[best][row]) {
                    column = possibleBetter;
                }
            }

            seam[row] = column;
        }
        return seam;
    }

    /**
     *
     * @param x column
     * @param y row
     * @param minEnergies min energy table so far
     * @return min energy path ending at pixel(col x, row y)
     */
    private double calMinEnergies(int x, int y, double[][] minEnergies) {

        if (y == 0) {
            return energies[x][0];
        }
        if (x == 0) {
            if (width == 1) {
                return energies[0][y];
            }
            return energy(x, y) + Double.min(minEnergies[x][y - 1], minEnergies[x + 1][y - 1]);
        }
        if (x == width - 1) {
            return energy(x, y) + Double.min(minEnergies[x - 1][y - 1], minEnergies[x][y - 1]);
        }
        return energy(x, y) + Double.min(minEnergies[x - 1][y - 1],
                Double.min(minEnergies[x][y - 1], minEnergies[x + 1][y - 1]));
    }

    /**
     * remove horizontal seam from picture
     * @param seam
     */
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam);
        image = SeamRemover.removeHorizontalSeam(image, seam);
        height = image.height();
    }

    /**
     * remove vertical seam from picture
     * @param seam
     */
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam);
        image = SeamRemover.removeVerticalSeam(image, seam);
        width = image.width();
    }

    private void validateSeam(int[] seam) {
        int length = seam.length;
        for (int i = 0; i < length - 1; i++) {
            int difference = seam[i] - seam[i + 1];
            if (difference * difference > 1) {
                throw new IllegalArgumentException();
            }
        }
    }

}
