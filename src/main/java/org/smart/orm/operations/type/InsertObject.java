package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.operations.text.ValuesNode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InsertObject implements Statement {

    private RelationNode<InsertObject, ?> relRoot;

    private final List<SqlNode<?>> nodeList = new ArrayList<>();

    private final List paramList = new ArrayList();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends SqlNode<?>> T attach(T node) {
        switch (node.getType()) {
            case NodeType.RELATION:
                RelationNode<InsertObject, ?> relNode = (RelationNode<InsertObject, ?>) node;
                relRoot = relRoot == null ? relNode : relRoot;
                break;
        }
        nodeList.add(node);
        return node;
    }

    @Override
    public <T extends SqlNode<?>> List<T> find(int nodeType, Predicate<T> predicate) {
        return null;
    }

    @Override
    public <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate) {
        return null;
    }

    @Override
    public <T extends SqlNode<?>> T findFirst(int nodeType, Predicate<T> predicate, Supplier<T> other) {
        return null;
    }

    private int sn = 0;

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public List getParams() {
        return paramList;
    }

    @Override
    public String alias(String term) {
        sn++;
        return term + "_" + sn;
    }


    public <T extends Model<T>> InsertObject into(Class<T> cls) {
        relRoot = new RelationNode<>(this, cls);
        return this;
    }

    public ValuesNode<InsertObject> values() {
        return new ValuesNode<>(this);
    }


    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        this.paramList.clear();
        StringBuilder sb = new StringBuilder();

        sb.append(Token.INSERT_INTO.apply(relRoot.getName()));

        List<AttributeNode<InsertObject, ?>> attrList = nodeList
                .stream().filter(t -> t.getType() == NodeType.ATTRIBUTE)
                .map(t -> (AttributeNode<InsertObject, ?>) t)
                .collect(Collectors.toList());

        int attrSize = attrList.size();
        if (attrSize > 0) {
            sb.append("(");
            for (int i = 0; i < attrSize; i++) {
                AttributeNode<InsertObject, ?> node = attrList.get(i);
                node.setAlias(node.getName());
                node.toString(sb);
                if (i < attrSize - 1)
                    sb.append(",");
            }
            sb.append(")");
        }

        sb.append(Token.VALUES);

        List<ValuesNode<InsertObject>> valuesList = nodeList
                .stream().filter(t -> t.getType() == NodeType.VALUES)
                .map(t -> (ValuesNode<InsertObject>) t)
                .collect(Collectors.toList());

        int valSize = valuesList.size();
        for (int i = 0; i < valSize; i++) {
            ValuesNode<InsertObject> node = valuesList.get(i);
            node.toString(sb);
            if (i < valSize - 1)
                sb.append(",");
        }

        return sb.toString();
    }


}
