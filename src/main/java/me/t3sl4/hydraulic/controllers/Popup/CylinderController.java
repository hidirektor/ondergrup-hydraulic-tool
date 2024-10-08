package me.t3sl4.hydraulic.controllers.Popup;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import me.t3sl4.hydraulic.utils.Utils;

public class CylinderController {

    @FXML
    private ComboBox<String> silindirSayisiComboBox;

    @FXML
    private ComboBox<String> basincSalteriComboBox;

    private int selectedCylinders = -1;
    private String pressureValfStatus = "";
    private boolean isConfirmed = false;

    @FXML
    public void initialize() {
        silindirSayisiComboBox.getItems().addAll(
                "1 Silindir",
                "2 Silindir",
                "3 Silindir",
                "4 Silindir"
        );
        basincSalteriComboBox.getItems().addAll(
                "Var",
                "Yok"
        );
    }

    @FXML
    public void popupKapat() {
        Stage stage = (Stage) silindirSayisiComboBox.getScene().getWindow();

        stage.close();
    }

    @FXML
    public void handleOkButton() {
        String selected = silindirSayisiComboBox.getSelectionModel().getSelectedItem();
        pressureValfStatus = basincSalteriComboBox.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selectedCylinders = Integer.parseInt(selected.split(" ")[0]);
                isConfirmed = true;
                closePopup();
            } catch (NumberFormatException e) {
                Utils.showErrorMessage("Geçerli bir silindir sayısı seçin.");
            }
        } else {
            Utils.showErrorMessage("Lütfen bir silindir sayısı seçin.");
        }
    }

    private void closePopup() {
        Stage stage = (Stage) silindirSayisiComboBox.getScene().getWindow();
        stage.close();
    }

    public int getSelectedCylinders() {
        return selectedCylinders;
    }

    public String getPressureValfStatus() {
        return pressureValfStatus;
    }

    public String getFinalResult() {
        return selectedCylinders + pressureValfStatus;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}