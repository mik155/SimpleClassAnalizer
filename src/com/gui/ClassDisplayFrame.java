package com.gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.Scanner;

public class ClassDisplayFrame extends JFrame
{
    private JTextPane pane;
    private static final int FONT_SIZE = 15;
    private static SimpleAttributeSet textAttribute;
    private static final String [] KEY_WORDS = { "abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "extends", "false",
            "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native",
            "new", "null", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true",
            "try", "void", "volatile", "while" };

    public static final Color KEY_WORD = new Color(0xCC, 0x78, 0x32);

    public static void main(String[] args) {
        new ClassDisplayFrame();
    }
    public ClassDisplayFrame()
    {
        textAttribute = new SimpleAttributeSet();
        StyleConstants.setFontSize(textAttribute, FONT_SIZE);

        setSize(new Dimension(600, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUpperMenu();
        setCenterMenu();
        setVisible(true);
    }

    private void setUpperMenu()
    {
        JPanel upperMenuPanel = new JPanel();
        upperMenuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton button = new JButton("<--");
        button.setPreferredSize(new Dimension(60,40));
        button.setBackground(Color.LIGHT_GRAY);
        upperMenuPanel.add(button);
        add(upperMenuPanel, BorderLayout.PAGE_START);
    }

    private void setCenterMenu()
    {
        pane = new JTextPane();
        pane.setBackground(new Color(43, 43, 43));
        pane.setFont(new Font("JetBrains Mono", Font.PLAIN, FONT_SIZE));

        JScrollPane scrollPane = new JScrollPane(pane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void printClassTextSource(String className)
    {

    }

    public void append(JTextPane pane, String text, Color color)
    {
        StyleConstants.setForeground(textAttribute, color);
        Document doc = pane.getDocument();
        try
        {
            doc.insertString(doc.getLength(), text, textAttribute);
        }
        catch (BadLocationException e)
        {
            System.out.println(e);
        }
    }

    private class StringParser
    {
        private String string;
        int position;

        public StringParser(String string)
        {
            this.string = string;
            position = 0;
        }

        public String nextWord()
        {
            int len = string.length();
            StringBuilder builder = new StringBuilder();
            while (position < len && string.charAt(position) == ' ')
            {
                builder.append(' ');
                position++;
            }

            if(builder.length() == 0)
            {
                while (position < len && string.charAt(position) != ' ')
                {
                    builder.append(string.charAt(position));
                    position++;
                }
            }

            return builder.toString();
        }
    }

    private static class ClassPrinter
    {

    }
}

