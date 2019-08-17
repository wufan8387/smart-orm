package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.functions.PropertyGetter;
import org.smart.orm.operations.AbstractSqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;

import java.util.ArrayList;
import java.util.List;

public class GroupByNode<T extends Statement> extends AbstractSqlNode<T, GroupByNode<T>> {
    

    private List<GroupByInfo<T, ?>> groupByList = new ArrayList<>();
    
    
    
    public <K extends Model<K>> GroupByNode<T> add(Class<K> rel, PropertyGetter<K> attr) {
        RelationNode<T, K> relNode = statement().findFirst(NodeType.RELATION
                , t -> t.getName().equals(Model.getMetaManager().findEntityInfo(rel).getTableName()));
        GroupByInfo<T, K> orderByInfo = new GroupByInfo<>();
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
            GroupByInfo<T, ?> item = groupByList.get(i);
            String attr = Model
                    .getMetaManager()
                    .findEntityInfo(item.rel.getEntityInfo().getType())
                    .getProp(item.attr)
                    .getColumnName();
            sb.append(Token.REL_ATTR.apply(item.rel.getAlias(), attr));
            if (i < len - 1)
                sb.append(", ");
            
        }
        
    }
    
    private final static class GroupByInfo<T extends Statement, K extends Model<K>> {
        PropertyGetter<K> attr;
        RelationNode<T, K> rel;
    }
    
    
}
