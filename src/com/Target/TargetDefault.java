package com.Target;


import com.Target.StatusTarget.IStatusTarget;
import com.Target.StatusTarget.StatusTargetOfDefault;
import com.Target.WindowComponent.DefaultWindowComponent;

import java.awt.*;
import java.io.File;


public class TargetDefault implements ITarget {


    @DisplayInTheTable(name = "Name")
    private String name;
    @DisplayInTheTable(name = "Value")
    private String value;

    private IStatusTarget statusTarget;
    private boolean modify = false;

    private File file;

    public TargetDefault() {

        setStatus(new StatusTargetOfDefault());
    }


    public TargetDefault(String name , String value) {
        setName(name);
        setValue(value);
    }

    public TargetDefault(String name , String value , File file) {
      this(name , value);
        setFile(file);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
    this.name = name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
this.value = value;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void setFile(File f) {
        this.file = f;
    }

    @Override
    public IStatusTarget getStatus() {
        return this.statusTarget;
    }

    @Override
    public void setStatus(IStatusTarget status) {

        this.statusTarget = status;
    }

    @Override
    public boolean isModify() {
        return this.modify;
    }

    @Override
    public void setModify(boolean modify) {
        this.modify = modify;

    }

    @Override
    public boolean isEmpty() {
        return ((getName() == null || getName().isEmpty()) && (getValue() == null || getValue().isEmpty()));
    }

    @Override
    public Component[] createWindowComponents() {
        return new DefaultWindowComponent().createComponents(this);
    }


}
