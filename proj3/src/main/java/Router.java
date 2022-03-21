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
        Map<Long, Double> bestDistanceFromSource = new HashMap<>();
        Map<Long, Double> distanceToGoal = new HashMap<>();

        bestDistanceFromSource = initializeDistance(startNodeID, bestDistanceFromSource);

        DistAndEdgeTo d = new DistAndEdgeTo(bestDistanceFromSource, distanceToGoal, edgeTo);


        Vertex start = new Vertex(startNodeID);
        fringe.add(start);

        while (!fringe.isEmpty()) {
            Vertex v = fringe.remove();
            long vID = v.getId();
            path.add(vID);
            if (vID == destNodeID) {
                break;
            }
            v.relaxEdge(g, d);
        }
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
        long id;
        double distanceFromSource;
        double distanceToGoal;
        List<Vertex> adjacentVertices;

        Vertex(long id) {
            this.id = id;
        }

        void setDistanceFromSource(double distance) {
            distanceFromSource = distance;
        }

        void setDistanceToGoal(double distance) {
            distanceToGoal = distance;
        }

        long getId() {
            return id;
        }

        double getDistanceFromSource() {
            return distanceFromSource;
        }

        double getDistanceToGoal() {
            return distanceToGoal;
        }

        List<Vertex> getAdjacentVertices() {
            return adjacentVertices;
        }

        @Override
        public int compareTo(Vertex o) {
            double distanceFromSource1 = getDistanceFromSource();
            double distanceFromSource2 = o.getDistanceFromSource();
            double distanceToGoal1 = getDistanceToGoal();
            double distanceToGoal2 = o.getDistanceToGoal();
            return Double.compare(distanceFromSource1 + distanceToGoal1, distanceFromSource2 + distanceToGoal2);
        }

        private List<Vertex> createAdjacentVertices(GraphDB g, long destID) {
            List<Vertex> vertices = new ArrayList<>();
            Iterable<Long> adjacent = g.adjacent(getId());
            for (long id : adjacent) {
                Vertex v = new Vertex(id);
                v.setDistanceToGoal(g.distance(id, destID));
                adjacentVertices.add(v);
            }
            return vertices;
        }

        DistAndEdgeTo relaxEdge(GraphDB g, DistAndEdgeTo d) {
            List<Vertex> adjacent = getAdjacentVertices();
            double myDistanceFromStart = d.distanceFromStart.get(getId());

            for (Vertex vertex : adjacent) {
                long adjacentID = vertex.getId();
                double neighborDistanceFromStart = d.distanceFromStart.get(adjacentID);
                double distanceBetweenTwoVertices = g.distance(getId(), adjacentID);
                if (myDistanceFromStart + distanceBetweenTwoVertices < neighborDistanceFromStart) {
                    d.distanceFromStart.put(adjacentID, myDistanceFromStart + distanceBetweenTwoVertices);
                    d.edgeTo.put(adjacentID, getId());
                }
            }
            return d;
        }
    }

    static class DistAndEdgeTo {
        Map<Long, Double> distanceFromStart;
        Map<Long, Double> distanceToGoal;
        Map<Long, Long> edgeTo;
        DistAndEdgeTo(Map<Long, Double> d1, Map<Long, Double> d2,  Map<Long, Long> e) {
            distanceFromStart = d1;
            distanceToGoal = d2;
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
