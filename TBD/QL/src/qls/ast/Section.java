package qls.ast;

import qls.ast.literals.QLSString;

/**
 * Created by rico on 7-3-17.
 */
public class Section extends PageStatement {
    private final String name;
    private final SectionStatements statements;

    public Section(QLSString name, SectionStatements statements) {
        super(name.getRowNumber());
        this.name = name.getValue();
        this.statements = statements;
    }

    public String getName() {
        return name;
    }

    public SectionStatements getSectionStatements() {
        return statements;
    }
}