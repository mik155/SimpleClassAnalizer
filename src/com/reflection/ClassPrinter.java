package com.reflection;

import java.lang.reflect.*;
import java.util.Arrays;

public class ClassPrinter
{
    private Class classObject;
    private Object classInstance;
    private StringBuilder builder;
    private Utils utils;

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
        utils = new Utils(Mode.SHORT_TYPE_NAME);
    }

    public String printClass()
    {
        printClassHeader();
        utils.print(builder, "\n{\n");
        utils.setIndentation(4);
        utils.print(builder, "    //FIELDS\n");
        printFields();
        utils.print(builder, "\n");
        utils.print(builder, "    //CONSTRUCTORS\n");
        printConstructors();
        utils.print(builder, "\n");
        utils.print(builder, "    //METHODS\n");
        printMethods();
        utils.setIndentation(0);
        utils.print(builder, "}\n");

        return builder.toString();
    }

    private void printClassHeader()
    {
        utils.printModdiefiers(builder, classObject.getModifiers());
        builder.append("class ").append(utils.getName(classObject.getTypeName()));
        TypeVariable [] typeVariables = classObject.getTypeParameters();
        if(typeVariables.length != 0)
        {
            builder.append(" <");
            for(int i = 0; i < typeVariables.length; i++)
            {
                utils.printType(builder, typeVariables[i]);
                if(i + 1 < typeVariables.length)
                    builder.append(", ");
            }
            builder.append(">");
        }
    }

    private void printFields()
    {
        Field[] fields = classObject.getDeclaredFields();
        for (Field f : fields)
        {
            printField(f);
            utils.print(builder, "\n");
        }

    }

    private void printField(Field field)
    {
        utils.printModdiefiers(builder, field.getModifiers());
        utils.printType(builder, field.getGenericType());
        utils.print(builder, " ");
        utils.print(builder, utils.getName(field.getName()));
        utils.print(builder, ";");
    }

    private void printConstructors()
    {
        Constructor [] constructors = classObject.getDeclaredConstructors();
        for(int i = 0; i < constructors.length; i++)
        {
            printConstructor(constructors[i]);
            builder.append("\n");
        }
    }

    private void printConstructor(Constructor c)
    {
        //MODDIFIERS: public, private ...
        utils.printModdiefiers(builder, c.getModifiers());
        //TYPA PARAMETERS: <T, U> ...
        Type [] typeParameters = c.getTypeParameters();
        if(typeParameters.length != 0)
        {
            utils.<Type>printElements(new Separators("<", ", ", ">"), builder, utils::printType, typeParameters);
            utils.print(builder, " ");
        }
        //NAME OF CLASS
        builder.append(utils.getName(classObject.getTypeName()));
        //ARGUMENTS OF CONSTRUCTOR
        Type [] args = c.getGenericParameterTypes();
        utils.<Type>printElements(new Separators("(",", ", ")", " arg"),  builder, utils::printType, args);
        utils.print(builder, ";");
    }

    private void printMethods()
    {
        Method [] methods = classObject.getDeclaredMethods();
        for(Method m : methods)
        {
            printMethod(m);
            utils.print(builder, "\n");
        }
    }

    private void printMethod(Method m)
    {
        utils.printModdiefiers(builder, m.getModifiers());
        Type [] types = m.getTypeParameters();
        if(types.length != 0)
        {
             utils.printElements(new Separators("<", ", ", ">"), builder, utils::printType, m.getTypeParameters());
             utils.print(builder, " ");
        }
        utils.printType(builder, m.getGenericReturnType());
        utils.print(builder, " ");
        utils.print(builder, m.getName());
        utils.printElements(new Separators("(",", ", ")", " arg"), builder, utils::printType, m.getGenericParameterTypes());
        utils.print(builder, ";");
    }
}
