package com.Window.Param;

import java.lang.annotation.*;

/**
 * @TypeOfValue
 * Component method( Object obj , Field field )
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface TypeOfValue {

  Class type();

}
