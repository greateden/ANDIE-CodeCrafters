package cosc202.andie;

import javax.swing.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;

public class ThemeConfig {
    public static LookAndFeel CreateTheme() {
        try {
            String followSysTheme = Files.readAllLines(Paths.get("src/cosc202/andie/Config.config"))
                    .get(1/* 1st line */);
            if (followSysTheme.compareTo("1") == 0) {

                if (whatsTheOSTheme() == 0) {
                    // light theme
                    return new FlatLightLaf();
                } else {
                    // dark theme
                    return new FlatDarkLaf();
                }

            } else {
                String customTheme = Files.readAllLines(Paths.get("src/cosc202/andie/Config.config")).get(3/*
                                                                                                            * 1st line
                                                                                                            */);
                if (customTheme.compareTo("FlatLightLaf") == 0) {
                    FlatLightLaf.setup();
                    return new FlatLightLaf();
                    // try {
                    // UIManager.setLookAndFeel(new FlatLightLaf());
                    // } catch (Exception ex) {
                    // System.err.println("Failed to initialize LaF");
                    // }
                } else if (customTheme.compareTo("FlatDarkLaf") == 0) {
                    FlatDarkLaf.setup();
                    return new FlatDarkLaf();
                    // try {
                    // UIManager.setLookAndFeel(new FlatDarkLaf());
                    // } catch (Exception ex) {
                    // System.err.println("Failed to initialize LaF");
                    // }
                } else if (customTheme.compareTo("FlatIntelliJLaf") == 0) {
                    FlatIntelliJLaf.setup();
                    return new FlatIntelliJLaf();
                    // try {
                    // UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    // } catch (Exception ex) {
                    // System.err.println("Failed to initialize LaF");
                    // }
                } else if (customTheme.compareTo("FlatDarculaLaf") == 0) {
                    FlatDarculaLaf.setup();
                    return new FlatDarculaLaf();
                    // try {
                    // UIManager.setLookAndFeel(new FlatDarculaLaf());
                    // } catch (Exception ex) {
                    // System.err.println("Failed to initialize LaF");
                    // }
                } else {
                    return new FlatLightLaf();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return new FlatLightLaf();
    }

    public static void SetTheme() {

    }

    // public static int getOSType() {
    // String osName = System.getProperty("os.name").toLowerCase();
    // if (osName.contains("win")) {
    // // Running on Windows
    // return 1;// stands for windows
    // } else if (osName.contains("mac")) {
    // // Running on Mac
    // return 0;// stands for mac
    // } else {
    // // Running on another operating system
    // return -1;// stands for other os
    // }

    // }

    /*
     * return value 0 = light theme
     * 1 = dark theme
     */
    public static int whatsTheOSTheme() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder;

            if (os.contains("win")) {
                // Command for Windows
                processBuilder = new ProcessBuilder("powershell", "-Command",
                        "Get-ItemProperty -Path HKCU:\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize | Select-Object AppsUseLightTheme");
            } else if (os.contains("mac")) {
                // Command for MacOS
                processBuilder = new ProcessBuilder("/bin/bash", "-c", "defaults read -g AppleInterfaceStyle");
            } else {
                return 0;// sorry Linux, cry myself to sleep...
            }

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int theme = 0; // Default to light theme

            while ((line = reader.readLine()) != null) {
                if (os.contains("win")) {
                    theme = line.contains("0") ? 1 : 0; // 0 for dark theme in Windows
                } else if (os.contains("mac")) {
                    theme = line.contains("Dark") ? 1 : 0; // Dark for dark theme in MacOS
                }
            }

            System.out.println("Current theme: " + (theme == 1 ? "Dark" : "Light"));
            return theme;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}