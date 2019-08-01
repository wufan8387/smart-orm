package org.smart.orm.operations.text;


import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Token;

import java.util.List;
import java.util.stream.Collectors;

public class InsertStatement extends AbstractStatement {
    
    
    
    private RelationNode<InsertStatement> relRoot;

    public InsertStatement(String rel) {
        relRoot = new RelationNode<>(this, rel);
    }

    @Override
    public <T extends SqlNode<?>> T attach(T node) {
        switch (node.getType()) {
            case NodeType.RELATION:
                RelationNode<InsertStatement> relNode = (RelationNode<InsertStatement>) node;
                relRoot = relRoot == null ? relNode : relRoot;
                break;
        }
        nodeList.add(node);
        return node;
    }
    
    
    public ValuesNode<InsertStatement> values() {
        return new ValuesNode<>(this);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.paramList.clear();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Token.INSERT_INTO.apply(relRoot.getName()));
        
        
        List<AttributeNode<InsertStatement>> attrList = nodeList
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<InsertStatement>) t)
                .collect(Collectors.toList());
        
        int attrSize = attrList.size();
        if (attrSize > 0) {
            sb.append("(");
            for (int i = 0; i < attrSize; i++) {
                AttributeNode<InsertStatement> node = attrList.get(i);
                node.setAlias(node.getName());
                node.toString(sb);
                if (i < attrSize - 1)
                    sb.append(",");
            }
            sb.append(")");
        }
        
        sb.append(Token.VALUES);
        
        List<ValuesNode<InsertStatement>> valuesList = nodeList
                .stream().filter(t -> t.getType() == NodeType.VALUES)
                .map(t -> (ValuesNode<InsertStatement>) t)
                .collect(Collectors.toList());
        
        int valSize = valuesList.size();
        for (int i = 0; i < valSize; i++) {
            ValuesNode<InsertStatement> node = valuesList.get(i);
            node.toString(sb);
            if (i < valSize - 1)
                sb.append(",");
        }
        
        return sb.toString();
    }
    
    
}
