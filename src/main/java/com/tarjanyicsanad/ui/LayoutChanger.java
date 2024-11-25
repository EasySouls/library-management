package com.tarjanyicsanad.ui;

/**
 * A functional interface for changing the layout of the main window.
 */
@FunctionalInterface
public interface LayoutChanger {
    void changeLayout(String layout);
}
