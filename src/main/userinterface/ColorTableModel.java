package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class ColorTableModel
{
    private final SimpleStringProperty barcodePrefix;
    private final SimpleStringProperty description;
    private final SimpleStringProperty alphaCode;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public ColorTableModel(Vector<String> atData)
    {
        barcodePrefix =  new SimpleStringProperty(atData.elementAt(0));
        description =  new SimpleStringProperty(atData.elementAt(1));
        alphaCode =  new SimpleStringProperty(atData.elementAt(2));
        status =  new SimpleStringProperty(atData.elementAt(3));
    }

    //----------------------------------------------------------------------------
    public String getBarcodePrefix() {
        return barcodePrefix.get();
    }

    //----------------------------------------------------------------------------
    public void setBarcodePrefix(String pref) {
        barcodePrefix.set(pref);
    }

    //----------------------------------------------------------------------------
    public String getDescription() {
        return description.get();
    }

    //----------------------------------------------------------------------------
    public void setDescription(String desc) {
        description.set(desc);
    }

    //----------------------------------------------------------------------------
    public String getAlphaCode() {
        return alphaCode.get();
    }

    //----------------------------------------------------------------------------
    public void setAlphaCode(String code) {
        alphaCode.set(code);
    }

    //----------------------------------------------------------------------------
    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String stat)
    {
        status.set(stat);
    }
}
