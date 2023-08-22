package me.t3sl4.hydraulic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.t3sl4.hydraulic.Util.Gen.Util;
import me.t3sl4.hydraulic.Util.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main extends Application {
    private double x, y;
    public static User loggedInUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        fileCopy();
        Util.filePath();

        Util.excelDataRead();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Login.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        Image icon = new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("icons/logo.png")));
        primaryStage.getIcons().add(icon);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });
        primaryStage.show();
    }

    public void fileCopy() {
        String dataFileLocalPath = "C:/Users/" + System.getProperty("user.name") + "/OnderGrup/data/";
        String destPath = dataFileLocalPath + "Hidrolik.xlsx";
        Path targetPath = Paths.get(destPath);

        if (!Files.exists(targetPath)) {
            try (InputStream inputStream = getClass().getResourceAsStream("/data/Hidrolik.xlsx")) {
                if (inputStream != null) {
                    Files.copy(inputStream, targetPath);
                    System.out.println("Hidrolik.xlsx kopyalandı: " + destPath);
                } else {
                    System.err.println("Hidrolik.xlsx dosyası bulunamadı.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}