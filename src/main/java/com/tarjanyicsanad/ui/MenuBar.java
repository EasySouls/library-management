package com.tarjanyicsanad.ui;

import javax.swing.*;

/**
 * A menu bar extending {@link JMenuBar} for the application.
 */
public class MenuBar extends JMenuBar {

    public MenuBar(LayoutChanger layoutChanger, Runnable saveAction, Runnable loadAction) {
        String listString = "Listázás";

        JMenu booksMenu = new JMenu(Screens.BOOKS);
        JMenuItem listBooksMenuItem = new JMenuItem(listString);
        listBooksMenuItem.addActionListener(e -> layoutChanger.changeLayout(Screens.BOOKS));
        booksMenu.add(listBooksMenuItem);
        this.add(booksMenu);

        JMenu authorsMenu = new JMenu(Screens.AUTHORS);
        JMenuItem listAuthorsMenuItem = new JMenuItem(listString);
        listAuthorsMenuItem.addActionListener(e -> layoutChanger.changeLayout(Screens.AUTHORS));
        authorsMenu.add(listAuthorsMenuItem);
        this.add(authorsMenu);

        JMenu membersMenu = new JMenu(Screens.MEMBERS);
        JMenuItem listMembersMenuItem = new JMenuItem(listString);
        listMembersMenuItem.addActionListener(e -> layoutChanger.changeLayout(Screens.MEMBERS));
        membersMenu.add(listMembersMenuItem);
        this.add(membersMenu);

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
