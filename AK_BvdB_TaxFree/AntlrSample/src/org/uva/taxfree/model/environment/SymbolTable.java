package org.uva.taxfree.model.environment;

import org.uva.taxfree.model.node.declarations.NamedNode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SymbolTable {
    private final Set<NamedNode> mDeclarations; // All declarations
    private final Set<String> mUsedVariables;

    public SymbolTable() {
        mDeclarations = new LinkedHashSet<>();
        mUsedVariables = new LinkedHashSet<>();
    }

    public void addDeclarations(Set<NamedNode> nodes) {
        for (NamedNode n : nodes) {
            addDeclaration(n);
        }
    }

    public void addDeclaration(NamedNode Node) {
        mDeclarations.add(Node);
    }

    public void addVariable(String variableName) {
        mUsedVariables.add(variableName);
    }

    public String resolve(String variableId) {
        for (NamedNode n : mDeclarations) {
            if (variableId.equals(n.getId())) {
                return (n.resolveValue());
            }
        }
        throw new RuntimeException("Unable to resolveValue id: " + variableId);
    }

    public List<String> getDuplicateLabelErrors() {
        List<String> errors = new ArrayList<>();
        Set<String> processedLabels = new LinkedHashSet<>();
        for (NamedNode node : mDeclarations) {
            String questionLabel = node.getLabel();
            if (!processedLabels.add(questionLabel)) {
                errors.add("Duplicate question label found: " + questionLabel);
            }
        }
        return errors;
    }

    public List<String> getDuplicateDeclarationErrors() {
        List<String> errors = new ArrayList<>();
        Set<String> processedDeclarations = new LinkedHashSet<>();
        for (NamedNode node : mDeclarations) {
            String declaration = node.getId();
            if (!processedDeclarations.add(declaration)) {
                errors.add("Duplicate declaration found: " + declaration);
            }
        }
        return errors;
    }


    public List<String> getUndefinedDeclarationErrros() {
        List<String> errors = new ArrayList<>();
        for (String identifier : mUsedVariables) {
            if (!validDeclaration(identifier)) {
                errors.add("No declaration found: " + identifier);
            }
        }
        return errors;
    }

    private boolean validDeclaration(String identifier) {
        for (NamedNode node : mDeclarations) {
            if (identifier.equals(node.getId())) {
                return true;
            }
        }
        return false;
    }
}