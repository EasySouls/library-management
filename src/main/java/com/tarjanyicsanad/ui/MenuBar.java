package com.tarjanyicsanad.ui;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(LayoutChanger layoutChanger) {
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
    }
}
