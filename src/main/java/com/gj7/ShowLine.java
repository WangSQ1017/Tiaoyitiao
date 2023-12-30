package com.gj7;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ShowLine extends JFrame {

    public ShowLine() {
    }

    public ShowLine(int width, int height, int x, int y, boolean flag) {
        setTitle("划线窗体");
        setSize(width, height);
        setLocation(x, y);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ShowLineJPanle jPanel = new ShowLineJPanle(flag);
        jPanel.setBackground(new Color(0, 0, 0, 0));
        getContentPane().add(jPanel);
        setVisible(true);
    }

}
