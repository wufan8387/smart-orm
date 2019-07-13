package org.smart.orm.reflect;

import org.junit.Test;

public class LambdaParserTest {

    @Test
    public void getGet() {

        LambdaParser.getGetter(PropertyInfo::getColumnName);

    }

    @Test
    public void getSet() {
    }
}