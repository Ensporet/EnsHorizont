package com.Window.Param.ElementEnter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.Field;

public abstract class ElementDefault<T , V> extends Element<T,V>{


    private V oldValue;


    public ElementDefault(Object object, Field field) {
        super(object,field);
    }


    protected V getOldValue() {return oldValue;}

    @Override
    public Component component () {

        TextField textField = new TextField();

        try {
            getFIELD().setAccessible(true);
            Object value = getFIELD().get(getOBJECT());
            textField.setText((value == null) ? "" : value.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        oldValue = (V) textField.getText();
        getFIELD().setAccessible(true);
        textField.addTextListener(e -> {
            if (filter((V) textField.getText())) {
                setValue((V) textField.getText(), getOBJECT(), getFIELD());
                oldValue = (V) textField.getText();
            } else {
                textField.setText((String) oldValue);
            }
        });

        return createPanel(getFIELD().getName(),textField);
    }




    protected abstract boolean filter(V string);

    protected abstract T toValue(V value);

    protected void setValue(V value, Object object , Field field) {
        try {
            field.set(object , toValue(value));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    //------------------------------------------------------------------------------------------------------------------

    protected JPanel createPanel(String name , Component... component) {
        return createPanel(name,-1,-1,component);
    }
    protected JPanel createPanel(String name , int row , int colum, Component ... component ) {
        //https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
        JPanel jPanel= new JPanel();
        for (Component c : component) {
            jPanel.add(c);
        }
        jPanel.setBorder(

                BorderFactory.createTitledBorder
                        (
                                BorderFactory.createLineBorder(Color.black,1,true),
                                name,
                                TitledBorder.LEFT,
                                TitledBorder.ABOVE_TOP
                        )


        );
        jPanel.setLayout(new GridLayout((row < 1) ? 1 : row, colum < 1 ? 1 : colum));



        return jPanel;
    }

}
