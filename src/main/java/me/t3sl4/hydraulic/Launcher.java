package me.t3sl4.hydraulic;

import me.t3sl4.hydraulic.app.Main;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        System.setProperty("prism.allowhidpi", "false");

        if(System.getProperty("os.name").contains("Windows")) {
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.verbose", "true");
            System.setProperty("javafx.animation.fullspeed", "true");
        } else {
            System.setProperty("CG_PDF_VERBOSE", "1");
            System.setProperty("apple.awt.UIElement", "true");
        }
        System.setProperty("java.util.logging.level", "WARNING");

        Main.main(args);
    }
}
