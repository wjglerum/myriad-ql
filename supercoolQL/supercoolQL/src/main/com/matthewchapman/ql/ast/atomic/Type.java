package com.matthewchapman.ql.ast.atomic;

import com.matthewchapman.ql.ast.TreeNode;
import com.matthewchapman.ql.visitors.TypeVisitor;

/**
 * Created by matt on 21/02/2017.
 * Abstract Type, provides base class for all Type objects to derive from
 */
public abstract class Type extends TreeNode {

    public abstract boolean isCompatible(Type type);

    @Override
    public abstract String toString();

    public abstract <T, C> T accept(TypeVisitor<T, C> visitor, C context);

}
