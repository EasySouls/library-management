package com.tarjanyicsanad.ui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DateFieldKeyAdapter extends KeyAdapter {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!((c >= '0') && (c <= '9') ||
                (c == KeyEvent.VK_BACK_SPACE) ||
                (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_MINUS)))
        {
            JOptionPane.showMessageDialog(null, "Kérlek YYYY-MM-DD formátumban add meg az évszámot!");
            e.consume();
        }
    }
}
