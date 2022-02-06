package lab11.graphs;



import java.util.ArrayDeque;
import java.util.Deque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    Deque<Integer> fringe = new ArrayDeque<>();

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);

    }



    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {

        edgeTo[s] = s;
        distTo[s] = 0;
        fringe.add(s);

        while (!fringe.isEmpty()) {
            int k = fringe.remove();
            marked[k] = true;
            if (k == t) {
                targetFound = true;
                announce();
                break;
            }
            for (int w : maze.adj(k)) {
                if (!marked[w]) {
                    edgeTo[w] = k;
                    distTo[w] = distTo[k] + 1;

                    announce();
                    fringe.add(w);
                }
            }
        }
    }



    @Override
    public void solve() {
        bfs();
    }
}

