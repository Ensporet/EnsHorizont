package com.Target.WindowComponent;

import com.Target.ITarget;

import java.awt.*;

public interface IWindowComponent <T extends ITarget> {

    Component[] createComponents(T target);
}
