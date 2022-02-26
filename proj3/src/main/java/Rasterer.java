
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private final double initialLonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON)
            / MapServer.TILE_SIZE;
    private final double leftMostLon = MapServer.ROOT_ULLON;
    private final double upperMostLat = MapServer.ROOT_ULLAT;
    private final double rightMostLon = MapServer.ROOT_LRLON;
    private final double lowerMostLat = MapServer.ROOT_LRLAT;


    public Rasterer() {

    }

    /**
     *
     * @param queryULLat
     * @param queryULLon
     * @param queryLRLat
     * @param queryLRLon
     * @param width
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */

    public Map<String, Object> getRasterParams(double queryULLat, double queryULLon,
                                           double queryLRLat, double queryLRLon, double width) {

        Map<String, Object> rasterParams = new HashMap<>();
        boolean isValidQuery = checkValidQuery(queryULLat, queryULLon, queryLRLat, queryLRLon);
        if (!isValidQuery) {
            rasterParams.put("query_success", false);
            return rasterParams;
        }

        int d = calDepth(queryULLon, queryLRLon, width);
        double layerTileLength = calLayerTileLength(d);
        double layerTileHeight = calLayerTileHeight(d);

        rasterParams.put("query_success", true);
        rasterParams.put("depth", d);


        Map<String, Integer> upperLeftTileXAndY = calUpperLeftTileXAndY
                (layerTileLength, layerTileHeight, queryULLat, queryULLon, d);
        Map<String, Integer> lowerRightTileXAndY = calLowerRightTileXAndY
                (layerTileLength, layerTileHeight, queryLRLat, queryLRLon, d);

        int upperLeftTileX = upperLeftTileXAndY.get("upperLeftTileX");
        int upperLeftTileY = upperLeftTileXAndY.get("upperLeftTileY");
        int lowerRightTileX = lowerRightTileXAndY.get("lowerRightTileX");
        int lowerRightTileY = lowerRightTileXAndY.get("lowerRightTileY");

        Map<String, Double> bounds = calBounds(upperLeftTileX, upperLeftTileY, lowerRightTileX, lowerRightTileY, layerTileLength, layerTileHeight);
        double rasterUlLon = bounds.get("leftBound");
        double rasterUlLat = bounds.get("upperBound");
        double rasterLrLon = bounds.get("rightBound");
        double rasterLrLat = bounds.get("lowerBound");

        rasterParams.put("raster_ul_lon", rasterUlLon);
        rasterParams.put("raster_ul_lat", rasterUlLat);
        rasterParams.put("raster_lr_lon", rasterLrLon);
        rasterParams.put("raster_lr_lat", rasterLrLat);



        String[][] renderGrid = renderGrid
                (upperLeftTileX, upperLeftTileY, lowerRightTileX, lowerRightTileY, d);
        rasterParams.put("render_grid", renderGrid);

        System.out.println(rasterParams);
        return rasterParams;
    }

    private String createFileName(String tileName) {
        String fileName = tileName + ".png";
        return fileName;
    }

    private boolean checkValidQuery(double queryULLat, double queryULLon,
                                    double queryLRLat, double queryLRLon) {
        boolean isValidQuery = true;
        if (queryULLat <= lowerMostLat || queryLRLat >= upperMostLat
                || queryULLon >= rightMostLon || queryLRLon <= leftMostLon) {
            isValidQuery = false;
        }
        return isValidQuery;
    }

    private String[][] renderGrid(int upperLeftTileX, int upperLeftTileY,
                                  int lowerRightTileX, int lowerRightTileY, int d) {

        int numOfColumns = lowerRightTileX - upperLeftTileX + 1;
        int numOfRows = lowerRightTileY - upperLeftTileY + 1;
        int x = upperLeftTileX;
        int y = upperLeftTileY;
        String[][] renderGrid = new String[numOfRows][numOfColumns];
        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfColumns; col++) {
                String tileName = translateIntoTileName(d, x, y);
                renderGrid[row][col] = createFileName(tileName);
                x++;

            }
            x = upperLeftTileX;
            y++;
        }
        return renderGrid;
    }

    private Map<String, Integer> calUpperLeftTileXAndY(double layerTileLength,
                                                       double layerTileHeight, double queryULLat,
                                                       double queryULLon, int d) {

        Map<String, Integer> upperLeftTileXAndY = new HashMap<>();


        if (queryULLon < leftMostLon) {
            int upperLeftTileX = 0;
            upperLeftTileXAndY.put("upperLeftTileX", upperLeftTileX);
        } else {
            for (int i = 0; i < Math.pow(2, d); i++) {
                if ((leftMostLon + i * layerTileLength <= queryULLon)
                        && leftMostLon + (i + 1) * layerTileLength > queryULLon) {
                    int upperLeftTileX = i;
                    //System.out.println("upperLeftTileX is: " + upperLeftTileX);
                    upperLeftTileXAndY.put("upperLeftTileX", upperLeftTileX);
                    break;
                }
            }
        }

        if (queryULLat > upperMostLat) {
            int upperLeftTileY = 0;
            upperLeftTileXAndY.put("upperLeftTileY", upperLeftTileY);
        } else {
            for (int i = 0; i < Math.pow(2, d); i++) {
                if ((upperMostLat - i * layerTileHeight >= queryULLat)
                        && (upperMostLat - (i + 1) * layerTileHeight < queryULLat)) {
                    int upperLeftTileY = i;
                    //System.out.println("upperLeftTileY is: " + upperLeftTileY);
                    upperLeftTileXAndY.put("upperLeftTileY", upperLeftTileY);
                    break;
                }
            }
        }
        return upperLeftTileXAndY;
    }

    private Map<String, Double> calBounds(int upperLeftTileX, int upperLeftTileY,
                                          int lowerRightTileX, int lowerRightTileY,
                                          double layerLength, double layerHeight) {
        Map<String, Double> bounds = new HashMap<>();
        double leftBound = leftMostLon + layerLength * upperLeftTileX;
        double upperBound = upperMostLat - layerHeight * upperLeftTileY;
        double rightBound = leftMostLon + layerLength * (lowerRightTileX + 1);
        double lowerBound = upperMostLat - layerHeight * (lowerRightTileY + 1);
        bounds.put("leftBound", leftBound);
        bounds.put("upperBound", upperBound);
        bounds.put("rightBound", rightBound);
        bounds.put("lowerBound", lowerBound);
        return bounds;
    }

    private Map<String, Integer> calLowerRightTileXAndY(double layerTileLength,
                                                        double layerTileHeight, double queryLRLat,
                                                        double queryLRLon, int d) {

        Map<String, Integer> lowerRightTileXAndY = new HashMap<>();

        if (queryLRLon > rightMostLon) {
            int lowerRightTileX = (int) (Math.pow(2, d) - 1);
            lowerRightTileXAndY.put("lowerRightTileX", lowerRightTileX);
        } else {
            for (int i = 0; i < Math.pow(2, d); i++) {
                if ((leftMostLon + i * layerTileLength < queryLRLon)
                        && leftMostLon + (i + 1) * layerTileLength >= queryLRLon) {
                    int lowerRightTileX = i;
                    //System.out.println("lowerRightTileX is: " + lowerRightTileX);
                    lowerRightTileXAndY.put("lowerRightTileX", lowerRightTileX);
                    break;
                }
            }
        }

        if (queryLRLat < lowerMostLat) {
            int lowerRightTileY = (int) (Math.pow(2, d) - 1);
            lowerRightTileXAndY.put("lowerRightTileY", lowerRightTileY);
        } else {
            for (int i = 0; i < Math.pow(2, d); i++) {
                if ((upperMostLat - i * layerTileHeight > queryLRLat)
                        && (upperMostLat - (i + 1) * layerTileHeight <= queryLRLat)) {
                    int lowerRightTileY = i;
                    lowerRightTileXAndY.put("lowerRightTileY", lowerRightTileY);
                    //System.out.println("lowerRightTileY is: " + lowerRightTileY);
                    break;
                }
            }
        }

        return lowerRightTileXAndY;
    }



    private double calLayerTileLength(int layer) {
        double totalLength = rightMostLon - leftMostLon;
        double numberOfTileLength = Math.pow(2, layer);
        double layerTileLength = totalLength / numberOfTileLength;
        return layerTileLength;
    }

    private double calLayerTileHeight(int layer) {
        double totalHeight = upperMostLat - lowerMostLat;
        double numberOfTileHeight = Math.pow(2, layer);
        double layerTileHeight = totalHeight / numberOfTileHeight;
        return layerTileHeight;
    }

    private String translateIntoTileName(int d, int xCoordinate, int yCoordinate) {
        String tileName = "d" + d + "_x" + xCoordinate + "_y" + yCoordinate;
        return tileName;
    }

    private int calDepth(double queryULLon, double queryLRLon, double width) {
        double depth;
        double queryBoxLonDPP = (queryLRLon - queryULLon) / width;
        //System.out.println("queryBoxLonDPP is: " + queryBoxLonDPP);
        double depthNeeded = Math.log(initialLonDPP / queryBoxLonDPP) / Math.log(2);
        depth = Math.ceil(depthNeeded);
        //System.out.println("desired depth is: " + depth);
        if (depth >= 7) {
            depth = 7;
        }
        if (depth <= 0) {
            depth = 0;
        }
        return (int) depth;
    }



    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);

        double queryULLat = params.get("ullat");
        double queryULLon = params.get("ullon");
        double queryLRLat = params.get("lrlat");
        double queryLRLon = params.get("lrlon");
        double width = params.get("w");

        Map<String, Object> results = getRasterParams(queryULLat, queryULLon,
                queryLRLat, queryLRLon, width);
        /**System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser."); */
        return results;
    }

}
