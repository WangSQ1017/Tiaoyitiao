package com.gj7;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

public class ShowTransparentWindows extends JFrame {

    private int lineXStart = 100;
    private int lineYStart = 100;
    private int lineXEnd = 0;
    private int lineYEnd = 0;

    public ShowTransparentWindows() {
    }

    public ShowTransparentWindows(int width, int height, int x, int y) {
        //设置显示窗口标题
        setTitle("透明框");
        //设置窗口显示尺寸
        setSize(width, height);
        setLocation(x, y);
        // 窗口去边框
        setUndecorated(true);
        // 设置窗口为透明色
        setBackground(new Color(0, 0, 0, 0));
        //置窗口是否可以关闭，关闭窗口后程序将一起关闭
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final int[] xOld = {0};
        final int[] yOld = {0};
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOld[0] = e.getX();//记录鼠标按下时的坐标
                yOld[0] = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen();
                int yOnScreen = e.getYOnScreen();
                int xx = xOnScreen - xOld[0];
                int yy = yOnScreen - yOld[0];
                ShowTransparentWindows.this.setLocation(xx, yy);//设置拖拽后，窗口的位置
            }
            @Override
            public void mouseMoved (MouseEvent e) {
                System.out.println(e);
            }
        });

        MyJPanel jPanel = new MyJPanel(this);
        jPanel.setBackground(new Color(0, 0, 0, 0));
        Border redLine = BorderFactory.createLineBorder(Color.red, 10);
        jPanel.setBorder(redLine);

        MyJButton jButton = new MyJButton("关闭", this);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException nativeHookException) {
                    nativeHookException.printStackTrace();
                }
            }
        });
        jButton.setPreferredSize(new Dimension(30, 25));
        jButton.setMargin(new Insets(0, 0, 0, 0));
        jPanel.add(jButton);


//        MyJButton openBorderButton = new MyJButton("开启边框");
//        openBorderButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//                Point location = getLocation();
//                Dimension size = getSize();
//                new ShowWindows(size.width, size.height, location.x, location.y);
//            }
//        });
//        openBorderButton.setPreferredSize(new Dimension(50, 25));
//        openBorderButton.setMargin(new Insets(0, 0, 0, 0));
//        jPanel.add(openBorderButton);

        MyJButton ScreenButton = new MyJButton("截图", this);
        ScreenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screenPic();
            }
        });
        ScreenButton.setPreferredSize(new Dimension(30, 25));
        ScreenButton.setMargin(new Insets(0, 0, 0, 0));
        jPanel.add(ScreenButton);

        MyJButton resetButton = new MyJButton("变大小", this);
        resetButton.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen();
                int yOnScreen = e.getYOnScreen();
                Point location = getLocation();
                int newWidth = xOnScreen - location.x;
                int newHeight = yOnScreen - location.y;
                if (newWidth > 150 || newHeight > 50) {
                    if (newWidth <= 150) {
                        newWidth = 150;
                    }
                    if (newHeight <= 50) {
                        newHeight = 50;
                    }
                    setSize(newWidth, newHeight);
                }
            }
        });
        resetButton.setPreferredSize(new Dimension(50, 25));
        resetButton.setMargin(new Insets(0, 0, 0, 0));
        jPanel.add(resetButton);

        getContentPane().add(jPanel);

        setVisible(true);
    }

    public BufferedImage screenPic() {
        try {
            Point location = getLocation();
            Dimension size = getSize();
            Robot robot = null;
            robot = new Robot();
            Rectangle screenRect = new Rectangle(location.x + 10, location.y + 10 + 35, size.width - 20, size.height - 10 - 10 - 35);
            BufferedImage screenshot = robot.createScreenCapture(screenRect);
            File file = new File("C:\\Users\\DELL\\Desktop\\新建文件夹\\" + System.currentTimeMillis() + ".png");
            ImageIO.write(screenshot, "png", file);
            return screenshot;

        } catch (AWTException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
