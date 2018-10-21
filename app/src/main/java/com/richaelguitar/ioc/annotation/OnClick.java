package com.richaelguitar.ioc.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnEvent(listenerMethod = "setOnClickListener",listenerClass = View.OnClickListener.class,callBackMethod = "onClick")
public @interface OnClick  {
    int[] value();
}
