package cosc202.andie;

import javax.swing.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;

public class ThemeConfig {
    public static LookAndFeel CreateTheme() {
        try {
            String followSysTheme = Files.readAllLines(Paths.get("src/cosc202/andie/Config.config"))
                    .get(1/* 1st line */);
            if (followSysTheme.compareTo("1") == 0) {
                // TODO follow system
                return new FlatLightLaf();
            } else {
                String customTheme = Files.readAllLines(Paths.get("src/cosc202/andie/Config.config")).get(3/*
                                                                                                            * 1st line
                                                                                                            */);
                if (customTheme.compareTo("FlatLightLaf") == 0) {
                    FlatLightLaf.setup();
                    return new FlatLightLaf();
                    // try {
                    //     UIManager.setLookAndFeel(new FlatLightLaf());
                    // } catch (Exception ex) {
                    //     System.err.println("Failed to initialize LaF");
                    // }
                } else if (customTheme.compareTo("FlatDarkLaf") == 0) {
                    FlatDarkLaf.setup();
                    return new FlatDarkLaf();
                    // try {
                    //     UIManager.setLookAndFeel(new FlatDarkLaf());
                    // } catch (Exception ex) {
                    //     System.err.println("Failed to initialize LaF");
                    // }
                } else if (customTheme.compareTo("FlatIntelliJLaf") == 0) {
                    FlatIntelliJLaf.setup();
                    return new FlatIntelliJLaf();
                    // try {
                    //     UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    // } catch (Exception ex) {
                    //     System.err.println("Failed to initialize LaF");
                    // }
                } else if (customTheme.compareTo("FlatDarculaLaf") == 0) {
                    FlatDarculaLaf.setup();
                    return new FlatDarculaLaf();
                    // try {
                    //     UIManager.setLookAndFeel(new FlatDarculaLaf());
                    // } catch (Exception ex) {
                    //     System.err.println("Failed to initialize LaF");
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
}