package com.reflection;

import java.lang.reflect.*;
import java.util.Comparator;
import java.util.function.Predicate;

public class GenTest <T extends String & Predicate<Integer>, U>
{
    private <E extends String & Comparator<E>> E get(Comparator<? extends String> obj){return null;}
    public Comparator<T> field0;
    public T field1;
    public Comparator<? extends String> field2;
    public Comparator<? super String> field3;
    private Comparator<?> field4;
    private String field5;
    private int field6;


    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException {
        Class cObj = GenTest.class;
        Object obj = Class.forName("com.reflection.GenTest");

        System.out.println(obj.getClass());
         for(TypeVariable t : cObj.getTypeParameters()) {
             printType(t);
         }

    }

    public static void printType(Type type)
    {
        if(type instanceof TypeVariable)
        {
            TypeVariable typeVariable = (TypeVariable) type;
            System.out.println(type.getTypeName() + " ");
            Type [] bounds = typeVariable.getBounds();
            if(bounds.length != 0)
                System.out.println(" extends ");
            for(Type tB : bounds)
                printType(tB);
        }
        else if(type instanceof ParameterizedType)
        {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            System.out.println(parameterizedType);
         }
    }



}
