package com.Window;

import javax.swing.*;
import java.awt.*;


public class WindowLocation implements IWindowLocation {

/*
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();

    В конфигурации с несколькими мониторами вы должны использовать это:

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();

Для фрейма (наследника Component) нужно использовать validate(), для наследников JComponent - revalidate().
//SwingUtilities.updateComponentTreeUI(jFrame);
*/


    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        WindowLocation windowLocation = new WindowLocation();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(100000,100);



        windowLocation.fullScreen(jFrame);
        jFrame.setVisible(true);






    }


    public Dimension getDimension() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }


    @Override
    public void upperLeft(Window window) {

        window.setLocation(0,0);
    }

    @Override
    public void upperRight(Window window) {

        Dimension screenSize = getDimension();
        double width = screenSize.getWidth() - window.getWidth();
        double height = 0;
        window.setLocation((int) width,(int) height);


    }

    @Override
    public void upperCenter(Window window) {

        Dimension screenSize = getDimension();
        double width = (screenSize.getWidth() / 2) - (window.getWidth() /  2);
        double height = 0;
        window.setLocation((int) width,(int) height);
    }

    @Override
    public void centerLeft(Window window) {
        Dimension screenSize = getDimension();
        double width = 0;
        double height = (screenSize.getHeight() / 2) - (window.getHeight() /2) ;
        window.setLocation((int) width,(int) height);

    }

    @Override
    public void centerRight(Window window) {

        Dimension screenSize = getDimension();
        double width = screenSize.getWidth() - window.getWidth();
        double height = (screenSize.getHeight() / 2) - (window.getHeight() /2) ;
        window.setLocation((int) width,(int) height);
    }

    @Override
    public void center(Window window) {

        Dimension screenSize = getDimension();
        double width = (screenSize.getWidth() / 2) - (window.getWidth() / 2);
        double height = (screenSize.getHeight() / 2) - (window.getHeight() /2) ;
        window.setLocation((int) width,(int) height);
    }

    @Override
    public void bottomLeft(Window window) {
        Dimension screenSize = getDimension();
        double width = 0;
        double height = screenSize.getHeight()- window.getHeight() ;
        window.setLocation((int) width,(int) height);

    }

    @Override
    public void bottomRight(Window window) {

        Dimension screenSize = getDimension();
        double width = screenSize.getWidth() - window.getWidth();
        double height = screenSize.getHeight()- window.getHeight() ;
        window.setLocation((int) width,(int) height);
    }

    @Override
    public void bottomCenter(Window window) {
        Dimension screenSize = getDimension();
        double width = (screenSize.getWidth() / 2) - (window.getWidth() / 2);
        double height = screenSize.getHeight()- window.getHeight() ;
        window.setLocation((int) width,(int) height);

    }

    @Override
    public void fullScreen(JFrame jFrame) {

        if (!jFrame.isUndecorated() && !jFrame.isVisible()) {
            jFrame.setUndecorated(true);
         //   jFrame.setVisible(true);
        }

        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.setAlwaysOnTop(true);





    }


    @Override
    public void roll(JFrame jFrame) {

        jFrame.setExtendedState(Frame.ICONIFIED);

    }

    @Override
    public void normal(JFrame jFrame) {
        jFrame.setExtendedState(Frame.NORMAL);

    }

    @Override
    public void expand(JFrame jFrame) {
        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}
