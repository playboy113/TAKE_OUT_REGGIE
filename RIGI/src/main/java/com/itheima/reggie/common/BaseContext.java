package com.itheima.reggie.common;

public class BaseContext {
    private static ThreadLocal<Long> thraedLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
         thraedLocal.set(id);
    }

    public static Long getCurrentId(){
        return thraedLocal.get();
    }
}
