package com.reflection;

import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.*;

public  class  Utils
{
    private Mode mode;
    private List<Type> typeVariables = new LinkedList<>();
    private int indentation;

    public Utils(Mode mode)
    {
        this.mode = mode;
        this.indentation = 0;
    }

    public void setIndentation(int in)
    {
        if(in >= 0)
            indentation = in;
    }

    public void setMode(Mode mode)
    {
        this.mode = mode;
    }

    public Mode getMode(Mode mode)
    {
        return this.mode;
    }

    public void printValueFromField(StringBuilder builder, Object classInstance, Field field) throws IllegalAccessException
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

    public void printModdiefiers(StringBuilder builder, int mod)
    {
        indent(builder, indentation);
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

    public void printType(StringBuilder stringBuilder, Type type)
    {
        print_Type(stringBuilder,type);
    }

    private void print_Type(StringBuilder stringBuilder, Type type)
    {
        if(type instanceof TypeVariable)
            printTypeVariable(stringBuilder, (TypeVariable) type);
        else if(type instanceof ParameterizedType)
            printParametrizedType(stringBuilder, (ParameterizedType) type);
        else if(type instanceof  WildcardType)
            printWildcardType(stringBuilder, (WildcardType) type);
        else if(type instanceof GenericArrayType)
            ;
        else
            stringBuilder.append(getName(type.getTypeName()));
     }

    public void printTypeVariable(StringBuilder builder, TypeVariable typeVariable)
    {
        String name = getName(typeVariable.getTypeName());
        builder.append(name);
        if(!typeVariables.contains(typeVariable))
        {
            typeVariables.add(typeVariable);
            Type[] types = typeVariable.getBounds();

            if (types.length != 0 && (types.length != 1 || !isObjectClassName(types[0].getTypeName())) ) //type form: T extends ...
            {
                builder.append(" extends ");
                for (int i = 0; i < types.length; i++)
                {
                    if(isObjectClassName(types[i].getTypeName()))
                        continue;
                    print_Type(builder, types[i]);
                    if (i < types.length - 1)
                        builder.append(" & ");
                }
            }
        }
    }

    public void printWildcardType(StringBuilder builder, WildcardType type)
    {
        builder.append("?");
        Type [] extendBounds = type.getUpperBounds();
        Type [] superBounds = type.getLowerBounds();

        if(extendBounds.length != 0)
        {
            builder.append(" extends ");
            for(int i = 0; i < extendBounds.length; i++)
            {
                print_Type(builder ,extendBounds[i]);
                if(i < extendBounds.length - 1)
                    builder.append(" & ");
            }
        }

        if(superBounds.length != 0)
        {
            builder.append(" super ");
            for(int i = 0; i < superBounds.length; i++)
            {
                print_Type(builder ,superBounds[i]);
                if(i < superBounds.length - 1)
                    builder.append(" & ");
            }
        }
    }

    public void printParametrizedType(StringBuilder builder, ParameterizedType type)
    {
        String name = getName(type.getRawType().getTypeName());
        builder.append(name).append("<");
        Type [] types = type.getActualTypeArguments();
        for(int i = 0; i < types.length; i++)
        {
            print_Type(builder, types[i]);
            if(types.length + 1 < types.length)
                builder.append(", ");
        }
        builder.append(">");
    }

    public String getName(String name)
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

    public <T> void printElements(Separators separators, StringBuilder builder, BiConsumer<StringBuilder, T> printer, T...elements)
    {
        int length = elements.length;
        builder.append(separators.left());
        for(int i = 0 ;i < length; i++)
        {
            printer.accept(builder, elements[i]);
            if(i + 1 < length)
                builder.append(separators.suffix()).append(separators.separtor()).append(" ");
            else
                builder.append(separators.suffix());
        }
        builder.append(separators.right());
    }

    public void print(StringBuilder builder, String s)
    {
        builder.append(s);
    }

    private boolean isObjectClassName(String str)
    {
        return str.equals("java.lang.Object");
    }

    private void indent(StringBuilder builder, int n)
    {
        while (n-- > 0)
            builder.append(" ");
    }
}
