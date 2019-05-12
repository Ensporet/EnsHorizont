package com.Target.WindowComponent;

import com.Target.ITarget;
import com.Target.TargetDefault;

import javax.swing.*;
import java.awt.*;

public class DefaultWindowComponent implements IWindowComponent <TargetDefault> {

    @Override
    public Component[] createComponents(TargetDefault target) {




        JTextArea name = new JTextArea(target.getName()) ;
        name.addCaretListener(e ->{
            modify(target);target.setName(name.getText());
        });
        name.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));

        JTextArea value = new JTextArea(target.getValue());
        value.addCaretListener(e -> {
            modify(target);target.setValue(value.getText());});

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));

        jPanel.add(name);
        jPanel.add(new JSeparator());
        jPanel.add(value);




        Component component[] = new Component[1];
        component[0] = jPanel;


        return component;
    }


    private void modify(ITarget target) {
        if (!target.isModify()) {
            target.setModify(true);
        }

    }

}
