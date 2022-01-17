package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;



public class Solver {

    private MinPQ<SearchNode> moveSequences = new MinPQ<>();;
    private Stack<WorldState> path = new Stack<>();
    private SearchNode finish;

    private class SearchNode implements Comparable<SearchNode> {
        private WorldState worldState;
        int movesSoFar;
        SearchNode prevNode;

        public SearchNode(WorldState state, int moves, SearchNode prev) {
            worldState = state;
            movesSoFar = moves;
            prevNode = prev;
        }

        @Override
        public int compareTo(SearchNode o) {
            return (this.movesSoFar + this.worldState.estimatedDistanceToGoal())
                    - (o.movesSoFar + o.worldState.estimatedDistanceToGoal());
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
        SearchNode initialNode = new SearchNode(initial, 0, null);
        moveSequences.insert(initialNode);
        while (!moveSequences.isEmpty()) {
            SearchNode current = moveSequences.delMin();
            if (current.worldState.estimatedDistanceToGoal() == 0) {
                finish = current;
                return;
            }
            for (WorldState neighborState : current.worldState.neighbors()) {
                if (current.prevNode == null
                        || !neighborState.equals(current.prevNode.worldState)) {
                    moveSequences.insert(new SearchNode(neighborState,
                            current.movesSoFar + 1, current));
                }
            }
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     * @return
     */
    public int moves() {
        return finish.movesSoFar;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     * @return
     */
    public Iterable<WorldState> solution() {
        SearchNode temp = finish;
        while (temp != null) {
            path.push(temp.worldState);
            temp = temp.prevNode;
        }
        return path;
    }
}
