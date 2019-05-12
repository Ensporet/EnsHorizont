package com.Window.Param;

import com.Window.Param.ElementEnter.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

public class JParam extends ParamSearch{



    @Override
    protected Component buldComponetPacket(List<Component> list) {

       JPanel jPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(list.size() , 1);
        jPanel.setLayout(gridLayout);

       for (Component component : list) {
            jPanel.add(component);
       }

       JScrollPane jScrollPane = new JScrollPane(jPanel);


       return jScrollPane;
    }

    //------------------------------------------------------------------------------------------------------------------


    @TypeOfValue(type =  Integer.class)
    protected Component componentInteger (Object object , Field field) {
        return new ElementInteger(object,field).component();
    }

    @TypeOfValue(type = Boolean.class)
    protected Component componentBoolean (Object object , Field field) {
        return new ElementBoolean(object,field).component();
    }

    @TypeOfValue(type = String.class)
    protected Component componentString (Object object , Field field) {
        return new ElementString(object,field).component();
    }

    @TypeOfValue(type = File.class)
    protected Component componentFile (Object object , Field field) {
        return new ElementFile(object,field).component();
    }

    @TypeOfValue(type = Color.class)
    protected Component componentColor (Object object , Field field) {
        return new ElementColor(object,field).component();
    }

}
