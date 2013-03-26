
package net.specialattack.settling.launcher;

import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, MouseMotionListener {

    public static int MouseX;
    public static int MouseY;
    public static int MouseDx;// dragged
    public static int MouseDy;
    public static int MousePx;// Pressed (click)
    public static int MousePy;
    public static boolean dragged = false;

    static Boolean released = false;
    public static int MouseButton;

    public void mouseDragged(MouseEvent e) {
        MouseDx = e.getX();
        MouseDy = e.getY();

    }

    public void mouseMoved(MouseEvent e) {
        MouseX = e.getX();
        MouseY = e.getY();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        MouseButton = e.getButton();
        MousePx = e.getX();
        MousePy = e.getY();
        MouseDx = MousePx;
        MouseDy = MousePy;
        dragged = true;
    }

    public void mouseReleased(MouseEvent e) {
        MouseButton = 0;
        dragged = false;
    }

}
