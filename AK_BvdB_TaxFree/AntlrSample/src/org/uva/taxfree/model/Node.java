package org.uva.taxfree.model;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Node {

    private Node mParent;
    private Set<Node> mChildren;

    public Node() {
        mChildren = new LinkedHashSet<Node>(); ///< preserves the order in which the items were inserted
    }

    public void addChild(Node child) {
        mChildren.add(child);
    }

    public abstract String getId();

    public void retrieveQuestions(Set<NamedNode> set) {
        addQuestion(set);
        for (Node child : mChildren) {
            child.addQuestion(set);
        }
    }

    protected void addQuestion(Set<NamedNode> set) {
        // Intentionally left blank
    }

    public boolean isVisible() {
        return false;
    }

    public abstract String getType();
}