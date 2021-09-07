package com.reflection;

import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.*;

public class ClassHeaderPrinter
{
    private static StringBuilder builder = null;
    private static Class objClass = null;
    private static Mode mode;
    private static List<TypeVariable> typeVariables = new LinkedList<>();


    public static void printClassHeader(StringBuilder builder, Class objClass, Mode mode)
    {
        ClassHeaderPrinter.mode = mode;
        ClassHeaderPrinter.builder = builder;
        ClassHeaderPrinter.objClass = objClass;

        printClassHeader();
    }

    private static void printClassHeader()
    {
        Utils.printModdiefiers(builder, objClass.getModifiers());
        builder.append("class ").append(getName(objClass.getTypeName()));
        TypeVariable [] typeVariables = objClass.getTypeParameters();
        if(typeVariables.length != 0)
        {
            builder.append(" <");
            for(int i = 0; i < typeVariables.length; i++)
            {
                printType(typeVariables[i]);
                if(i + 1 < typeVariables.length)
                    builder.append(", ");
            }
            builder.append(">");
        }
    }

    private static void printType(Type type)
    {
        if(type instanceof TypeVariable)
            printTypeVariable((TypeVariable) type);
        else if(type instanceof ParameterizedType)
            printParametrizedType((ParameterizedType) type);
        else if(type instanceof  WildcardType)
            printWildcardType((WildcardType) type);
        else if(type instanceof GenericArrayType)
            ;
        else
            builder.append(getName(type.getTypeName()));
    }

    private static void printTypeVariable(TypeVariable typeVariable)
    {
        String name = getName(typeVariable.getTypeName());
        builder.append(name);
        if(!typeVariables.contains(typeVariable))
        {
            typeVariables.add(typeVariable);
            Type[] types = typeVariable.getBounds();
            if (types.length != 0) //type form: T extends ...
            {
                builder.append(" extends ");
                for (int i = 0; i < types.length; i++) {
                    printType(types[i]);
                    if (i < types.length - 1)
                        builder.append(" & ");
                }
            }
        }
    }

    private static void printWildcardType(WildcardType type)
    {
        builder.append("?");
        Type [] extendBounds = type.getUpperBounds();
        Type [] superBounds = type.getLowerBounds();

        if(extendBounds.length != 0)
        {
            builder.append(" extends ");
            for(int i = 0; i < extendBounds.length; i++)
            {
                printType(extendBounds[i]);
                if(i < extendBounds.length - 1)
                    builder.append(" & ");
            }
        }

        if(superBounds.length != 0)
        {
            builder.append(" super ");
            for(int i = 0; i < superBounds.length; i++)
            {
                printType(superBounds[i]);
                if(i < superBounds.length - 1)
                    builder.append(" & ");
            }
        }
    }

    private static void printParametrizedType(ParameterizedType type)
    {
        String name = getName(type.getRawType().getTypeName());
        builder.append(name).append("<");
        Type [] types = type.getActualTypeArguments();
        for(int i = 0; i < types.length; i++)
        {
            printType(types[i]);
            if(types.length + 1 < types.length)
               builder.append(", ");
        }
        builder.append(">");
    }

    private static String getName(String name)
    {
        if(mode == Mode.SHORT_TYPE_NAME)
        {
            int pos = name.lastIndexOf(".") + 1;
            return name.substring(pos < 0 ? 0 : pos);
        }
        else if(mode == Mode.FULL_TYPE_NAME)
        {
            return name;
        }
        return null;
    }
}
