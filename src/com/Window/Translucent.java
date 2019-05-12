package com.Window;

import javax.swing.*;
import java.awt.*;

public class Translucent extends JFrame {
    public Translucent() {
        super("Translucent JFrame");
        setLayout(new GridLayout());

        setBounds(400, 300, 300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(new JLabel("Translucent Window Demo"));
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Translucent window = new Translucent();

                if (translucencySupported())
                    window.setOpacity(0.5f);

                window.setVisible(true);
            }
        });
    }

    public static boolean translucencySupported() {
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        return gd.isWindowTranslucencySupported(
                GraphicsDevice.WindowTranslucency.TRANSLUCENT);
    }
}
