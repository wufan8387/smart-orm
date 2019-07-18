package org.smart.orm.operations.text;

import org.smart.orm.operations.Formatter;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;

public class ConditionNode<T extends Statement> implements SqlNode<T> {
    
    private String boolOp;
    
    private SqlNode left;
    
    private SqlNode right;
    
    private String op;
    
    private ConditionNode<T>
    
    
    public ConditionNode(String leftRel, String leftAttr, Formatter op, String rightRel, String rightAttr) {
    
    }
    
    public ConditionNode(String rel, String attr, Formatter op, Object... value) {
    
    }
    
    public ConditionNode(ConditionNode internalNode, String leftRel, String leftAttr, Formatter op, String rightRel, String rightAttr) {
    
    }
    
    public ConditionNode(ConditionNode internalNode, String rel, String attr, Formatter op, Object... value) {
    
    }
    
    @Override
    public int getType() {
        return 0;
    }
    
    
}
