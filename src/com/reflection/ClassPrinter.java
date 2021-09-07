package com.reflection;

import java.lang.reflect.*;

public class ClassPrinter
{
    private Class classObject;
    private Object classInstance;
    private StringBuilder builder;

    public static void main(String[] args)
    {
        try
        {
            ClassPrinter classPrinter = new ClassPrinter("com.reflection.GenTest");
            System.out.println(classPrinter.printClass());
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Class wasn't found");
        }
    }

    public ClassPrinter(String className) throws ClassNotFoundException
    {
        classObject = Class.forName(className);
        builder = new StringBuilder();
    }

    public String printClass()
    {
        ClassHeaderPrinter.printClassHeader(builder, classObject, Mode.SHORT_TYPE_NAME);
        return builder.toString();
    }



}
