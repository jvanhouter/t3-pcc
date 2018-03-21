package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class RequestTableModel
{
    private final SimpleStringProperty requesterNetId;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty articleType;
    private final SimpleStringProperty color1;
    private final SimpleStringProperty color2;
    private final SimpleStringProperty size;
    private final SimpleStringProperty brand;
    private final SimpleStringProperty status;
    private final SimpleStringProperty fulfillItemBarcode;
    private final SimpleStringProperty requestMadeDate;
    private final SimpleStringProperty requestFulfilledDate;

    //----------------------------------------------------------------------------
    public RequestTableModel(Vector<String> atData)
    {
        requesterNetId = new SimpleStringProperty(atData.elementAt(0));
        phone = new SimpleStringProperty(atData.elementAt(1));
        lastName = new SimpleStringProperty(atData.elementAt(2));
        firstName = new SimpleStringProperty(atData.elementAt(3));
        gender = new SimpleStringProperty(atData.elementAt(4));
        articleType = new SimpleStringProperty(atData.elementAt(5));
        color1 = new SimpleStringProperty(atData.elementAt(6));
        color2 = new SimpleStringProperty(atData.elementAt(7));
        size = new SimpleStringProperty(atData.elementAt(8));
        brand = new SimpleStringProperty(atData.elementAt(9));
        status = new SimpleStringProperty(atData.elementAt(10));
        fulfillItemBarcode = new SimpleStringProperty(atData.elementAt(11));
        requestMadeDate = new SimpleStringProperty(atData.elementAt(12));
        requestFulfilledDate = new SimpleStringProperty(atData.elementAt(13));
    }

    //----------------------------------------------------------------------------
    public String getRequesterNetId() {
        return requesterNetId.get();
    }

    //----------------------------------------------------------------------------
    public void setRequesterNetId(String pref) {
        requesterNetId.set(pref);
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
