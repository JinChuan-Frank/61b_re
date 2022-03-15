import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    Map<Long, Node> graph = new HashMap<>();
    Map<Long, Way> ways = new HashMap<>();
    long startNodeID;
    long destNodeID;

    public Map<Long, Node> getGraph() {
        return graph;
    }

    public void setStartNodeID(long id) {
        startNodeID = id;
    }

    public void setDestNodeID(long id) {
        destNodeID = id;
    }


    public class Node implements Comparable<Node> {
        long id;
        double lat;
        double lon;
        double distanceFromStart = Double.MAX_VALUE;
        double distanceToEnd;
        ArrayList<Node> adjacentVertices;
        String locationName;

        Node(long id, double lat, double lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            adjacentVertices = new ArrayList<>();
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public void setDistanceToEnd() {
            this.distanceToEnd = distance(id, destNodeID);
        }

        public ArrayList<Node> getAdjacentVertices() {
            return adjacentVertices;
        }

        public void setDistanceFromStart(double distanceFromStart) {
            this.distanceFromStart = distanceFromStart;
        }

        public long getId() {
            return id;
        }

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }

        public double getDistanceFromStart() {
            return distanceFromStart;
        }

        public void relaxEdge(PriorityQueue<Node> pq, Map<Long, Long> edgeTo) {
            List<Node> nodes = this.getAdjacentVertices();
            for (Node node : nodes) {
                double distanceFromStarToThis = getDistanceFromStart();
                double distanceBetweenTwoNodes = distance(this.getId(), node.getId());
                double distanceFromStartToAdjacent = node.getDistanceFromStart();
                double distanceToAdjNodeViaThis = distanceFromStarToThis + distanceBetweenTwoNodes;

                if (distanceToAdjNodeViaThis < distanceFromStartToAdjacent) {
                    node.setDistanceFromStart(distanceToAdjNodeViaThis);
                    edgeTo.put(node.getId(), this.getId());
                    if (pq.contains(node)) {
                        pq.remove(node);
                    }
                    pq.add(node);
                }

            }
        }

        public void addAdjacentVertex(Node node) {
            adjacentVertices.add(node);
        }

        @Override
        public int compareTo(Node o) {
            double distanceToEnd1 = distance(this.getId(), destNodeID);
            double distanceToEnd2 = distance(o.getId(), destNodeID);
            double distanceFromStart1 = this.getDistanceFromStart();
            double distanceFromStart2 = o.getDistanceFromStart();
            return Double.compare(distanceFromStart1 + distanceToEnd1,
                    distanceFromStart2 + distanceToEnd2);
        }
    }

    public class Way {
        long ID;
        boolean isValidWay;
        Set<Long> nodesIDs;
        ArrayList<Node> nodes;
        int maxSpeed;
        String wayName;

        public Way() {
            isValidWay = false;
            nodes = new ArrayList<>();
            nodesIDs = new HashSet<>();
        }

        public void setID(long id) {
            this.ID = id;
        }

        public void setValidWay(boolean validWay) {
            this.isValidWay = validWay;
        }

        public void setWayName(String wayName) {
            this.wayName = wayName;
        }

        public long getID() {
            return ID;
        }

        void addNode(Node node) {
            long id = node.getId();
            if (!nodesIDs.contains(ID)) {
                nodes.add(node);
                nodesIDs.add(ID);
            }
        }

        void setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public ArrayList<Node> getNodes() {
            return nodes;
        }

        public boolean isValidWay() {
            return isValidWay;
        }

        public void connectNodesOnTheWay() {
            int numOfEdges = nodes.size() - 1;
            for (int i = 0; i < numOfEdges; i++) {
                addEdge(nodes.get(i), nodes.get(i + 1));
            }
        }

    }

    public void addEdge(Node A, Node B) {
        A.addAdjacentVertex(B);
        B.addAdjacentVertex(A);
    }

    public void addNode(Node node) {
        long id = node.getId();
        graph.put(id, node);
    }

    public void addNewWay(Way way) {
        long id = way.getID();
        ways.put(id, way);
    }


    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Set<Long> ids = graph.keySet();
        Set<Long> nodesToBeRemoved = new HashSet<>();
        for (long i : ids) {
            Node node = graph.get(i);
            if (node.getAdjacentVertices().size() == 0) {
                nodesToBeRemoved.add(i);
            }
        }

        for (long i : nodesToBeRemoved) {
            graph.remove(i);
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return graph.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        List<Long> adjacentVerticesIDs = new ArrayList<>();
        Node node = graph.get(v);
        List<Node> adjacentVertices = node.getAdjacentVertices();
        for (Node node1 : adjacentVertices) {
            adjacentVerticesIDs.add(node1.getId());
        }
        return adjacentVerticesIDs;

    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double lonV = lon;
        double latV = lat;
        double minDistance = Double.MAX_VALUE;
        long minDisID = 0;
        for (Node W : graph.values()) {
            long id = W.getId();
            double lonW = W.getLon();
            double latW = W.getLat();
            double distance = distance(lonV, latV, lonW, latW);

            if (distance < minDistance) {
                minDistance = distance;
                minDisID = id;
            }
        }
        return minDisID;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return graph.get(v).getLon();
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return graph.get(v).getLat();
    }
}
