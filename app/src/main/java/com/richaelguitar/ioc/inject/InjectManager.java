package com.richaelguitar.ioc.inject;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.richaelguitar.ioc.annotation.BindView;
import com.richaelguitar.ioc.annotation.ContentView;
import com.richaelguitar.ioc.annotation.OnEvent;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;


public class InjectManager {

    private static final String TAG = InjectManager.class.getSimpleName() ;
    private WeakReference<Activity> activityWeakReference;

    private static InjectManager viewInject;

    private InjectManager(){
    }
    public static InjectManager with(){
        if(viewInject == null){
            synchronized (InjectManager.class){
                if(viewInject == null){
                    viewInject = new InjectManager();
                }
            }
        }
        return viewInject;
    }
    public  void inject(Activity activity){
        this.activityWeakReference = new WeakReference<>(activity);
        injectLayout();
        injectView();
        injectEvent();
    }





    private void injectLayout() {
        Activity activity = this.activityWeakReference.get();
        if(activity!=null){
            try {
                //获取该activity上的注解
                ContentView contentViewAnnotation = activity.getClass().getAnnotation(ContentView.class);
                if(contentViewAnnotation!=null){
                    //获取该注解的值
                    int layoutId = contentViewAnnotation.value();
                    //获取activity的setContentView方法
                    // getMethod是获取该类的所有公共方法包括继承的方法，getDeclaredMethod是获取类或接口的指定已声明方法，不包含继承的方法
                    Method method = activity.getClass().getMethod("setContentView", int.class);
                    //调用该方法
                    method.invoke(activity,layoutId);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    private void injectView() {
        Activity activity = activityWeakReference.get();
        if(activity!=null){
            //获取类声明的所有属性
            Field[] fields = activity.getClass().getDeclaredFields();
            for(Field field:fields){
                //获取属性的注解
                BindView bindViewAnnotation = field.getAnnotation(BindView.class);
                if(bindViewAnnotation!=null){
                    try {
                        //获取注解的值
                        int viewId = bindViewAnnotation.value();
                        //获取findViewById方法
                        Method method = activity.getClass().getMethod("findViewById", int.class);
                        Object object = method.invoke(activity,viewId);
                        //设置属性的值
                        field.setAccessible(true);
                        field.set(activity,object);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void injectEvent() {
        final Activity activity = activityWeakReference.get();
        try {
            if(activity!=null){
                //获取类上的所有方法
                Method[] methods = activity.getClass().getDeclaredMethods();
                //遍历所有方法
                for (final Method targetMethod :methods) {
                    //过滤静态方法和私有方法
                    if (Modifier.isStatic(targetMethod.getModifiers())) {
                        continue;
                    }
                    //获取该方法的所有注解
                    Annotation[] annotations = targetMethod.getAnnotations();
                    Log.d(TAG,""+annotations.length);
                    if(annotations!=null){
                        //遍历所有注解
                        for (Annotation annotation:annotations) {
                            //获取该注解上的类型
                            Class<?> annotationType =annotation.annotationType();
                            if(annotationType!=null){
                                //获取注解上的注解
                                OnEvent event = annotationType.getAnnotation(OnEvent.class);
                                //事件的三要素
                                String listenerMethod = event.listenerMethod();
                                final Class<?> listenerClass = event.listenerClass();
                                final String callBackMethod = event.callBackMethod();
                                //获取注解的值
                                final Method value = annotationType.getDeclaredMethod("value");
                                if(value!=null){
                                    int[] viewIds = (int[]) value.invoke(annotation);
                                    //遍历赋值控件
                                    for (int id:viewIds) {
                                        final View view = activity.findViewById(id);
                                        if(view == null){
                                            continue;
                                        }
                                        //调用view的代理方法listenerMethod
                                        Method setListenerMethod = view.getClass().getMethod(listenerMethod,listenerClass);
                                        //动态代理
                                        InvocationHandler handler = new InvocationHandler(){
                                            /**
                                             *
                                             * @param o 目标对象
                                             * @param method 被代理的方法
                                             * @param objects
                                             * @return
                                             * @throws Throwable
                                             */
                                            @Override
                                            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                                                if(method.getName().equalsIgnoreCase(callBackMethod)){
                                                    return targetMethod.invoke(activity,objects);
                                                }else {
                                                    return method.invoke(activity, objects);
                                                }
                                            }
                                        };
                                        Object proxyInstance = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class<?>[]{listenerClass}, handler);
                                        setListenerMethod.invoke(view,proxyInstance);
                                    }
                                }


                            }
                        }
                    }
                }
            }
        }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
    }


}
