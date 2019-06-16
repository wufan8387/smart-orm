package org.smart.orm;

import org.smart.orm.reflect.Getter;

public abstract class Model<T extends Class> {

    protected void propertyChange(String propertyName, Object value) {

    }

    protected void propertyChange(Getter<T> propertyName, Object value) {

    }

}
