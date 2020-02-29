package com.example.demo.config;

import java.lang.reflect.InvocationTargetException;

import org.springframework.context.ApplicationContext;

class EnvironmentRunner {

    @SuppressWarnings("unchecked")
    void run(Class<? extends EnvironmentRoot> environmentClass, ApplicationContext applicationContext) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<? extends EnvironmentRoot> superclass = (Class<? extends EnvironmentRoot>) environmentClass.getSuperclass();
        if (!superclass.equals(EnvironmentRoot.class)) {
            run(superclass, applicationContext);
        }

        EnvironmentRoot environmentRoot = environmentClass.getDeclaredConstructor().newInstance();
        environmentRoot.run(applicationContext);
    }

}
