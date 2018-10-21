package com.richaelguitar.ioc.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnEvent(listenerMethod = "setOnLongClickListener",listenerClass = View.OnLongClickListener.class,callBackMethod = "onLongClick")
public @interface OnLongClick {
    int[] value();
}
