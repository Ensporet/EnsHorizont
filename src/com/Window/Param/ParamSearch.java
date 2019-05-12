package com.Window.Param;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class ParamSearch {

    public static void main(String[] args) {
        ParamSearch paramSearch = new JParam();

        Component component =
                paramSearch.buldComponetPacket(
                        paramSearch.buldValues(paramSearch)
                );


        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setBounds(500, 200, 500, 400);
        jFrame.add(component);
        jFrame.setVisible(true);


    }


    public  Component createParam(Object object) {



        return
                buldComponetPacket(
                        buldValues(object)
                );
    }

    protected abstract Component buldComponetPacket(List<Component> list);



    //------------------------------------------------------------------------------------------------------------------



    //------------------------------------------------------------------------------------------------------------------


    protected List<Component> buldValues(Object object) {

        final List <Field> FIELDS = searchFields(object,null,null);
        final List<Method> METHODS = searchMethodType();
        final List<Component> LIST = new ArrayList<>();

        for (Field field : FIELDS) {
            for (Method method : METHODS) {
                if (isInstantsOf(object , field , method)) { // type of method. For Integer or String or another
                    Component component = methodAction(object , field , method);
                    if (component != null) {
                        LIST.add(component) ;
                    }
                }
            }
        }

        return LIST;
    }

    private Component methodAction(Object object , Field field , Method method) {
        try {
            field.setAccessible(true);
            method.setAccessible(true);

            Object com = method.invoke(this,object,field);
            if (com instanceof Component) {
                return (Component) com;

            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();

        }
        return null;
    }

    private boolean isInstantsOf(Object object , Field field , Method method) {


        try {

            field.setAccessible(true);
           // System.out.println("" +field.get(object));
            return method.getAnnotation(TypeOfValue.class).type().isInstance(field.get(object));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<Method> searchMethodType() {

        final List <Method> LIST = new ArrayList<>();

        Class cl = this.getClass();
        do{
        for (Method method : cl.getDeclaredMethods()) {

            if (method.getAnnotation(TypeOfValue.class) != null) {
                LIST.add(method);
            }
        }} while ((cl = cl.getSuperclass()) != null);
        return LIST ;
    }

    //------------------------------------------------------------------------------------------------------------------

    protected List<Field> searchFields(Object object, Class cl, List<Field> list) {
        if (object == null) {
            return new ArrayList<>();
        }

        if (cl == null) {
            cl = object.getClass();
        }
        if (list == null) {
            list = new ArrayList<>();
        }

        for (Field field : cl.getDeclaredFields()) {
            if (field.getAnnotation(Param.class) == null) {
                continue;
            }
            list.add(field);


        }

        return (cl.getSuperclass() == null) ? list : searchFields(object, cl.getSuperclass(), list);
    }


    @Param
    private Integer i = 12;

    @Param
    private boolean b = true;

    @Param
    private String s = "ss";

    @Param
    private Color color = Color.red;

    @Param
    private File file = new File("");


}
