package me.t3sl4.hydraulic.Screens.Controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import me.t3sl4.hydraulic.Launcher;
import me.t3sl4.hydraulic.Screens.Component.FilterSwitch;
import me.t3sl4.hydraulic.Screens.Controllers.Calculation.PopupController;
import me.t3sl4.hydraulic.Screens.SceneUtil;
import me.t3sl4.hydraulic.Utility.Data.HydraulicUnit.HydraulicInfo;
import me.t3sl4.hydraulic.Utility.Data.User.Profile;
import me.t3sl4.hydraulic.Utility.File.ExcelUtil;
import me.t3sl4.hydraulic.Utility.HTTP.HTTPRequest;
import me.t3sl4.hydraulic.Utility.SystemDefaults;
import me.t3sl4.hydraulic.Utility.Utils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.t3sl4.hydraulic.Launcher.*;
import static me.t3sl4.hydraulic.Screens.Main.loggedInUser;
import static me.t3sl4.hydraulic.Utility.Utils.openURL;

public class MainController implements Initializable {

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnHome;

    @FXML
    private Button btnKlasik;

    @FXML
    private Button btnHidros;

    @FXML
    private Button btnInisMetodu;

    @FXML
    private Button btnParametreler;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    @FXML
    private Label kullaniciAdiIsimText;
    @FXML
    private ImageView kullaniciProfilFoto;

    @FXML
    private Circle profilePhotoCircle;

    @FXML
    private Label toplamSiparisCount;
    @FXML
    private Label klasikUniteCount;
    @FXML
    private Label hidrosUntiteCount;
    @FXML
    private Label parametreCount;

    private List<HydraulicInfo> cachedHydraulicInfos = new ArrayList<>();
    int siparisSayisi;
    int klasik;
    int hidros;

