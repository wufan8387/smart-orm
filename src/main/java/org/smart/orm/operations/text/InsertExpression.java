//package org.smart.orm.operations.text;
//
//import org.smart.orm.OperationContext;
//import org.smart.orm.data.OperationPriority;
//import org.smart.orm.operations.AbstractExpression;
//import org.smart.orm.operations.Statement;
//import org.smart.orm.reflect.TableInfo;
//
//public class InsertExpression extends AbstractExpression {
//
//    private final static String EXPRESSION = " INSERT INTO `%s` ";
//
//    public InsertExpression(Statement statement, String table) {
//        this.statement = statement;
//        this.tableInfo =  statement.getTable(table);
//    }
//
//    public InsertExpression(Statement statement, TableInfo table) {
//        this.statement = statement;
//        this.tableInfo = statement.getTable(table);
//    }
//
//    @Override
//    public int getPriority() {
//        return OperationPriority.INSERT;
//    }
//
//    @Override
//    public String build() {
//         return String.op(EXPRESSION, tableInfo.getName());
//    }
//
//}
