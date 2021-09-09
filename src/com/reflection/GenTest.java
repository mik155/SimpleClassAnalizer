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

    private <T> GenTest(String a, Comparator<T> a2, Comparable<? extends  T> a3){};
    public <E> GenTest(int a){};
    public GenTest(int a, double b){};

    private <E extends String & Comparator<E>> E get(Comparator<? extends String> obj){return null;}
    protected int foo(String a){return 0;};


}
