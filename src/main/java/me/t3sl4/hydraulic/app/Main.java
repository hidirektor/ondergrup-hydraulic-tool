package me.t3sl4.hydraulic.app;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import me.t3sl4.hydraulic.utils.Utils;
import me.t3sl4.hydraulic.utils.database.File.FileUtil;
import me.t3sl4.hydraulic.utils.database.Model.Replay.ClassicData;
import me.t3sl4.hydraulic.utils.database.Model.Replay.PowerPackData;
import me.t3sl4.hydraulic.utils.general.SceneUtil;
import me.t3sl4.hydraulic.utils.general.SystemVariables;

import java.util.List;
import java.util.prefs.Preferences;

public class Main extends Application {
    List<Screen> screens = Screen.getScreens();

    public static Screen defaultScreen;

    public static String license;

    public static PowerPackData powerPackReplayData = new PowerPackData();
    public static ClassicData classicReplayData = new ClassicData();

    @lombok.SneakyThrows
    @Override
    public void start(Stage primaryStage) {
        Utils.prefs = Preferences.userRoot().node(this.getClass().getName());
        String defaultMonitor = Utils.checkDefaultMonitor();
        FileUtil.criticalFileSystem();

        checkVersionFromPrefs();

        defaultScreen = screens.get(0);

        if (defaultMonitor == null) {
            if (screens.size() > 1) {
                Utils.showMonitorSelectionScreen(screens, null, true);
            } else {
                SceneUtil.openMainScreen(screens.get(0));
            }
        } else {
            if(screens.size() > 1) {
                int monitorIndex = Integer.parseInt(defaultMonitor.split(" ")[1]) - 1;
                defaultScreen = screens.get(monitorIndex);
                SceneUtil.openMainScreen(screens.get(monitorIndex));
            } else {
                SceneUtil.openMainScreen(screens.get(0));
            }
        }

        Thread systemThread = new Thread(FileUtil::setupLocalData);
        systemThread.start();
    }

    private void checkVersionFromPrefs() {
        // Sabit düğüm adı kullanarak Preferences oluşturuluyor
        Utils.prefs = Preferences.userRoot().node("onderGrupUpdater");

        // Launcher için key
        String launcherVersionKey = "launcher_version";
        // HydraulicTool için key
        String hydraulicVersionKey = "hydraulic_version";

        // Mevcut sürüm (Launcher için)
        String currentVersion = SystemVariables.CURRENT_VERSION;

        // Kaydedilmiş sürümleri oku
        String savedLauncherVersion = Utils.prefs.get(launcherVersionKey, null);
        String savedHydraulicVersion = Utils.prefs.get(hydraulicVersionKey, "unknown");

        // Launcher sürümünü kontrol et ve güncelle
        if (savedHydraulicVersion == null || !savedHydraulicVersion.equals(currentVersion)) {
            Utils.prefs.put(hydraulicVersionKey, currentVersion);
            savedHydraulicVersion = Utils.prefs.get(hydraulicVersionKey, "unknown");
        }

        // HydraulicTool sürümünü logla
        System.out.println("HydraulicTool sürümü: " + savedHydraulicVersion);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
