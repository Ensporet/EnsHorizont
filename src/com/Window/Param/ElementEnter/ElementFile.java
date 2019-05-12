package com.Window.Param.ElementEnter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;

public class ElementFile extends Element <File, File> {


    public ElementFile(Object object, Field field) {
        super(object, field);
    }

    @Override
    public Component component() {

        JButton jButton = new JButton();
        try {
            getFIELD().setAccessible(true);
            jButton.setText(((File)getFIELD().get(getOBJECT())).getAbsolutePath());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        JPanel jPanel = createPanel(getFIELD().getName(), jButton );
        jButton.addActionListener((e)->{

            JFileChooser jFileChooser = new JFileChooser();

            jFileChooser.setFileFilter(ElementFile.this.fileNameExtensionFilter());

            try {
                getFIELD().setAccessible(true);
                jFileChooser.setSelectedFile((File)getFIELD().get(getOBJECT()));
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }

            if (jFileChooser.showOpenDialog(jPanel) == JFileChooser.APPROVE_OPTION) {
                setValue(jFileChooser.getSelectedFile(),getOBJECT(),getFIELD());
                jButton.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            };











        });

        return jPanel;
    }

    protected FileNameExtensionFilter fileNameExtensionFilter(){
        return null;
    }


    @Override
    protected boolean filter(File string) {
        return true;
    }

    @Override
    protected File toValue(File value) {
        return value;
    }

}
