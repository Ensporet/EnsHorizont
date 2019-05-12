package com.Target.TargetConstryktor;

import com.Target.ITarget;
import com.Target.TargetDefault;
import com.Window.WindowLocation;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class TargetConstrictor implements ITargetConstryctor {

    private String name ;
    private String value;

    @Override
    public ITarget buldNewTarget() {
        return buldNewTarget(null);
    }
    @Override
    public ITarget buldNewTarget(Window window) {




        JTextArea nameArea = new JTextArea("Name"), value = new JTextArea("Values");
        Window win = createWindow(window , nameArea , value ,createButtonOk( nameArea,value) );

        new WindowLocation().center(win);

        win.setVisible(true);

        return new TargetDefault(getName() , getValue());
    }

    private Window createWindow(Window window , Component ... components) {

        final JDialog WINDOW = (window == null) ? new JDialog() :
                new JDialog(window , Dialog.ModalityType.APPLICATION_MODAL);

        WINDOW.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        WINDOW.setLayout(new GridLayout((components == null) ? 0 : components.length,1));
        WINDOW.setSize(200,200);

        for (Component component : components) {
            WINDOW.add(component);
        }

        new WindowLocation().center(WINDOW);


        return WINDOW;
    }

    private Component createButtonOk(JTextComponent nameText , JTextComponent valueComponent) {
        final JButton OK = new JButton("Ok");

        OK.addActionListener(e -> {
           setName(nameText.getText());
           setValue(valueComponent.getText());
           Window window = SwingUtilities.getWindowAncestor(OK);
           if (window != null) {
               window.dispose();
           }

        });
        return OK;
    }

    protected ITarget createTarget(String name , String value) {
        return new TargetDefault(name , value);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
