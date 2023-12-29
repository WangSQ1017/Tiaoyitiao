package com.gj7;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.json.JSONUtil;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GlobalKeyListener implements NativeKeyListener {
    private ShowTransparentWindows jFrame;
    private static List<Integer> colorList = new ArrayList<>();

    public GlobalKeyListener(ShowTransparentWindows jFrame) {
        this.jFrame = jFrame;
    }

    static {
        String str = IoUtil.readUtf8(new ClassPathResource("color.json").getStream());
        colorList = JSONUtil.toList(str, Integer.class);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_F1) {
            // RGB 61 50 88
            BufferedImage bufferedImage = jFrame.screenPic();
            try {
                analyzingImages(bufferedImage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\DELL\\Desktop\\新建文件夹\\1703822482279.png");
        BufferedImage image=(BufferedImage)ImageIO.read(file);
        analyzingImages(image);
    }

    public static void analyzingImages(BufferedImage bufferedImage) throws IOException {

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int minx = bufferedImage.getMinX();
        int miny = bufferedImage.getMinY();
        System.out.println("width=" + width + ",height=" + height + ".");
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");
        int[] rgb = new int[3];
        int[] tempXY = new int[4];
        int[] realXY = new int[4];
        int flag = 0;
        boolean lastLine = false;
        for (int i = minx; i < height; i++) {
            int count = 0;
            for (int j = miny; j < width; j++) {
                int pixel = bufferedImage.getRGB(j, i);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                String s = rgb[0] + "" + rgb[1] + "" + rgb[2];
                if (colorList.contains(Integer.parseInt(s))){

                } else {


                }

            }
        }
        bufferedImage.setRGB((tempXY[0] + tempXY[2]) / 2, (tempXY[1] + tempXY[3]) / 2, new Color(255,0,0).getRGB());
        bufferedImage.setRGB(tempXY[0], tempXY[1], new Color(255,0,0).getRGB());
        bufferedImage.setRGB(tempXY[2], tempXY[3], new Color(255,0,0).getRGB());
        File file = new File("C:\\Users\\DELL\\Desktop\\新建文件夹\\" + System.currentTimeMillis() + ".png");
        ImageIO.write(bufferedImage, "png", file);
    }

}