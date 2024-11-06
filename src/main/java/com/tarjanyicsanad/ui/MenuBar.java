package com.tarjanyicsanad.ui;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;

    public MenuBar() {
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");

        add(fileMenu);
        add(editMenu);
        add(helpMenu);
    }
}
