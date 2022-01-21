package lab11.graphs;

import java.util.PriorityQueue;
import java.util.Queue;

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
    Queue<Integer> fringe = new PriorityQueue<>();
    private static final int INFINITY = Integer.MAX_VALUE;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);


        for (int x = 0; x < maze.V(); x++)
            distTo[x] = INFINITY;
    }



    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        edgeTo[s] = s;
        distTo[s] = 0;
        marked[s] = true;
        fringe.add(s);
        announce();

        if (s == t) {
            targetFound = true;
        }


        while (!fringe.isEmpty() && !targetFound) {
            int k = fringe.remove();

            for (int w : maze.adj(k)) {
                if (!marked[w]) {
                    edgeTo[w] = k;
                    distTo[w] = distTo[k] + 1;
                    marked[k] = true;
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

