package com.Window.Param.ElementEnter;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class ElementBoolean extends Element<Boolean , Boolean> {


    public ElementBoolean(Object object, Field field) {
        super(object, field);
    }

    @Override
    public Component component() {

        JRadioButton jRadioButton = new JRadioButton();
        try {
            getFIELD().setAccessible(true);
            jRadioButton.setSelected((Boolean) getFIELD().get(getOBJECT()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        jRadioButton.addActionListener((e)->{
            ElementBoolean.this.setValue(jRadioButton.isSelected(),getOBJECT() , getFIELD());
        });


        return createPanel(getFIELD().getName() , 1 , 1 ,jRadioButton );
    }

    @Override
    protected boolean filter(Boolean string) {
        return true;
    }

    @Override
    protected Boolean toValue(Boolean value) {
        return value;
    }
}
