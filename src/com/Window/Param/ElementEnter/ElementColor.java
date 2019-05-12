package com.Window.Param.ElementEnter;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class ElementColor extends Element<Color, Color> {
    public ElementColor(Object object, Field field) {
        super(object, field);
        field.setAccessible(true);
    }

    @Override
    public Component component() {

        JPanel jPanel = createPanel(getFIELD().getName());

        JButton jButton = new JButton();
        jPanel.add(jButton);


        try {

            jButton.setBackground((Color) getFIELD().get(getOBJECT()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        jButton.addActionListener(e->{
            try {
                Color color = (Color) getFIELD().get(getOBJECT());
                color = JColorChooser.showDialog(jPanel,getFIELD().getName(),color);

                if (color != null) {
                jButton.setBackground(color);
                ElementColor.this.setValue(color,getOBJECT(),getFIELD());
                };
            } catch (IllegalAccessException en) {
                en.printStackTrace();
            }

        });




        return jPanel;
    }

    @Override
    protected boolean filter(Color string) {
        return true;
    }

    @Override
    protected Color toValue(Color value) {
        return value;
    }
}
