/**
 * Node.java.
 */

package ql.astnodes;

public abstract class Node {

    private final LineNumber location;

    public Node() {
        this.location = null;
    }

    public Node(LineNumber location) {
        this.location = location;
    }

    public LineNumber getLocation() {
        return location;
    }
}
