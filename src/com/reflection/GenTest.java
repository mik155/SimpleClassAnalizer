package com.reflection;

import java.lang.reflect.*;
import java.util.Comparator;
import java.util.function.Predicate;

public class GenTest <T extends String & Predicate<Predicate<Integer>>, U extends Predicate<? extends Integer> & Comparator<? extends U>>
{
    public Comparator<String> fieldS;
    public Comparator<T> field0;
    public T field1;
    public Comparator<? extends String> field2;
    public Comparator<? super String> field3;
    private Comparator<?> field4;
    private String field5;
    private int field6;
    private int field7;
    private int field8;
    private int field9;
    private int field10;
    private int field62;
    private int field36;
    private int field156;
    private int field6412;
    private int field63123;
    private int field6131;
    private int f3ield6;
    private int afield6;
    private int fiteld6;
    private int fieled6;

    private <T> GenTest(String a, Comparator<T> a2, Comparable<? extends  T> a3){};
    public <E> GenTest(int a){};
    public GenTest(int a, double b){};

    private <E extends String & Comparator<E>> E get(Comparator<? extends String> obj){return null;}
    protected int foo(String a){return 0;};


}
