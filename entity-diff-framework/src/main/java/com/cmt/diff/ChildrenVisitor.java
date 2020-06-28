package com.cmt.diff;

import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;

/**
 * 子节点遍历器
 *
 * @author yonghuang
 */
public abstract class ChildrenVisitor implements DiffNode.Visitor {
    public void node(DiffNode node, Visit visit) {
        visit.dontGoDeeper();
        child(node);
    }

    abstract protected void child(DiffNode node);
}
