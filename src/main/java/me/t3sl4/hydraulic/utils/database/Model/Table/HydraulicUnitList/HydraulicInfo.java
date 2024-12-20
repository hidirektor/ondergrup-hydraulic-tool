package me.t3sl4.hydraulic.utils.database.Model.Table.HydraulicUnitList;

import org.json.JSONObject;

public class HydraulicInfo {
    private int id;
    private String userID;
    private String userName;
    private String orderID;
    private String partListID;
    private String schematicID;
    private String hydraulicType;
    private boolean isLocal;
    private long createdDate;
    private String unitParameters;

    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPartListID() {
        return partListID;
    }

    public void setPartListID(String partListID) {
        this.partListID = partListID;
    }

    public String getSchematicID() {
        return schematicID;
    }

    public void setSchematicID(String schematicID) {
        this.schematicID = schematicID;
    }

    public String getHydraulicType() {
        return hydraulicType;
    }

    public void setHydraulicType(String hydraulicType) {
        this.hydraulicType = hydraulicType;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getUnitParameters() {
        return unitParameters;
    }

    public void setUnitParameters(String unitParameters) {
        this.unitParameters = unitParameters;
    }
}