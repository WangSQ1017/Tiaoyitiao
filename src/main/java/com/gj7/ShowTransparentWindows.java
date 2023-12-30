package com.gj7;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShowTransparentWindows extends JFrame {

    public static double time = 4.30D;
    public static Boolean showLineFlag = true;

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
        });

        MyJPanel jPanel = new MyJPanel(this);
        jPanel.setBackground(new Color(0, 0, 0, 0));
        Border redLine = BorderFactory.createLineBorder(Color.red, 10);
        jPanel.setBorder(redLine);

        MyJButton jButton = new MyJButton("关闭", this);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (GlobalKeyListener.showLine != null){
                    GlobalKeyListener.showLine.dispose();
                }
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

//        MyJButton ScreenButton = new MyJButton("截图", this);
//        ScreenButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                screenPic(true);
//            }
//        });
//        ScreenButton.setPreferredSize(new Dimension(30, 25));
//        ScreenButton.setMargin(new Insets(0, 0, 0, 0));
//        jPanel.add(ScreenButton);

        JCheckBox jCheckBox = new JCheckBox("是否显示辅助线", true);
        jCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (1 == e.getStateChange()){
                    showLineFlag = true;
                } else {
                    showLineFlag = false;
                }
            }
        });
        jPanel.add(jCheckBox);

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

        JTextField jTextField = new JTextField();
        jTextField.setPreferredSize(new Dimension(30, 25));
        jTextField.setText("4.30");
        jTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                double temp = time;
                try {
                    temp = Double.parseDouble(jTextField.getText());
                } catch (Exception ignored){
                }
                time = temp;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                double temp = time;
                try {
                    temp = Double.parseDouble(jTextField.getText());
                } catch (Exception ignored){
                }
                time = temp;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        jPanel.add(jTextField);

        JLabel jLabel = new JLabel("作者: 十七有丶瘦，本程序免费，严禁买卖！！");
//        jLabel.setForeground(Color.blue);
        jLabel.setHorizontalAlignment(JLabel.CENTER);
        jLabel.setVerticalAlignment(JLabel.BOTTOM);
        jPanel.add(jLabel);


        getContentPane().add(jPanel);



        setVisible(true);
    }

    public BufferedImage screenPic(Boolean save) {
        try {
            Point location = getLocation();
            Dimension size = getSize();
            Robot robot = null;
            robot = new Robot();
            Rectangle screenRect = new Rectangle(location.x + 10, location.y + 10 + 35 + 25, size.width - 20, size.height - 10 - 10 - 35);
            BufferedImage screenshot = robot.createScreenCapture(screenRect);
            if (save){
                File file = new File("C:\\Users\\ws615\\Desktop\\新建文件夹\\" + System.currentTimeMillis() + ".png");
                ImageIO.write(screenshot, "png", file);
            }
            return screenshot;

        } catch (AWTException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