    private final BooleanProperty isKlasik = new SimpleBooleanProperty();
    private final BooleanProperty isHidros = new SimpleBooleanProperty();
    private FilterSwitch klasikSwitch;
    private FilterSwitch hidrosSwitch;
    @FXML
    private VBox klasikSwitchVBox;
    @FXML
    private VBox hidrosSwitchVBox;

    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInfo();
        updateHydraulicText();
        hydraulicUnitInit(1);
        initializeSwitchs();
    }

    private void initializeSwitchs() {
        klasikSwitch = new FilterSwitch();
        hidrosSwitch = new FilterSwitch();

        klasikSwitchVBox.getChildren().addAll(klasikSwitch);
        hidrosSwitchVBox.getChildren().addAll(hidrosSwitch);

        klasikSwitch.isToggledProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if(!isHidros.getValue()) {
                    hydraulicUnitInit(2);
                } else {
                    hidrosSwitch.setIsToggled(false);
                    hydraulicUnitInit(2);
                }
            } else {
                hydraulicUnitInit(1);
            }
        });

        hidrosSwitch.isToggledProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if(!isKlasik.getValue()) {
                    hydraulicUnitInit(3);
                } else {
                    klasikSwitch.setIsToggled(false);
                    hydraulicUnitInit(3);
                }
            } else {
                hydraulicUnitInit(1);
            }
        });

        klasikSwitch.isToggledProperty().bindBidirectional(isKlasik);
        hidrosSwitch.isToggledProperty().bindBidirectional(isHidros);
    }

    @FXML
    public void anaEkranaDon() throws IOException {
        Stage stage = (Stage) parametreCount.getScene().getWindow();

        if(loggedInUser != null) {
            loggedInUser = null;
        }

        stage.close();
        SceneUtil.changeScreen("fxml/Login.fxml");
    }

    @FXML
    public void onderGrupSiteOpen() {
        Utils.openURL(SystemDefaults.WEB_URL);
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnHidros) {
            paneSwitch(2);
        }
        if (actionEvent.getSource() == btnInisMetodu) {
            paneSwitch(3);
        }
        if (actionEvent.getSource() == btnParametreler) {
            paneSwitch(4);
        }
        if (actionEvent.getSource() == btnHome) {
           if(!SystemDefaults.offlineMode) {
               pnlOverview.setStyle("-fx-background-color : #02030A");
               pnlOverview.toFront();
               updateHydraulicText();
               hydraulicUnitInit(1);
           } else {
               btnKlasik.fire();
           }
        }
        if(actionEvent.getSource()==btnKlasik) {
            paneSwitch(1);
        }
    }

    @FXML
    public void profileEditScreen() {
        if(loggedInUser != null) {
            pnlMenus.setStyle("-fx-background-color : #353a46");
            pnlMenus.toFront();
            try {
                FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("fxml/ProfileEdit.fxml"));
                Pane profilPane = loader.load();
                pnlMenus.getChildren().setAll(profilPane);

                double paneWidth = pnlMenus.getWidth();
                double paneHeight = pnlMenus.getHeight();

                double parametreWidth = profilPane.getPrefWidth();
                double parametreHeight = profilPane.getPrefHeight();

                double centerX = (paneWidth - parametreWidth) / 2;
                double centerY = (paneHeight - parametreHeight) / 2;
                profilPane.setLayoutX(centerX);
                profilPane.setLayoutY(centerY+20);
            } catch(IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    @FXML
    public void programiKapat() {
        System.exit(0);
    }

    public void userInfo() {
        if(loggedInUser != null) {
            kullaniciAdiIsimText.setText(loggedInUser.getUsername() + "\n" + loggedInUser.getFullName() + "\n" + loggedInUser.getCompanyName() + "\n ");
            Profile.downloadAndSetProfilePhoto(loggedInUser.getUsername(), profilePhotoCircle, kullaniciProfilFoto);
        }
    }

    public void updateHydraulicText() {
        hidrolikUnitStats(() -> {
            toplamSiparisCount.setText(String.valueOf(siparisSayisi));
            klasikUniteCount.setText(String.valueOf(klasik));
            hidrosUntiteCount.setText(String.valueOf(hidros));
        });
        excelVoidCount();
    }

    public void hidrolikUnitStats(Runnable updateHydraulicText) {
        String profileInfoUrl = BASE_URL + hydraulicGetStatsURLPrefix;

        HTTPRequest.sendRequestNormal(profileInfoUrl, new HTTPRequest.RequestCallback() {
            @Override
            public void onSuccess(String hydraulicResponse) {
                JSONObject responseJson = new JSONObject(hydraulicResponse);
                siparisSayisi = responseJson.getInt("Sipariş Sayısı");
                klasik = responseJson.getInt("Klasik");
                hidros = responseJson.getInt("Hidros");
                updateHydraulicText.run();
            }

            @Override
            public void onFailure() {
                System.out.println("Kullanıcı bilgileri alınamadı!");
            }
        });
    }

    public void hydraulicUnitInit(int type) {
        populateUIWithCachedData();

        if(type == 1) {
            //Tümü
            HTTPRequest.sendRequestNormal(BASE_URL + hydraulicGetInfoURLPrefix, new HTTPRequest.RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    cachedHydraulicInfos = parseJsonResponse(response);
                    populateUIWithCachedData();
                }

                @Override
                public void onFailure() {
                    System.out.println("API request failed.");
                }
            });
        } else if(type == 2) {
            //Klasik
            String reqURL = BASE_URL + hydraulicGetCustomInfoURLPrefix;
            String jsonHydraulicBody = "{\"UnitType\": \"" + "Klasik" + "\"}";
            HTTPRequest.sendRequest(reqURL, jsonHydraulicBody, new HTTPRequest.RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    cachedHydraulicInfos = parseJsonResponse(response);
                    populateUIWithCachedData();
                }

                @Override
                public void onFailure() {
                    System.out.println("API request failed.");
                }
            });
        } else if(type == 3) {
            //Hidros
            String reqURL = BASE_URL + hydraulicGetCustomInfoURLPrefix;
            String jsonHydraulicBody = "{\"UnitType\": \"" + "Hidros" + "\"}";
            HTTPRequest.sendRequest(reqURL, jsonHydraulicBody, new HTTPRequest.RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    cachedHydraulicInfos = parseJsonResponse(response);
                    populateUIWithCachedData();
                }

                @Override
                public void onFailure() {
                    System.out.println("API request failed.");
                }
            });
        }
    }

    private void populateUIWithCachedData() {
        pnItems.getChildren().clear();

        for (HydraulicInfo info : cachedHydraulicInfos) {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Launcher.class.getResource("fxml/Item.fxml")));
                Node node = loader.load();

                HBox itemC = (HBox) loader.getNamespace().get("itemC");
                Label orderNumberLabel = (Label) loader.getNamespace().get("orderNumberLabel");
                Label orderDateLabel = (Label) loader.getNamespace().get("orderDateLabel");
                Label typeLabel = (Label) loader.getNamespace().get("typeLabel");
                Label InChargeLabel = (Label) loader.getNamespace().get("InChargeLabel");
                Button pdfViewButton = (Button) loader.getNamespace().get("pdfViewButton");
                ImageView excelViewButton = (ImageView) loader.getNamespace().get("excelPart");

                orderNumberLabel.setText(info.getSiparisNumarasi());
                orderDateLabel.setText(formatDateTime(info.getSiparisTarihi()));
                typeLabel.setText(info.getUniteTipi());
                InChargeLabel.setText(info.getUserName());

                pdfViewButton.setOnAction(event -> openURL(BASE_URL + fileViewURLPrefix + info.getSiparisNumarasi() + ".pdf"));

                excelViewButton.setOnMouseClicked(event -> openURL(BASE_URL + fileViewURLPrefix + info.getSiparisNumarasi() + ".xlsx"));

                node.setOnMouseEntered(event -> itemC.setStyle("-fx-background-color : #0A0E3F"));
                node.setOnMouseExited(event -> itemC.setStyle("-fx-background-color : #02030A"));

                pnItems.getChildren().add(node);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public String formatDateTime(String dateTimeString) {
        try {
            OffsetDateTime dateTime = OffsetDateTime.parse(dateTimeString);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return dateTime.format(formatter);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return "";
        }
    }

    public List<HydraulicInfo> parseJsonResponse(String response) {
        List<HydraulicInfo> hydraulicInfos = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            HydraulicInfo[] infoArray = objectMapper.readValue(response, HydraulicInfo[].class);
            hydraulicInfos.addAll(Arrays.asList(infoArray));
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return hydraulicInfos;
    }

    private void excelVoidCount() {
        String excelPath = "/assets/data/Hidrolik.xlsx";
        String sheetName = "Boşluk Değerleri";

        try (InputStream file = Launcher.class.getResourceAsStream(excelPath)) {
            assert file != null;
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet != null) {
                int rowCount = sheet.getPhysicalNumberOfRows();
                int maxColumnCount = 0;

                for (int i = 0; i < rowCount; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        int columnCount = row.getPhysicalNumberOfCells();
                        if (columnCount > maxColumnCount) maxColumnCount = columnCount;
                    }
                }

                parametreCount.setText(String.valueOf(maxColumnCount));
            } else {
                System.out.println("Sayfa bulunamadı: " + sheetName);
            }

            workbook.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void paneSwitch(int state) {
        if(state == 1) {
            pnlMenus.setStyle("-fx-background-color : #353a46");
            pnlMenus.toFront();
            try {
                FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("fxml/Klasik.fxml"));
                Pane parametrePane = loader.load();
                pnlMenus.getChildren().setAll(parametrePane);

                double paneWidth = pnlMenus.getWidth();
                double paneHeight = pnlMenus.getHeight();

                double parametreWidth = parametrePane.getPrefWidth();
                double parametreHeight = parametrePane.getPrefHeight();

                double centerX = (paneWidth - parametreWidth) / 2;
                double centerY = (paneHeight - parametreHeight) / 2;
                parametrePane.setLayoutX(centerX-100);
                parametrePane.setLayoutY(centerY+20);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } else if(state == 2) {
            pnlMenus.setStyle("-fx-background-color : #353a46");
            pnlMenus.toFront();
            try {
                FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("fxml/Hidros.fxml"));
                Pane parametrePane = loader.load();
                pnlMenus.getChildren().setAll(parametrePane);

                double paneWidth = pnlMenus.getWidth();
                double paneHeight = pnlMenus.getHeight();

                double parametreWidth = parametrePane.getPrefWidth();
                double parametreHeight = parametrePane.getPrefHeight();

                double centerX = (paneWidth - parametreWidth) / 2;
                double centerY = (paneHeight - parametreHeight) / 2;
                parametrePane.setLayoutX(centerX-100);
                parametrePane.setLayoutY(centerY+20);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } else if(state == 3) {
            pnlMenus.setStyle("-fx-background-color : #353a46");
            pnlMenus.toFront();
            try {
                FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("fxml/Hidros.fxml"));
                Pane parametrePane = loader.load();
                pnlMenus.getChildren().setAll(parametrePane);

                double paneWidth = pnlMenus.getWidth();
                double paneHeight = pnlMenus.getHeight();

                double parametreWidth = parametrePane.getPrefWidth();
                double parametreHeight = parametrePane.getPrefHeight();

                double centerX = (paneWidth - parametreWidth) / 2;
                double centerY = (paneHeight - parametreHeight) / 2;
                parametrePane.setLayoutX(centerX-100);
                parametrePane.setLayoutY(centerY+20);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } else if(state == 4) {
            pnlMenus.setStyle("-fx-background-color : #02030A");
            pnlMenus.toFront();
            try {
                FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("fxml/Parametre.fxml"));
                Pane parametrePane = loader.load();
                pnlMenus.getChildren().setAll(parametrePane);

                double paneWidth = pnlMenus.getWidth();
                double paneHeight = pnlMenus.getHeight();

                double parametreWidth = parametrePane.getPrefWidth();
                double parametreHeight = parametrePane.getPrefHeight();

                double centerX = (paneWidth - parametreWidth) / 2;
                double centerY = (paneHeight - parametreHeight) / 2;
                parametrePane.setLayoutX(centerX);
                parametrePane.setLayoutY(centerY);

                PopupController popupController = loader.getController();
                popupController.setValues(ExcelUtil.dataManipulator.kampanaBoslukX, ExcelUtil.dataManipulator.kampanaBoslukY,
                        ExcelUtil.dataManipulator.valfBoslukX, ExcelUtil.dataManipulator.valfBoslukYArka, ExcelUtil.dataManipulator.valfBoslukYOn,
                        ExcelUtil.dataManipulator.kilitliBlokAraBoslukX, ExcelUtil.dataManipulator.tekHizAraBoslukX, ExcelUtil.dataManipulator.ciftHizAraBoslukX,
                        ExcelUtil.dataManipulator.kompanzasyonTekHizAraBoslukX, ExcelUtil.dataManipulator.sogutmaAraBoslukX, ExcelUtil.dataManipulator.sogutmaAraBoslukYkOn,
                        ExcelUtil.dataManipulator.sogutmaAraBoslukYkArka, ExcelUtil.dataManipulator.kilitMotorKampanaBosluk, ExcelUtil.dataManipulator.kilitMotorMotorBoslukX,
                        ExcelUtil.dataManipulator.kilitMotorBoslukYOn, ExcelUtil.dataManipulator.kilitMotorBoslukYArka, ExcelUtil.dataManipulator.kayipLitre, ExcelUtil.dataManipulator.kilitPlatformMotorBosluk, ExcelUtil.dataManipulator.valfXBoslukSogutma);
                popupController.showValues();
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}