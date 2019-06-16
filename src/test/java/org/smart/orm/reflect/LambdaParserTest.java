package org.smart.orm.reflect;

import org.junit.Test;

import static org.junit.Assert.*;

public class LambdaParserTest {

    @Test
    public void getGet() {

        LambdaParser.getGet(PropertyInfo::getClassName);

    }

    @Test
    public void getSet() {
    }
}