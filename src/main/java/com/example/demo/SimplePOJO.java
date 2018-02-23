package com.example.demo;

import lombok.Data;

@Data
@MyAnnotation(myProperty = "myProp")
public class SimplePOJO {
    private String id;
}
