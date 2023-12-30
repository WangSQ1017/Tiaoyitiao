package com.gj7;

import javax.swing.*;
import java.awt.*;

public class ShowLineJPanle extends JPanel {

    public Boolean flag;

    public ShowLineJPanle(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.red);
        graphics2D.setStroke(new BasicStroke(3.0f));
        if (flag){
            graphics2D.drawLine(0,0, getWidth(), getHeight());
        } else {
            graphics2D.drawLine(0,getHeight(), getWidth(), 0);
        }
    }

}
