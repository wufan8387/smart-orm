package org.smart.orm.operations.text;

import org.smart.orm.data.NodeType;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;

import java.util.ArrayList;
import java.util.List;

public class GroupByNode<T extends Statement> extends AbstractSqlNode<T, GroupByNode<T>> {
    
 
    private List<GroupByInfo<T>> groupByList = new ArrayList<>();
    
 
    public GroupByNode<T> add(String rel, String attr) {
        RelationNode<T> relNode = statement().findFirst(NodeType.RELATION, t -> t.getName().equals(rel));
        GroupByInfo<T> orderByInfo = new GroupByInfo<>();
        orderByInfo.attr = attr;
        orderByInfo.rel = relNode;
        groupByList.add(orderByInfo);
        return this;
    }
    
    @Override
    public int getType() {
        return NodeType.GROUP_BY;
    }
    

    @Override
    public void toString(StringBuilder sb) {
        
        int len = groupByList.size();
        
        if (len == 0)
            return;
        
        sb.append(Token.GROUP_BY);
        
        for (int i = 0; i < len; i++) {
            GroupByInfo<T> item = groupByList.get(i);
            
            sb.append(Token.REL_ATTR.apply(item.rel.getAlias(), item.attr));
            if (i < len - 1)
                sb.append(", ");
            
        }
        
    }
    
    private final static class GroupByInfo<T extends Statement> {
        String attr;
        RelationNode<T> rel;
    }
    
}
