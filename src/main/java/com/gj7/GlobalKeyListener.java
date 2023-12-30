package com.gj7;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GlobalKeyListener implements NativeKeyListener {
    private ShowTransparentWindows transparentWindows;
    public static ShowLine showLine;
    private static List<Integer> colorList = new ArrayList<>();

    public GlobalKeyListener(ShowTransparentWindows jFrame) {
        this.transparentWindows = jFrame;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_F1) {
            // RGB 149 140 179
            BufferedImage bufferedImage = transparentWindows.screenPic(false);
            try {
                if (showLine != null) {
                    showLine.dispose();
                }
                Point location = MouseInfo.getPointerInfo().getLocation();
                int endX = location.x;
                int endY = location.y;
                Point frameLocation = transparentWindows.getLocation();
                int[] top = {143,134,175};
                int[] bottom = {149,140,179};
                int[] startPoint = analyzingImages(bufferedImage, top, bottom);
                int startX = startPoint[0] + frameLocation.x + 10;
                int startY = startPoint[1] + frameLocation.y + 45;
                if (ShowTransparentWindows.showLineFlag){
                    if (endX > startX) {
                        showLine = new ShowLine(endX - startX, Math.abs(endY - startY), startX, endY, false);
                    } else {
                        showLine = new ShowLine(startX - endX, Math.abs(startY - endY), endX, endY, true);
                    }
                    showLine.setAlwaysOnTop(true);
                }
                double sqrt = Math.sqrt(Math.pow(Math.abs(endX - startX), 2) + Math.pow(Math.abs(endY - startY), 2));
                transparentWindows.screenPic(true);
                Robot robot = new Robot();
                if (ShowTransparentWindows.showLineFlag){
                    robot.mouseMove(location.x, location.y - 2);
                }
                robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                double pressTemp = ShowTransparentWindows.time * sqrt;
                if (pressTemp > 850.0){
                    pressTemp = pressTemp * 0.95;
                }
                if (pressTemp < 280.0){
                    pressTemp = pressTemp * 1.05;
                }
                long press = new Double(pressTemp).longValue();
//                System.out.println("距离：" + sqrt + "像素，按" + press + "毫秒");
                Thread.sleep(press);
                robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
//        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }


    public int[] analyzingImages(BufferedImage bufferedImage, int[] top, int[] bottom) throws IOException {
        int[] real = {0, 0};
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        int[] rgb = new int[3];

        for (int i = minx; i < height; i++) {
            for (int j = miny; j < width; j++) {
                int pixel = bufferedImage.getRGB(j, i);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                if (rgb[0] >= top[0] &&
                        rgb[0] <= bottom[0] &&
                        rgb[1] >= top[1] &&
                        rgb[1] <= bottom[1] &&
                        rgb[2] >= top[2] &&
                        rgb[2] <= bottom[2]) {
                        real[0] = j - 6;
                        real[1] = i + 55;
                        return real;
//                    }
                }
            }
        }
        for (int i : top) {
            top[i] += 1;
        }
        for (int i : bottom) {
            bottom[i] += 1;
        }
        return analyzingImages(bufferedImage, top, bottom);
    }



}
