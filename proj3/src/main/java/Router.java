import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    //private static GraphDB graphDB;

    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {

        long startNodeID = g.closest(stlon, stlat);
        long destNodeID = g.closest(destlon, destlat);
        return findShortestPath(g, startNodeID, destNodeID);
    }

    private static List<Long> findShortestPath(GraphDB g, long startNodeID, long destNodeID) {
        List<Long> path = new ArrayList<>();
        PriorityQueue<Vertex> fringe = new PriorityQueue<>();
        Map<Long, Long> edgeTo = new HashMap<>();
        Map<Long, Vertex> vertices = new HashMap<>();
        FringeAndEdge fringeAndEdge = new FringeAndEdge(fringe, edgeTo);

        Vertex start = new Vertex(g, startNodeID, destNodeID);
        start.distanceFromSource = 0.0;
        fringeAndEdge.fringe.add(start);
        vertices.put(startNodeID, start);

        while (!fringeAndEdge.fringe.isEmpty()) {
            System.out.println("fringe size: " + fringeAndEdge.fringe.size());
            Vertex v = fringeAndEdge.fringe.remove();
            long vID = v.getId();
            //path.add(vID);
            if (vID == destNodeID) {
                break;
            }
            fringeAndEdge = v.relaxEdge(g, destNodeID, fringeAndEdge, vertices);
        }

        long v = destNodeID;
        edgeTo = fringeAndEdge.edgeTo;
        //System.out.println("edgeTo.size(): " + edgeTo.size());
        while (v != startNodeID) {
            path.add(0, v);
            v = edgeTo.get(v);
        }
        path.add(0, v);
        return path;
    }

    private static Map<Long, Double> initializeDistance(long startNodeID, Map<Long, Double> distance) {
        for (long l: distance.keySet()) {
            distance.put(l, Double.MAX_VALUE);
        }
        distance.put(startNodeID, 0.0);
        return distance;
    }


    static class Vertex implements Comparable<Vertex> {
        GraphDB g;
        long id;
        double distanceFromSource;
        double distanceToGoal;
        Iterable<Long> adjacentVerticesIDs;
        ArrayList<Vertex> adjacentVertices;

        Vertex(GraphDB graphDB, long idNum, long destNode) {
            g = graphDB;
            id = idNum;
            distanceFromSource = Double.MAX_VALUE;
            distanceToGoal = g.distance(idNum, destNode);
            adjacentVerticesIDs = g.adjacent(id);
            adjacentVertices = new ArrayList<>();
        }

        void reSetDistanceFromSource(double distance) {
            distanceFromSource = distance;
        }

        void addAdjacentVertex(Vertex v) {
            adjacentVertices.add(v);
        }


        long getId() {
            return id;
        }

        List<Vertex> getAdjacentVertices() {
            return adjacentVertices;
        }

        double getDistanceFromSource() {
            return distanceFromSource;
        }

        double getDistanceToGoal() {
            return distanceToGoal;
        }


        @Override
        public int compareTo(Vertex o) {
            double distanceFromSource1 = getDistanceFromSource();
            double distanceFromSource2 = o.getDistanceFromSource();
            double distanceToGoal1 = getDistanceToGoal();
            double distanceToGoal2 = o.getDistanceToGoal();
            return Double.compare(distanceFromSource1 + distanceToGoal1, distanceFromSource2 + distanceToGoal2);
        }

        private void createAdjacentVertices(long destID, Map<Long, Vertex> vertices) {

            for (long id : adjacentVerticesIDs) {
                if (!vertices.containsKey(id)) {
                    Vertex v = new Vertex(g, id, destID);
                    vertices.put(id, v);
                    addAdjacentVertex(v);
                    v.addAdjacentVertex(this);
                }
            }
        }

        FringeAndEdge relaxEdge(GraphDB g, long destID, FringeAndEdge fringeAndEdge, Map<Long, Vertex> vertices) {
            long myID = getId();
            createAdjacentVertices(destID, vertices);
            List<Vertex> adjacent = getAdjacentVertices();
            double myDistanceFromStart = getDistanceFromSource();

            for (Vertex adjacentVertex : adjacent) {
                long adjacentID = adjacentVertex.getId();
                double neighborDistanceFromStart = adjacentVertex.getDistanceFromSource();
                double distanceBetweenTwoVertices = g.distance(myID, adjacentID);

                if (myDistanceFromStart + distanceBetweenTwoVertices < neighborDistanceFromStart) {
                    adjacentVertex.reSetDistanceFromSource(myDistanceFromStart + distanceBetweenTwoVertices);
                    fringeAndEdge.edgeTo.put(adjacentID, myID);

                    if (fringeAndEdge.fringe.contains(adjacentVertex)) {
                        fringeAndEdge.fringe.remove(adjacentVertex);
                    }

                }
                fringeAndEdge.fringe.add(adjacentVertex);
            }

            return fringeAndEdge;
        }
    }

    static class FringeAndEdge {
        PriorityQueue<Vertex> fringe;
        Map<Long, Long> edgeTo;
        FringeAndEdge(PriorityQueue<Vertex> p, Map<Long, Long> e) {
            fringe = p;
            edgeTo = e;
        }
    }


    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null; // FIXME
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
