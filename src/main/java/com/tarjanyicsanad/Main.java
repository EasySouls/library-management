package com.tarjanyicsanad;

import com.tarjanyicsanad.ui.ApplicationFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationFrame applicationFrame = new ApplicationFrame("Library Management", 1600, 900);
            applicationFrame.show();
        });
    }
}