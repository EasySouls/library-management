package com.tarjanyicsanad.ui;

/**
 * A functional interface for changing the layout of the main window.
 */
@FunctionalInterface
public interface LayoutChanger {

    /**
     * Changes the layout of the main window.
     *
     * @param layout the new layout to use
     */
    void changeLayout(String layout);
}
