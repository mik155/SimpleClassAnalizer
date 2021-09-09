package com.reflection;

public class Separators
{
    private int counter = -1;
    private final String leftEdge;
    private final String rightEdge;
    private final String separator;
    private final String argumentSuffix;

    public Separators(String leftEdge, String separator, String rightEdge)
    {
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
        this.separator = separator;
        this.argumentSuffix = null;
    }

    public Separators(String leftEdge, String separator, String rightEdge, String argumentSuffix)
    {
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
        this.separator = separator;
        this.argumentSuffix = argumentSuffix;
    }

    public String left()
    {
        return leftEdge;
    }

    public String right() {
        return rightEdge;
    }

    public String separtor() {
        return separator;
    }

    public String suffix()
    {
        if(argumentSuffix == null)
            return "";
        else
        {
            counter++;
            return argumentSuffix + Integer.toString(counter);
        }
    }
}
