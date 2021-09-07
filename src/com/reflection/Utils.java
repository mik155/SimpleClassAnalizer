package com.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Utils
{
    public static void printValueFromField(StringBuilder builder, Object classInstance, Field field) throws IllegalAccessException
    {
        if(field.getType().isPrimitive())
        {
            Class fieldClass = field.getType();
            if(fieldClass == int.class)
                builder.append(field.getInt(classInstance));
            if(fieldClass == double.class)
                builder.append(field.getDouble(classInstance));
            if(fieldClass == float.class)
                builder.append(field.getFloat(classInstance));
            if(fieldClass == boolean.class)
                builder.append(field.getBoolean(classInstance));
            if(fieldClass == long.class)
                builder.append(field.getLong(classInstance));
            if(fieldClass == short.class)
                builder.append(field.getShort(classInstance));
            if(fieldClass == char.class)
                builder.append(field.getChar(classInstance));
            if(fieldClass == byte.class)
                builder.append(field.getByte(classInstance));
        }
        else
        {
            Object obj = field.get(classInstance);
            builder.append(obj == null ? "null" : obj.hashCode());
        }
    }

    public static void printModdiefiers(StringBuilder builder, int mod)
    {
        if(Modifier.isPublic(mod))
            builder.append("public ");
        else if(Modifier.isPrivate(mod))
            builder.append("private ");
        else if(Modifier.isProtected(mod))
            builder.append("protected ");

        if(Modifier.isStatic(mod))
            builder.append("static ");
        if(Modifier.isAbstract(mod))
            builder.append("abstract ");
        if(Modifier.isFinal(mod))
            builder.append("final ");
    }

}
