package com.gj7;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ShowWindows extends JFrame {

    public ShowWindows() {
    }

    public ShowWindows(int width, int height, int x, int y) {
        //设置显示窗口标题
        setTitle("可移动默认窗体");
        //设置窗口显示尺寸
        setSize(width, height);
        setLocation(x, y);
        //置窗口是否可以关闭，关闭窗口后程序将一起关闭
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(0, 0, 0, 0));
        Border redLine = BorderFactory.createLineBorder(Color.red, 10);
        jPanel.setBorder(redLine);

        JButton jButton = new JButton("关闭");
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jButton.setPreferredSize(new Dimension(50,25));
        jButton.setMargin(new Insets(0,0,0,0));
        jPanel.add(jButton);

        JButton closeBorderButton = new JButton("关闭边框");
        closeBorderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Point location = getLocation();
                Dimension size = getSize();
                new ShowTransparentWindows(size.width, size.height, location.x, location.y);
            }
        });
        closeBorderButton.setPreferredSize(new Dimension(70,25));
        closeBorderButton.setMargin(new Insets(0,0,0,0));
        jPanel.add(closeBorderButton);

        getContentPane().add(jPanel);

        setVisible(true);
    }

}
