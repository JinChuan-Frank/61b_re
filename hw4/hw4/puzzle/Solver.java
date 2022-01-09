package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    MinPQ<SearchNode> moveSequences;
    Stack<WorldState> path;

    public class SearchNode implements Comparable<SearchNode> {
        private WorldState worldState;
        int movesSoFar;
        SearchNode prevNode;

        public SearchNode(WorldState state, int moves, SearchNode prev) {
            worldState = state;
            movesSoFar = moves;
            prevNode = prev;
        }

        @Override
        public int compareTo(SearchNode searchNode) {
            return (this.movesSoFar + this.worldState.estimatedDistanceToGoal())
                    - (searchNode.movesSoFar + searchNode.worldState.estimatedDistanceToGoal());
        }

    }

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     * @param initial
     */
    public Solver(WorldState initial) {
        moveSequences = new MinPQ<>();
        path = new Stack<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        moveSequences.insert(initialNode);
        bestFirstSearch(moveSequences, initial);
    }

    public void bestFirstSearch(MinPQ<SearchNode> moves, WorldState initial) {
        SearchNode X = moves.delMin();
        if (X.worldState.isGoal()) {
            path.push(X.worldState);
            while (!X.worldState.equals(initial)) {
                X = X.prevNode;
                path.push(X.worldState);
            }
            return;
        }

        for (WorldState neighbor : X.worldState.neighbors()) {
            if (X.prevNode != null && neighbor.equals(X.prevNode.worldState)) {
                continue;
            }
            SearchNode neighborNode = new SearchNode(neighbor,
                    X.movesSoFar + 1, X);
            moves.insert(neighborNode);

        }
        bestFirstSearch(moves, initial);
    }


    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     * @return
     */
    public int moves() {
        return path.size() - 1;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     * @return
     */
    public Iterable<WorldState> solution() {
        return path;
    }
}
