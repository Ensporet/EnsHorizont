package com.Window.PagePanel;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageAnnotation {

    String name();

}
