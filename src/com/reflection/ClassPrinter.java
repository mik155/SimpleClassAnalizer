package com.reflection;

import java.lang.reflect.*;

public class ClassPrinter
{
    private Class classObject;
    private Object classInstance;
    private StringBuilder builder;
    private final boolean SHORT_TYPE_NAME_DISPLAY = true;

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

    public  String printClass()
    {
        System.out.println(classObject);
        Field [] fields = classObject.getDeclaredFields();
        for(Field f : fields)
        {
            printField(f);
            builder.append("\n");
        }
        return builder.toString();
    }

    public void printField(Field field)
    {
        printModdiefiers(field.getModifiers());
        printType(field.getGenericType());
        builder.append(" ").append(field.getName()).append(";");
    }

    public void printValueFromField(Field field) throws IllegalAccessException
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

    public void printModdiefiers(int mod)
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

    public void printType(Type type)
    {
        if(type instanceof TypeVariable)
        {
            builder.append(getTypeName(type));
            TypeVariable typeVariable = (TypeVariable) type;
            Type [] boundTypes = typeVariable.getBounds();
            if(boundTypes.length != 0)
                builder.append(" extends ");
            for(int i = 0; i < boundTypes.length; i++)
            {
                printType(boundTypes[i]);
                if(i < boundTypes.length - 1)
                    builder.append(" & ");
            }
        } // if(instance of TypeVariable)
        else if(type instanceof WildcardType)
        {
            builder.append(getTypeName(type));
            WildcardType wildcardType = (WildcardType) type;
            Type [] lowerBounds = wildcardType.getLowerBounds();
            Type [] upperBounds = wildcardType.getUpperBounds();
            if(lowerBounds.length != 0)
            {
                builder.append(" extends ");
                for(int i = 0; i < lowerBounds.length; i++)
                {
                    printType(lowerBounds[i]);
                    if(i < lowerBounds.length - 1)
                        builder.append(" & ");
                }
            }
            else if(upperBounds.length != 0)
            {
                builder.append(" super ");
                for(int i = 0; i < upperBounds.length; i++)
                {
                    printType(upperBounds[i]);
                    if(i < upperBounds.length - 1)
                        builder.append(" & ");
                }
            }
        }//else if(instance of WildcardType)
        else if(type instanceof ParameterizedType)
        {
            builder.append(getTypeName(type));
            builder.append("<");
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type [] genArguments = parameterizedType.getActualTypeArguments();
            for(int i = 0; i < genArguments.length; i++)
            {
                printType(genArguments[i]);
                if(i < genArguments.length - 1)
                    builder.append(", ");
            }
            builder.append(">");
        }
        else if(type instanceof GenericArrayType)
        {
            GenericArrayType arrayType = (GenericArrayType) type;
            printType(arrayType.getGenericComponentType());
            builder.append(" [] ").append(arrayType.getTypeName());
        }
        else
        {
            builder.append(getTypeName(type));
        }
    }//printType(Type)

    private String getTypeName(Type type)
    {
        String name = type.getTypeName();
        if(SHORT_TYPE_NAME_DISPLAY == true)
        {
            int index = name.lastIndexOf(".");
            return name.substring(index == -1 ? 0 : index+1, name.length());
        }
        else
            return type.getTypeName();
    }
}
