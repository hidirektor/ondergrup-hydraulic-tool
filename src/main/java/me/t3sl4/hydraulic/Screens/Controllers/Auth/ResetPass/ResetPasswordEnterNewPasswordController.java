package me.t3sl4.hydraulic.Screens.Controllers.Auth.ResetPass;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import me.t3sl4.hydraulic.Launcher;
import me.t3sl4.hydraulic.Screens.SceneUtil;
import me.t3sl4.hydraulic.Utility.HTTP.HTTPRequest;
import me.t3sl4.hydraulic.Utility.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static me.t3sl4.hydraulic.Launcher.BASE_URL;
import static me.t3sl4.hydraulic.Launcher.updatePassURLPrefix;

public class ResetPasswordEnterNewPasswordController implements Initializable {

    @FXML
    private Button btnChangePass;

    @FXML
    private TextField sifreText;
    @FXML
    private TextField sifrePassword;
    @FXML
    private ImageView passwordVisibilityIcon;
    @FXML
    private Button togglePasswordButton;

    @FXML
    private Label lblErrors;

    private String girilenSifre = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        togglePasswordButton.setOnMouseClicked(event -> togglePasswordVisibility());
        sifreText.textProperty().addListener((observable, oldValue, newValue) -> {
            girilenSifre = newValue;
        });
    }

    @FXML
    public void programiKapat(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    public void goBackAction() throws IOException {
        Stage stage = (Stage) btnChangePass.getScene().getWindow();

        stage.close();
        SceneUtil.changeScreen("fxml/Login.fxml");
    }

    @FXML
    public void onderWeb() {
        Utils.openURL("https://ondergrup.com");
    }

    @FXML
    public void sifreDegistir() {
        String yeniSifre = sifreText.getText();

        if (yeniSifre.isEmpty()) {
            Utils.showErrorOnLabel(lblErrors, "E-posta adresi boş olamaz.");
        } else {
            String otpUrl = BASE_URL + updatePassURLPrefix;
            String jsonUpdatePassBody = "{\"userName\": \"" + ResetPasswordController.enteredUserName + "\", \"newPassword\": \"" + yeniSifre + "\"}";

            System.out.println(otpUrl + jsonUpdatePassBody);

            HTTPRequest.sendJsonRequest(otpUrl, "POST", jsonUpdatePassBody, new HTTPRequest.RequestCallback() {
                @Override
                public void onSuccess(String changeResponse) throws IOException {
                    backMainScreen();
                }

                @Override
                public void onFailure() {
                    Utils.showErrorOnLabel(lblErrors, "Eski şifre ile yeni şifre aynı olamaz !");
                }
            });
        }
    }

    private void togglePasswordVisibility() {
        if (sifreText.isVisible()) {
            sifreText.setManaged(false);
            sifreText.setVisible(false);
            sifrePassword.setManaged(true);
            sifrePassword.setVisible(true);
            sifrePassword.setText(girilenSifre);
            passwordVisibilityIcon.setImage(new Image(Launcher.class.getResourceAsStream("icons/hidePass.png")));
        } else {
            sifreText.setManaged(true);
            sifreText.setVisible(true);
            sifrePassword.setManaged(false);
            sifrePassword.setVisible(false);
            passwordVisibilityIcon.setImage(new Image(Launcher.class.getResourceAsStream("icons/showPass.png")));
        }
    }

    private void backMainScreen() throws IOException {
        Stage stage = (Stage) btnChangePass.getScene().getWindow();

        stage.close();
        SceneUtil.changeScreen("fxml/Login.fxml");
    }
}
