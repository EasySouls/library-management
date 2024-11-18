package com.tarjanyicsanad.ui;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(LayoutChanger layoutChanger, Runnable saveAction, Runnable loadAction) {
        JMenu booksMenu = new JMenu(Screens.BOOKS);
        JMenuItem listBooksMenuItem = new JMenuItem("Listázás");
        listBooksMenuItem.addActionListener(e -> layoutChanger.changeLayout(Screens.BOOKS));
        booksMenu.add(listBooksMenuItem);
        this.add(booksMenu);

        JMenu authorsMenu = new JMenu(Screens.AUTHORS);
        JMenuItem listAuthorsMenuItem = new JMenuItem("Listázás");
        listAuthorsMenuItem.addActionListener(e -> layoutChanger.changeLayout(Screens.AUTHORS));
        authorsMenu.add(listAuthorsMenuItem);
        this.add(authorsMenu);

        JMenu fileMenu = new JMenu("Fájl");
        JMenuItem saveMenuItem = new JMenuItem("Mentés");
        saveMenuItem.addActionListener(e -> saveAction.run());
        fileMenu.add(saveMenuItem);
        JMenuItem loadMenuItem = new JMenuItem("Betöltés");
        loadMenuItem.addActionListener(e -> loadAction.run());
        fileMenu.add(loadMenuItem);
        this.add(fileMenu);
    }
}
