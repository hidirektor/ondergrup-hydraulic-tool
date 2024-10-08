package me.t3sl4.hydraulic.utils.database.Model.Table.PartList;

import javafx.beans.property.SimpleStringProperty;

public class TableData {
    private final SimpleStringProperty satir1Property;
    private final SimpleStringProperty satir2Property;

    public TableData(String data1, String data2) {
        this.satir1Property = new SimpleStringProperty(data1);
        this.satir2Property = new SimpleStringProperty(data2);
    }

    public String getSatir1Property() {
        return satir1Property.get();
    }

    public void setSatir1Property(String satir1) {
        satir1Property.set(satir1);
    }

    public String getSatir2Property() {
        return satir2Property.get();
    }

    public void setSatir2Property(String satir2) {
        satir2Property.set(satir2);
    }
}