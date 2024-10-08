package me.t3sl4.hydraulic.controllers.Calculation.Klasik.PartList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import me.t3sl4.hydraulic.controllers.Calculation.Klasik.KlasikController;
import me.t3sl4.hydraulic.utils.Utils;
import me.t3sl4.hydraulic.utils.database.Model.Kabin.Kabin;
import me.t3sl4.hydraulic.utils.database.Model.Table.PartList.ParcaTableData;
import me.t3sl4.hydraulic.utils.general.SystemVariables;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Objects;

public class KlasikParcaController {
    @FXML
    private ComboBox<String> basincSalteriComboBox;

    @FXML
    private ComboBox<String> manometreComboBox;

    @FXML
    private ComboBox<String> elPompasiComboBox;

    @FXML
    private TableView<ParcaTableData> parcaListesiTablo;

    @FXML
    private TableColumn<ParcaTableData, String> malzemeKodu;

    @FXML
    private TableColumn<ParcaTableData, String> secilenMalzeme;

    @FXML
    private TableColumn<ParcaTableData, String> adet;

    private String basincSalteriDurumu = null;
    private String manometreDurumu = null;
    private String elPompasiDurumu = null;

    public void initialize() {
        if(KlasikController.secilenSogutmaDurumu.equals("Var")) {
            manometreComboBox.setDisable(true);
            basincSalteriComboBox.setDisable(false);
            basincSalteriComboBox.getItems().clear();
            basincSalteriComboBox.getItems().addAll("Var", "Yok");
        } else {
            manometreComboBox.setDisable(false);
            manometreComboBox.getItems().clear();
            manometreComboBox.getItems().addAll("Var", "Yok");
        }

        malzemeKodu.setCellValueFactory(new PropertyValueFactory<>("satir1Property"));
        secilenMalzeme.setCellValueFactory(new PropertyValueFactory<>("satir2Property"));
        adet.setCellValueFactory(new PropertyValueFactory<>("satir3Property"));

        parcaListesiTablo.getItems().clear();
        comboBoxListener();
    }

    @FXML
    public void panoyaKopyala() {
        StringBuilder clipboardString = new StringBuilder();

        ObservableList<ParcaTableData> veriler = parcaListesiTablo.getItems();

        for (ParcaTableData veri : veriler) {
            clipboardString.append(veri.getSatir1Property()).append(" ");
            clipboardString.append(veri.getSatir2Property()).append(" ");
            clipboardString.append(veri.getSatir3Property()).append("\n");
        }

        ClipboardContent content = new ClipboardContent();
        content.putString(clipboardString.toString());
        Clipboard.getSystemClipboard().setContent(content);
    }

    @FXML
    public void exportExcelProcess() {
        ObservableList<ParcaTableData> veriler = parcaListesiTablo.getItems();
        String excelFileName = KlasikController.girilenSiparisNumarasi + ".xlsx";

        String desktopPath = Paths.get(System.getProperty("user.home"), "Desktop").toString();
        excelFileName = Paths.get(desktopPath, excelFileName).toString();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Malzeme Listesi");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Malzeme Kodu");
            headerRow.createCell(1).setCellValue("Seçilen Malzeme");
            headerRow.createCell(2).setCellValue("Adet");

            for (int i = 0; i < veriler.size(); i++) {
                ParcaTableData rowData = veriler.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(rowData.getSatir1Property());
                row.createCell(1).setCellValue(rowData.getSatir2Property());
                row.createCell(2).setCellValue(rowData.getSatir3Property());
            }

