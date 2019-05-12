package com.Window.Param.ElementEnter;

import java.lang.reflect.Field;

public class ElementInteger extends ElementDefault<Integer , String> {



    public ElementInteger(Object object, Field field) {
        super(object,field);
    }

    @Override
    protected boolean filter(String string) {

        return string.matches(
                "((\\+?0*"                             +
                        "(([1]\\d\\d\\d\\d\\d\\d\\d\\d\\d)|"  +
                        "([2][0]\\d\\d\\d\\d\\d\\d\\d\\d)|"   +
                        "([2][1][0-3]\\d\\d\\d\\d\\d\\d\\d)|" +
                        "([2][1][4][0-6]\\d\\d\\d\\d\\d\\d)|" +
                        "([2][1][4][7][0-3]\\d\\d\\d\\d\\d)|" +
                        "([2][1][4][7][4][0-7]\\d\\d\\d\\d)|" +
                        "([2][1][4][7][4][8][0-2]\\d\\d\\d)|" +
                        "([2][1][4][7][4][8][3][0-5]\\d\\d)|" +
                        "([2][1][4][7][4][8][3][6][0-3]\\d)|" +
                        "([2][1][4][7][4][8][3][6][4][0-7])|" +
                        "(\\d\\d?\\d?\\d?\\d?\\d?\\d?\\d?\\d?)))|("+

                        "(-)0*"                               +
                        "(([1]\\d\\d\\d\\d\\d\\d\\d\\d\\d)|"  +
                        "([2][0]\\d\\d\\d\\d\\d\\d\\d\\d)|"   +
                        "([2][1][0-3]\\d\\d\\d\\d\\d\\d\\d)|" +
                        "([2][1][4][0-6]\\d\\d\\d\\d\\d\\d)|" +
                        "([2][1][4][7][0-3]\\d\\d\\d\\d\\d)|" +
                        "([2][1][4][7][4][0-7]\\d\\d\\d\\d)|" +
                        "([2][1][4][7][4][8][0-2]\\d\\d\\d)|" +
                        "([2][1][4][7][4][8][3][0-5]\\d\\d)|" +
                        "([2][1][4][7][4][8][3][6][0-3]\\d)|" +
                        "([2][1][4][7][4][8][3][6][4][0-8])|" +
                        "(\\d\\d?\\d?\\d?\\d?\\d?\\d?\\d?\\d?))))"
        );

    }

    @Override
    protected Integer toValue(String value) {
        return Integer.valueOf(value);
    }


}
