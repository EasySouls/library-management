package com.tarjanyicsanad.ui;

import javax.swing.*;

public class ApplicationFrame {
    private JFrame frame;

    public ApplicationFrame(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
    }
}
