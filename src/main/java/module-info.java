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
    requires java.prefs;
    requires java.net.http;
    requires org.apache.commons.io;
    requires javafx.media;

    exports me.t3sl4.hydraulic.utils;
    opens me.t3sl4.hydraulic.controllers to javafx.fxml;
    exports me.t3sl4.hydraulic.utils.database.Model.HydraulicData;
    opens me.t3sl4.hydraulic.utils.database.Model.HydraulicData to javafx.base;
    exports me.t3sl4.hydraulic.utils.service.UserDataService;
    opens me.t3sl4.hydraulic.utils.service.UserDataService to javafx.base;
    exports me.t3sl4.hydraulic.utils.database.Model.Table.HydraulicUnitList;
    opens me.t3sl4.hydraulic.utils.database.Model.Table.HydraulicUnitList to javafx.base;
    exports me.t3sl4.hydraulic.controllers;
    exports me.t3sl4.hydraulic.controllers.Calculation.Hidros;
    opens me.t3sl4.hydraulic.controllers.Calculation.Hidros to javafx.fxml;
    exports me.t3sl4.hydraulic.controllers.Calculation.Klasik;
    opens me.t3sl4.hydraulic.controllers.Calculation.Klasik to javafx.fxml;
    exports me.t3sl4.hydraulic.controllers.Auth;
    opens me.t3sl4.hydraulic.controllers.Auth to javafx.fxml;
    exports me.t3sl4.hydraulic.controllers.User;
    opens me.t3sl4.hydraulic.controllers.User to javafx.fxml;
    opens me.t3sl4.hydraulic.utils to javafx.base, javafx.fxml;
    exports me.t3sl4.hydraulic.utils.general;
    opens me.t3sl4.hydraulic.utils.general to javafx.base, javafx.fxml;
    exports me.t3sl4.hydraulic.utils.database.Model.Table.PartList;
    opens me.t3sl4.hydraulic.utils.database.Model.Table.PartList to javafx.base;
    exports me.t3sl4.hydraulic.app;
    opens me.t3sl4.hydraulic.app to javafx.base, javafx.fxml;
    exports me.t3sl4.hydraulic.controllers.Popup;
    opens me.t3sl4.hydraulic.controllers.Popup to javafx.fxml;
    exports me.t3sl4.hydraulic.controllers.Calculation.Klasik.PartList;
    opens me.t3sl4.hydraulic.controllers.Calculation.Klasik.PartList to javafx.fxml;
    exports me.t3sl4.hydraulic.controllers.Calculation.Hidros.PartList;
    opens me.t3sl4.hydraulic.controllers.Calculation.Hidros.PartList to javafx.fxml;
    exports me.t3sl4.hydraulic.controllers.Password;
    opens me.t3sl4.hydraulic.controllers.Password to javafx.fxml;
    exports me.t3sl4.hydraulic;
    opens me.t3sl4.hydraulic to javafx.base, javafx.fxml;
    exports me.t3sl4.hydraulic.utils.service.HTTP;
    opens me.t3sl4.hydraulic.utils.service.HTTP to javafx.base;
    exports me.t3sl4.hydraulic.utils.service.HTTP.Request.User;
    opens me.t3sl4.hydraulic.utils.service.HTTP.Request.User to javafx.base;
}