            try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
                workbook.write(fileOut);
                System.out.println("Excel dosyası başarıyla oluşturuldu: " + excelFileName);
            }

        } catch (IOException e) {
            System.err.println("Excel dosyası oluşturulurken bir hata oluştu: " + e.getMessage());
        }
    }

    @FXML
    public void popupKapat() {
        Stage stage = (Stage) basincSalteriComboBox.getScene().getWindow();

        stage.close();
    }

    @FXML
    public void manometrePressed() {
        basincSalteriComboBox.getItems().clear();
        basincSalteriComboBox.getItems().addAll("Var", "Yok");

        if(manometreComboBox.getValue() != null) {
            manometreDurumu = manometreComboBox.getValue();
        }
        basincSalteriComboBox.setDisable(false);
    }

    @FXML
    public void basincSalteriPressed() {
        elPompasiComboBox.getItems().clear();
        elPompasiComboBox.getItems().addAll("Var", "Yok");

        if(basincSalteriComboBox.getValue() != null) {
            basincSalteriDurumu = basincSalteriComboBox.getValue();
        }
        elPompasiComboBox.setDisable(false);
    }

    @FXML
    public void elPompasiPressed() {
        elPompasiDurumu = String.valueOf(elPompasiComboBox.getValue());
        tabloGuncelle();
    }

    private void comboBoxListener() {
        elPompasiComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            parcaListesiTablo.getItems().clear();
            tabloGuncelle();
            elPompasiDurumu = String.valueOf(newValue);
        });
    }

    private void tabloGuncelle() {
        loadMotorParca();
        loadKampanaParca();
        loadPompaParca();
        if(KlasikController.secilenSogutmaDurumu.equals("Yok")) {
            loadKabinKodu();
        }
        loadKaplinParca();
        loadValfBlokParca();
        if(basincSalteriDurumu.equals("Var")) {
            loadBasincSalteriParca();
        }
        if(KlasikController.secilenSogutmaDurumu.contains("Yok") && manometreDurumu.equals("Var")) {
            loadManometre();
        }

        if(elPompasiDurumu.equals("Var")) {
            loadElPompasiParca();
        }

        loadStandartParca();
        if(KlasikController.secilenSogutmaDurumu.contains("Var")) {
            loadSogutucuParca();
        }
        loadYagMiktari();
    }

    private void loadKabinKodu() {
        String malzemeKodu = null;
        String malzemeAdi = null;
        String adet = "1";

        Kabin foundedTank = Utils.findTankByKabinName(KlasikController.atananKabinFinal);
        malzemeKodu = foundedTank.getMalzemeKodu();
        malzemeAdi = foundedTank.getMalzemeAdi();

        ParcaTableData data = new ParcaTableData(malzemeKodu, malzemeAdi, adet);
        parcaListesiTablo.getItems().add(data);
    }

    private void loadKampanaParca() {
        if(KlasikController.secilenPompaVal >= 33.3) {
            if(KlasikController.secilenKampana == 250) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKampana.get("4"));
            } else if(KlasikController.secilenKampana == 300) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKampana.get("5"));
            } else if(KlasikController.secilenKampana == 350) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKampana.get("6"));
            } else if(KlasikController.secilenKampana == 400) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKampana.get("7"));
            }
        } else {
            if(KlasikController.secilenKampana == 250) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKampana.get("0"));
            } else if(KlasikController.secilenKampana == 300) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKampana.get("1"));
            } else if(KlasikController.secilenKampana == 350) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKampana.get("2"));
            } else if(KlasikController.secilenKampana == 400) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKampana.get("3"));
            }
        }
    }

    private void loadPompaParca() {
        if(Objects.equals(KlasikController.secilenPompa, "9.5 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("0"));
        } else if(Objects.equals(KlasikController.secilenPompa, "11.9 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("1"));
        } else if(Objects.equals(KlasikController.secilenPompa, "14 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("2"));
        } else if(Objects.equals(KlasikController.secilenPompa, "14.6 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("3"));
        } else if(Objects.equals(KlasikController.secilenPompa, "16.8 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("4"));
        } else if(Objects.equals(KlasikController.secilenPompa, "19.2 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("5"));
        } else if(Objects.equals(KlasikController.secilenPompa, "22.9 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("6"));
        } else if(Objects.equals(KlasikController.secilenPompa, "28.1 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("7"));
        } else if(Objects.equals(KlasikController.secilenPompa, "28.8 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("8"));
        } else if(Objects.equals(KlasikController.secilenPompa, "33.3 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("9"));
        } else if(Objects.equals(KlasikController.secilenPompa, "37.9 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("10"));
        } else if(Objects.equals(KlasikController.secilenPompa, "42.6 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("11"));
        } else if(Objects.equals(KlasikController.secilenPompa, "45.5 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("12"));
        } else if(Objects.equals(KlasikController.secilenPompa, "49.4 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("13"));
        } else if(Objects.equals(KlasikController.secilenPompa, "56.1 cc")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaPompa.get("14"));
        }
    }

    private void loadMotorParca() {
        if(Objects.equals(KlasikController.secilenMotor, "2.2 kW")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("0"));
        } else if(Objects.equals(KlasikController.secilenMotor, "3 kW")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("1"));
        } else if(Objects.equals(KlasikController.secilenMotor, "4 kW")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("2"));
        } else if(Objects.equals(KlasikController.secilenMotor, "5.5 kW")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("3"));
        } else if(Objects.equals(KlasikController.secilenMotor, "5.5 kW (Kompakt)")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("4"));
        } else if(Objects.equals(KlasikController.secilenMotor, "7.5 kW (Kompakt)")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("5"));
        } else if(Objects.equals(KlasikController.secilenMotor, "11 kW")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("6"));
        } else if(Objects.equals(KlasikController.secilenMotor, "11 kW (Kompakt)")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("7"));
        } else if(Objects.equals(KlasikController.secilenMotor, "15 kW")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("8"));
        } else if(Objects.equals(KlasikController.secilenMotor, "18.5 kW")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("9"));
        } else if(Objects.equals(KlasikController.secilenMotor, "22 kW")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("10"));
        } else if(Objects.equals(KlasikController.secilenMotor, "37 kW")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaMotor.get("11"));
        }
    }

    private void loadKaplinParca() {
        String[] secPmp = KlasikController.secilenPompa.split(" cc");
        float secilenPompaVal = Float.parseFloat(secPmp[0]);

        if(secilenPompaVal < 33.3) {
            if(Objects.equals(KlasikController.secilenMotor, "2.2 kW") || Objects.equals(KlasikController.secilenMotor, "3 kW") || Objects.equals(KlasikController.secilenMotor, "4 kW") || Objects.equals(KlasikController.secilenMotor, "5.5 kW") || Objects.equals(KlasikController.secilenMotor, "5.5 kW (Kompakt)")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKaplin.get("0"));
            } else if(Objects.equals(KlasikController.secilenMotor, "7.5 kW (Kompakt)") || Objects.equals(KlasikController.secilenMotor, "11 kW") || Objects.equals(KlasikController.secilenMotor, "11 kW (Kompakt)")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKaplin.get("1"));
            } else if(Objects.equals(KlasikController.secilenMotor, "15 kW") || Objects.equals(KlasikController.secilenMotor, "18.5 kW") || Objects.equals(KlasikController.secilenMotor, "22 kW") || Objects.equals(KlasikController.secilenMotor, "37 kW")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKaplin.get("2"));
            }
        } else {
            if(Objects.equals(KlasikController.secilenMotor, "2.2 kW") || Objects.equals(KlasikController.secilenMotor, "3 kW") || Objects.equals(KlasikController.secilenMotor, "4 kW") || Objects.equals(KlasikController.secilenMotor, "5.5 kW") || Objects.equals(KlasikController.secilenMotor, "5.5 kW (Kompakt)")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKaplin.get("3"));
            } else if(Objects.equals(KlasikController.secilenMotor, "7.5 kW (Kompakt)") || Objects.equals(KlasikController.secilenMotor, "11 kW") || Objects.equals(KlasikController.secilenMotor, "11 kW (Kompakt)")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKaplin.get("4"));
            } else if(Objects.equals(KlasikController.secilenMotor, "15 kW") || Objects.equals(KlasikController.secilenMotor, "18.5 kW") || Objects.equals(KlasikController.secilenMotor, "22 kW") || Objects.equals(KlasikController.secilenMotor, "37 kW")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaKaplin.get("5"));
            }
        }
    }

    private void loadValfBlokParca() {
        if(KlasikController.secilenPompaVal < 33.3) {
            //1 Grubu
            if(Objects.equals(KlasikController.secilenValfTipi, "İnişte Tek Hız")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaValfBloklari.get("0"));
            } else if(Objects.equals(KlasikController.secilenValfTipi, "İnişte Çift Hız")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaValfBloklari.get("1"));
            } else if(Objects.equals(KlasikController.secilenValfTipi, "Kilitli Blok")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaValfBloklari.get("2"));
            } else if(Objects.equals(KlasikController.secilenValfTipi, "Kompanzasyon || İnişte Tek Hız")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaValfBloklari.get("3"));
            }
        } else {
            //2 Grubu
            if(Objects.equals(KlasikController.secilenValfTipi, "İnişte Tek Hız")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaValfBloklari.get("4"));
            } else if(Objects.equals(KlasikController.secilenValfTipi, "İnişte Çift Hız")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaValfBloklari.get("5"));
            } else if(Objects.equals(KlasikController.secilenValfTipi, "Kilitli Blok")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaValfBloklari.get("6"));
            } else if(Objects.equals(KlasikController.secilenValfTipi, "Kompanzasyon || İnişte Tek Hız")) {
                generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaValfBloklari.get("7"));
            }
        }
    }

    private void loadBasincSalteriParca() {
        if(Objects.equals(basincSalteriDurumu, "Var")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaBasincSalteri.get("0"));
        }
    }

    private void loadElPompasiParca() {
        if(Objects.equals(elPompasiDurumu, "Var")) {
            String malzemeKodu = "150-51-10-086";
            String secilenMalzeme = "Oleocon Hidrolik El Pompası OHP Serisi 501-t";
            String adet = "1";

            ParcaTableData data = new ParcaTableData(malzemeKodu, secilenMalzeme, adet);
            parcaListesiTablo.getItems().add(data);
        }
    }

    private void loadStandartParca() {
        if(Objects.equals(KlasikController.atananHT, "HT 40")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("0"));
        } else if(Objects.equals(KlasikController.atananHT, "HT 70")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("1"));
        } else if(Objects.equals(KlasikController.atananHT, "HT 100")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("2"));
        } else if(Objects.equals(KlasikController.atananHT, "HT 125")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("3"));
        } else if(Objects.equals(KlasikController.atananHT, "HT 160")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("4"));
        } else if(Objects.equals(KlasikController.atananHT, "HT 200")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("5"));
        } else if(Objects.equals(KlasikController.atananHT, "HT 250")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("6"));
        } else if(Objects.equals(KlasikController.atananHT, "HT 300")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("7"));
        } else if(Objects.equals(KlasikController.atananHT, "HT 350")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("8"));
        } else if(Objects.equals(KlasikController.atananHT, "HT 400")) {
            generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaDefault.get("9"));
        }
    }

    private void loadSogutucuParca() {
        generalLoadFunc(SystemVariables.getLocalHydraulicData().classicParcaSogutma.get("0"));
    }

    private void loadManometre() {
        if(Objects.equals(manometreDurumu, "Var")) {
            String malzemeKodu = "150-51-10-802";
            String secilenMalzeme = "Manometre";
            String adet = "1";

            ParcaTableData data = new ParcaTableData(malzemeKodu, secilenMalzeme, adet);
            parcaListesiTablo.getItems().add(data);
        }
    }

    private void loadYagMiktari() {
        String malzemeKodu = "150-53-04-002";
        String malzemeAdi = "HİDROLİK YAĞ SHELL TELLUS S2 M46";
        String adet = KlasikController.girilenTankKapasitesiMiktari + " Lt";

        ParcaTableData data = new ParcaTableData(malzemeKodu, malzemeAdi, adet);
        parcaListesiTablo.getItems().add(data);
    }

    private void generalLoadFunc(LinkedList<String> parcaListesi) {
        for (String veri : parcaListesi) {
            String[] veriParcalari = veri.split(";");

            String malzemeKodu = veriParcalari[0];
            String secilenMalzeme = veriParcalari[1];
            String adet = veriParcalari[2];

            ParcaTableData data = new ParcaTableData(malzemeKodu, secilenMalzeme, adet);
            parcaListesiTablo.getItems().add(data);
        }
    }
}