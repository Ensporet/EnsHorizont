package com.Window.Param.ElementEnter;

import java.lang.reflect.Field;

public class ElementString extends ElementDefault<String , String> {

    public ElementString(Object object, Field field) {
        super(object, field);
    }

    @Override
    protected boolean filter(String string) {
        return true;
    }

    @Override
    protected String toValue(String value) {
        return value;
    }
}
