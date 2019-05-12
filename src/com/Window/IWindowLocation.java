package com.Window;

import javax.swing.*;
import java.awt.*;

public interface IWindowLocation {


    void upperLeft(Window window);
    void upperRight(Window window);
    void upperCenter(Window window);

    void centerLeft(Window window);
    void centerRight(Window window);
    void center(Window window);

    void bottomLeft(Window window);
    void bottomRight(Window window);
    void bottomCenter(Window window);

    void fullScreen(JFrame jFrame);
    void roll(JFrame jFrame);
    void normal(JFrame jFrame);
    void expand(JFrame jFrame);





}
