package org.smart.orm.operations.type;

import org.smart.orm.Model;
import org.smart.orm.data.NodeType;
import org.smart.orm.operations.SqlNode;
import org.smart.orm.operations.Statement;
import org.smart.orm.operations.Token;
import org.smart.orm.functions.PropertyGetter;

import java.util.ArrayList;
import java.util.List;

public class SetNode<T extends Statement> implements SqlNode<T> {

    private T statement;

    private final List<PropertyGetter<?>> attrList = new ArrayList<>();

    private final List<Object> valueList = new ArrayList<>();


    public SetNode(T statement) {
        this.statement = statement;
    }

    @Override
    public int getType() {
        return NodeType.SET;
    }

    @Override
    public T statement() {
        return statement;
    }

    public <K extends Model<K>> SetNode<T> assign(PropertyGetter<K> attr, Object value) {
        int index = attrList.indexOf(attr);
        if (index < 0) {
            attrList.add(attr);
            valueList.add(value);
        } else {
            valueList.set(index, value);
        }
        return this;
    }

    @Override
    public void toString(StringBuilder sb) {

        int len = attrList.size();

        if (len == 0)
            return;

        for (int i = 0; i < len; i++) {
            sb.append(Token.ASSIGN.apply(attrList.get(i), "?"));
            statement.getParams().add(valueList.get(i));
            if (i < len - 1)
                sb.append(",");
        }

    }


}
