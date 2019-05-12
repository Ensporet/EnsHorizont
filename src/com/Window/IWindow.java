package com.Window;

import java.awt.*;

public interface IWindow {

    public static boolean translucencySupported() {
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        return gd.isWindowTranslucencySupported(
                GraphicsDevice.WindowTranslucency.TRANSLUCENT);
    }
}
