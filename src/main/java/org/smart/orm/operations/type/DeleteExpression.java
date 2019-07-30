//package org.smart.orm.operations.type;
//
//import org.smart.orm.Model;
//import org.smart.orm.OperationContext;
//import org.smart.orm.data.OperationPriority;
//import org.smart.orm.operations.AbstractExpression;
//import org.smart.orm.operations.Statement;
//import org.smart.orm.reflect.TableInfo;
//
//public class DeleteStatement<T extends Model<T>> extends AbstractExpression {
//
//    private final static String EXPRESSION = " DELETE FROM `%s` ";
//
//    private TableInfo tableInfo;
//
//    public DeleteStatement(Statement statement) {
//        this.statement = statement;
//    }
//
//    public DeleteStatement<T> where(WhereExpression<T>... operations) {
//        for (WhereExpression<T> operation : operations) {
//            operation.setStatement(statement);
//        }
//        return this;
//    }
//
//    @Override
//    public int getPriority() {
//        return OperationPriority.DELETE;
//    }
//
//    @Override
//    public String build() {
//        return String.op(EXPRESSION, tableInfo.getName());
//    }
//
//
//}
