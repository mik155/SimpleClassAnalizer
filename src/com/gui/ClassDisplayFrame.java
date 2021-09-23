package com.gui;

import com.reflection.ClassPrinter;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.Arrays;

public class ClassDisplayFrame extends JFrame
{
    private JTextPane pane;
    private static final Dimension FRAME_SIZE = new Dimension(600, 700);
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

    public ClassDisplayFrame()
    {
        textAttribute = new SimpleAttributeSet();
        StyleConstants.setFontSize(textAttribute, FONT_SIZE);

        setSize(FRAME_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUpperMenu();
        setCenterMenu();
        setVisible(true);
    }

    public ClassDisplayFrame(String className)
    {
        this();
        printClassTextSource(className);
    }

    private void setUpperMenu()
    {
        JPanel upperMenuPanel = new JPanel();
        upperMenuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton button = new JButton("\u21E6");
        button.addActionListener(action ->
        {
            dispose();
            new InitFrame();
        });
        button.setFont(new Font(button.getFont().getName(), Font.PLAIN, 30));
        button.setPreferredSize(new Dimension(80,40));
        button.setBackground(Color.LIGHT_GRAY);
        upperMenuPanel.add(button);
        add(upperMenuPanel, BorderLayout.PAGE_START);
    }

    private void setCenterMenu()
    {
        pane = new JTextPane();
        pane.setEditable(false);
        pane.setBackground(new Color(43, 43, 43));
        pane.setFont(new Font("JetBrains Mono", Font.PLAIN, FONT_SIZE));

        JScrollPane scrollPane = new JScrollPane(pane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void printClassTextSource(String className)
    {
        try
        {
            ClassPrinter printer = new ClassPrinter(className);
            StringParser parser = new StringParser(printer.printClass());
            while (parser.hasNext())
            {
                String nextWord = parser.nextWord();
                int out = Arrays.binarySearch(KEY_WORDS, nextWord);
                if(out < 0)
                    append(pane, nextWord, Color.WHITE);
                else
                    append(pane, nextWord, KEY_WORD);
            }
        }
        catch (ClassNotFoundException e)
        {
            append(pane, "Class was not found. Press the \u21E6 button and try again.", KEY_WORD);
        }
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

        public boolean hasNext()
        {
            return position < string.length();
        }
    }
}

