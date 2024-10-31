package com.tarjanyicsanad.ui;

import javax.swing.*;

public class ApplicationFrame {
    private JFrame frame;

    public ApplicationFrame(String title, int wisdth, int height) {
        frame = new JFrame(title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
    }
}
