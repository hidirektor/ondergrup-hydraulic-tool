module me.t3sl.hydraulic {
    requires javafx.controls;
    requires javafx.fxml;

    requires itext.xtra;
    requires itextpdf;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.json;
    requires com.google.gson;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.codec;
    requires javafx.web;
    requires log4j;
    requires annotations;
    requires org.yaml.snakeyaml;
    requires java.desktop;
    requires java.logging;
    requires static lombok;

    opens me.t3sl4.hydraulic to javafx.fxml;
    exports me.t3sl4.hydraulic;
    exports me.t3sl4.hydraulic.Screens.Controllers;
    exports me.t3sl4.hydraulic.Utils;
    opens me.t3sl4.hydraulic.Screens.Controllers to javafx.fxml;
    exports me.t3sl4.hydraulic.Utils.API;
    opens me.t3sl4.hydraulic.Utils.API to javafx.base;
    exports me.t3sl4.hydraulic.Utils.Database.Model.HydraulicData;
    opens me.t3sl4.hydraulic.Utils.Database.Model.HydraulicData to javafx.base;
    exports me.t3sl4.hydraulic.Utils.System.UserDataService;
    opens me.t3sl4.hydraulic.Utils.System.UserDataService to javafx.base;
    exports me.t3sl4.hydraulic.Utils.Database.Model.Table.HydraulicUnitList;
    opens me.t3sl4.hydraulic.Utils.Database.Model.Table.HydraulicUnitList to javafx.base;
    exports me.t3sl4.hydraulic.Screens;
    opens me.t3sl4.hydraulic.Screens to javafx.base, javafx.fxml;
    exports me.t3sl4.hydraulic.Screens.Controllers.Calculation;
    opens me.t3sl4.hydraulic.Screens.Controllers.Calculation to javafx.fxml;
    exports me.t3sl4.hydraulic.Screens.Controllers.Calculation.Hidros;
    opens me.t3sl4.hydraulic.Screens.Controllers.Calculation.Hidros to javafx.fxml;
    exports me.t3sl4.hydraulic.Screens.Controllers.Calculation.Klasik;
    opens me.t3sl4.hydraulic.Screens.Controllers.Calculation.Klasik to javafx.fxml;
    exports me.t3sl4.hydraulic.Screens.Controllers.Auth;
    opens me.t3sl4.hydraulic.Screens.Controllers.Auth to javafx.fxml;
    exports me.t3sl4.hydraulic.Screens.Controllers.Auth.ResetPass;
    opens me.t3sl4.hydraulic.Screens.Controllers.Auth.ResetPass to javafx.fxml;
    exports me.t3sl4.hydraulic.Screens.Controllers.User;
    opens me.t3sl4.hydraulic.Screens.Controllers.User to javafx.fxml;
    opens me.t3sl4.hydraulic.Utils to javafx.base, javafx.fxml;
    exports me.t3sl4.hydraulic.Utils.System;
    opens me.t3sl4.hydraulic.Utils.System to javafx.base, javafx.fxml;
    exports me.t3sl4.hydraulic.Utils.General;
    opens me.t3sl4.hydraulic.Utils.General to javafx.base, javafx.fxml;
    exports me.t3sl4.hydraulic.Utils.Database.Model.Table.PartList;
    opens me.t3sl4.hydraulic.Utils.Database.Model.Table.PartList to javafx.base;
}