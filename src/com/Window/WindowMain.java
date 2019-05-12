package com.Window;


import com.EnsClassLoader.EnsClassLoader;
import com.MainClass;
import com.Window.PagePanel.PageAnnotation;
import com.Window.PagePanel.PagePanel;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WindowMain extends JFrame implements IWindow {



    final private List<PagePanel> pagePanelsArray = new ArrayList<>();
    final private JTabbedPane TABLE_PANEL = new JTabbedPane();


    public WindowMain () {


        this.addWindowListener(this.windowListener());
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(200, 200);
        this.setContentPane(this.TABLE_PANEL);
        this.fillPagePanelArray();
        this.pack();
        new WindowLocation().center(this);
        this.setVisible(true);

    }



    protected WindowListener windowListener(){
        return new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

               for (PagePanel pagePanel : WindowMain.this.getPagePanelsArray()) {

                   pagePanel.close();
               }
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };

    }



//----------------------------------------------------------------------------------------------------------------------

    private void fillPagePanelArray() {




        this.getPagePanelsArray().clear();
        for (Class cl : new EnsClassLoader(MainClass.class).getAllClass(e->{
            return true;
        })) {

            PageAnnotation pageAnnotation ;
            if ((pageAnnotation = (PageAnnotation) cl.getAnnotation(PageAnnotation.class)) != null) {
                try {
                    Object object =  cl.getDeclaredConstructor().newInstance();
                    if (object instanceof PagePanel) {
                        getPagePanelsArray().add((PagePanel) object);
                        getTABLE_PANEL().addTab(pageAnnotation.name() , ((PagePanel)object).createConteiner());

                    }

                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    //------------------------------------------------------------------------------------------------------------------

    public List<PagePanel> getPagePanelsArray() {
        return pagePanelsArray;
    }

    public JTabbedPane getTABLE_PANEL() {
        return TABLE_PANEL;
    }
}
