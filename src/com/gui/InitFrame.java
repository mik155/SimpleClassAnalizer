package com.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InitFrame extends JFrame
{
    private static final Dimension FRAME_SIZE = new Dimension(600, 100);
    private JTextField textField;
    private JButton okButton;
    private JButton cancelButton;
    private boolean firstClick = true;

    public static void main(String[] args)
    {
        new InitFrame();
    }

    public InitFrame()
    {
        setPreferredSize(FRAME_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setFrameLocation();
        setJTextField();
        setButtonsMenu();

        pack();
        setVisible(true);
    }

    private void setButtonsMenu()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        okButton = new JButton("OK");
        okButton.addActionListener(event ->
        {
            dispose();
            new ClassDisplayFrame(textField.getText().trim());
        });
        cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(event -> dispose());
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(35, 0)));
        buttonPanel.add(cancelButton);


        add(buttonPanel, BorderLayout.PAGE_END);
    }

    private void setJTextField()
    {
        textField = new JTextField("Type in full class name (package.ClassName).", 87);
        textField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        textField.addKeyListener(new KeyListener()
        {

            @Override
            public void keyTyped(KeyEvent keyEvent)
            {
                String text = textField.getText();
                if(text.length() > 145)
                    textField.setText(text.substring(0, 145));
            }

            @Override
            public void keyPressed(KeyEvent keyEvent)
            {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        textField.addMouseListener(
                new MouseListener()
                {

                    @Override
                    public void mouseClicked(MouseEvent mouseEvent)
                    {

                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent)
                    {
                        if(firstClick)
                        {
                            firstClick = !firstClick;
                            textField.setText("");
                            textField.setFocusable(true);
                            textField.requestFocus();
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {

                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {

                    }
                });

                textField.setFocusable(false);
                add(textField, BorderLayout.CENTER);
    }

    private void setFrameLocation()
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        int sWidth = tk.getScreenSize().width;
        int sHeight = tk.getScreenSize().height;
        int xPos = (sWidth - FRAME_SIZE.width) / 2;
        int yPos = (sHeight - FRAME_SIZE.height) / 2;
        setLocation(new Point(xPos, yPos));
    }
}
