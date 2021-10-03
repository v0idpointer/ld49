package me.v0idpointer.ld49;

import javax.swing.*;
import java.awt.*;

public class Window {

    private final JFrame frame;

    public Window(int width, int height, String title, boolean a) {
        this.frame = new JFrame();
        this.frame.setTitle(title);

        Dimension dimension = new Dimension(width, height);
        this.frame.setPreferredSize(dimension);
        this.frame.setMaximumSize(dimension);
        this.frame.setMinimumSize(dimension);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setAlwaysOnTop(a);
    }

    public JFrame getFrame() {
        return this.frame;
    }

}
