package com.tarjanyicsanad.ui;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    LayoutChanger layoutChanger;

    public MenuBar(LayoutChanger layoutChanger) {
        this.layoutChanger = layoutChanger;
        JMenu booksMenu = new JMenu(Screens.BOOKS);
        JMenuItem listBooksMenuItem = new JMenuItem("Listázás");
        listBooksMenuItem.addActionListener(e -> layoutChanger.changeLayout(Screens.BOOKS));
        booksMenu.add(listBooksMenuItem);

        this.add(booksMenu);
    }
}
