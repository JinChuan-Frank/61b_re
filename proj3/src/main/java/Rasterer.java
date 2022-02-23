import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private final double  initialLonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
    public Map<String, Tile>[] tiles;

    public Rasterer() {
        tiles = new Map[8];
        for (int i = 0; i <= 7; i++) {
            tiles[i] = new HashMap<>();
        }
        Tile originalTile = createOriginalTile();
        createTilesAndChildren(originalTile);
    }

    public Map<String, Object> findBounds(double queryULLat, double queryULLon,
                                           double queryLRLat, double queryLRLon, double width) {

        Map<String, Object> bounds = new HashMap<>();
        int d = calDepth(queryULLon, queryLRLon, width);
        double layerTileLength = calLayerTileLength(d);
        double layerTileHeight = calLayerTileHeight(d);

        double leftMostLon = MapServer.ROOT_ULLON;
        double upperMostLat = MapServer.ROOT_ULLAT;


        if (queryULLon < leftMostLon) {
            int upperLeftTileX = 0;
            bounds.put("upperLeftLon", leftMostLon);
        } else {
            for (int i = 0; i <= Math.pow(2, d); i++) {
                if ((upperMostLat - i * layerTileLength <= queryULLon) &&
                        leftMostLon + (i + 1) * layerTileLength > queryULLon) {
                    double leftBoundLon = leftMostLon + i * layerTileLength;
                    bounds.put("leftBoundLon", leftBoundLon);
                    int upperLeftTileX = i;
                    System.out.println("leftBound is: " + leftBoundLon);
                    System.out.println("upperLeftTileY: " + upperLeftTileX);
                }
            }
        }

        if (queryULLat > upperMostLat) {
            int upperLeftTileY = 0;
            bounds.put("upperLeftLat", upperMostLat);
        } else {
            for (int i = 0; i <= Math.pow(2, d); i++) {
                if ((upperMostLat - i * layerTileHeight >= queryULLat) &&
                        leftMostLon - (i + 1) * layerTileLength < queryULLon) {
                    double upperBondLat = upperMostLat - i * layerTileHeight;
                    bounds.put("upperBondLat", upperBondLat);
                    int upperLeftTileY = i;
                    System.out.println("upperLeftTileY is: " + upperLeftTileY);
                }
            }
        }

        return bounds;
    }

    private double calLayerTileLength(int layer) {
        Tile upperLeftMostTile = tiles[layer].get(translateIntoTileName(layer, 0, 0));
        double layerTileLength = upperLeftMostTile.getTileLRLon() - upperLeftMostTile.getTileULLon();
        return layerTileLength;
    }

    private double calLayerTileHeight(int layer) {
        Tile upperLeftMostTile = tiles[layer].get(translateIntoTileName(layer, 0, 0));
        double layerTileHeight = upperLeftMostTile.getTileULLat() - upperLeftMostTile.getTileLRLat();
        return layerTileHeight;
    }

    private String translateIntoTileName(int d, int xCoordinate, int yCoordinate) {
        String tileName = "d" + d +"_x" + xCoordinate + "_y" + yCoordinate;
        return tileName;
    }

    public class Tile {
        private String tileName;
        private int depth;
        private int x;
        private int y;
        private double tileULLat;
        private double tileULLon;
        private double tileLRLat;
        private double tileLRLon;

        public Tile(int d, int xCoordinate, int yCoordinate) {
            tileName = "d" + d +"_x" + xCoordinate + "_y" + yCoordinate;
            depth = d;
            x = xCoordinate;
            y = yCoordinate;
        }

        public void setPosition(double uLLatPos, double uLLonPos, double lRLatPos, double lRLonPos) {
            tileULLat =  uLLatPos;
            tileULLon = uLLonPos;
            tileLRLat = lRLatPos;
            tileLRLon = lRLonPos;
        }

        public int getDepth() {
            return depth;
        }

        public double getTileULLat() {
            return tileULLat;
        }

        public double getTileULLon() {
            return tileULLon;
        }

        public double getTileLRLat() {
            return tileLRLat;
        }

        public double getTileLRLon() {
            return tileLRLon;
        }

        public String getTileName() {
            return tileName;
        }
    }

    public Tile createOriginalTile() {
        Tile originalTile = new Tile(0, 0, 0);
        originalTile.setPosition(MapServer.ROOT_ULLAT, MapServer.ROOT_ULLON,
                MapServer.ROOT_LRLAT, MapServer.ROOT_LRLON);
        tiles[0].put(originalTile.getTileName(), originalTile);
        return originalTile;
    }

    public void createTilesAndChildren(Tile tile) {
        int d = tile.getDepth();
        if (d == 7) {
            return;
        }

        Map<String, Tile> children = createChildren(tile);
        Tile upperLeftChild = children.get("upperLeftChild");
        tiles[d + 1].put(upperLeftChild.getTileName(), upperLeftChild);
        Tile upperRightChild = children.get("upperRightChild");
        tiles[d + 1].put(upperRightChild.getTileName(), upperRightChild);
        Tile lowerLeftChild = children.get("lowerLeftChild");
        tiles[d + 1].put(lowerLeftChild.getTileName(), lowerLeftChild);
        Tile lowerRightChild = children.get("lowerRightChild");
        tiles[d + 1].put(lowerRightChild.getTileName(), lowerRightChild);
        for (Tile child : children.values()) {
            createTilesAndChildren(child);
        }
    }

    public Map<String, Tile> createChildren(Tile tile) {
        Map<String, Tile> children = new HashMap<>();
        int d = tile.getDepth();
        double parentULLat = tile.getTileULLat();
        double parentULLon = tile.getTileULLon();
        double parentLRLat = tile.getTileLRLat();
        double parentLRLon = tile.getTileLRLon();
        double centralLat = (parentULLat + parentLRLat) / 2;
        double centralLon = (parentULLon + parentLRLon) / 2;

        Tile upperLeftChild = new Tile(d + 1, 2 * tile.x, 2 * tile.y);
        upperLeftChild.setPosition(parentULLat, parentULLon, centralLat, centralLon);
        children.put("upperLeftChild", upperLeftChild);

        Tile upperRightChild = new Tile(d + 1, 2 * tile.x + 1, 2 * tile.y);
        upperRightChild.setPosition(parentULLat, centralLon, centralLat, parentLRLon);
        children.put("upperRightChild", upperRightChild);

        Tile lowerLeftChild = new Tile(d + 1, 2 * tile.x, 2 * tile.y + 1);
        lowerLeftChild.setPosition(centralLat, parentULLon, parentLRLat, centralLon);
        children.put("lowerLeftChild", lowerLeftChild);

        Tile lowerRightChild = new Tile(d + 1, 2 * tile.x + 1, 2 * tile.y + 1);
        lowerRightChild.setPosition(centralLat, centralLon, parentLRLat, parentLRLon);
        children.put("lowerRightChild", lowerRightChild);

        return children;
    }

    public int calDepth(double queryULLon, double queryLRLon, double width) {
        double depth;
        double queryBoxLonDPP = (queryLRLon - queryULLon) / width;
        //System.out.println("queryBoxLonDPP is: " + queryBoxLonDPP);
        double depthNeeded = Math.log(initialLonDPP /queryBoxLonDPP) / Math.log(2);
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
        Map<String, Object> results = new HashMap<>();
        double queryULLat = params.get("ullat");
        double queryULLon = params.get("ullon");
        double queryLRLat = params.get("lrlat");
        double queryLRLon = params.get("lrlon");
        double width = params.get("w");
        double height = params.get("h");

        /**String[][] render_grid = {{"d7_x84_y28.png", "d7_x85_y28.png", "d7_x86_y28.png"}, {"d7_x84_y29", "d7_x85_y29", "d7_x86_y29"}, {"d7_x84_y30", "d7_x85_y30", "d7_x86_y30"}};
        Double raster_ul_lon =  -122.24212;
        Double raster_ul_lat = 37.87702;
        Double raster_lr_lon = -122.24006;
        Double raster_lr_lat = 37.87539;
        int depth = 7;

        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", true);*/
        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser.");
        return results;
    }

}
