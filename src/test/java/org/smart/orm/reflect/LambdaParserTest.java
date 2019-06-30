package org.smart.orm.reflect;

import org.junit.Test;

import static org.junit.Assert.*;

public class LambdaParserTest {

    @Test
    public void getGet() {

        LambdaParser.getGet(PropertyInfo::getColumn);

    }

    @Test
    public void getSet() {
    }
}