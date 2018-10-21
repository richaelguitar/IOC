package com.richaelguitar.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//注解类型为类，接口，枚举
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface ContentView {
    int value();
}
