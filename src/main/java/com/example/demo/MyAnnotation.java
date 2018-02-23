package com.example.demo;

import java.lang.annotation.*;


@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String myProperty();
}

