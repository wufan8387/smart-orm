package org.smart.orm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OperationContext {

    private List<Operation> operationList = new ArrayList<>();

    public List<Operation> getOperationList() {
        return operationList;
    }

    public void add(Operation operation) {
        this.operationList.add(operation);
    }

    public void execute(Operation operation) {
        List<Operation> batchList = operationList
                .stream()
                .filter(t -> t.getBatch() == operation.getBatch())
                .sorted((o1, o2) -> {
                    if (o1.getPriority() < o2.getPriority())
                        return 1
                    return 0;
                })
                .collect(Collectors.toList());
        operationList.removeAll(batchList);

        StringBuilder sb = new StringBuilder();
        List<Object> paramList = new ArrayList<>()
        for (Operation op : batchList) {
            op.build();
            sb.append(op.getExpression());
            paramList.addAll(op.getParams());
        }

        return null;
    }


}
