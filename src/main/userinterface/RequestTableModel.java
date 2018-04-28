package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class RequestTableModel
{
    private final SimpleStringProperty id;
    private final SimpleStringProperty requesterNetId;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty email;
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
        id = new SimpleStringProperty(atData.elementAt(0));
        requesterNetId = new SimpleStringProperty(atData.elementAt(1));
        phone = new SimpleStringProperty(atData.elementAt(2));
        email = new SimpleStringProperty(atData.elementAt(3));
        lastName = new SimpleStringProperty(atData.elementAt(4));
        firstName = new SimpleStringProperty(atData.elementAt(5));
        gender = new SimpleStringProperty(atData.elementAt(6));
        articleType = new SimpleStringProperty(atData.elementAt(7));
        color1 = new SimpleStringProperty(atData.elementAt(8));
        color2 = new SimpleStringProperty(atData.elementAt(9));
        size = new SimpleStringProperty(atData.elementAt(10));
        brand = new SimpleStringProperty(atData.elementAt(11));
        status = new SimpleStringProperty(atData.elementAt(12));
        fulfillItemBarcode = new SimpleStringProperty(atData.elementAt(13));
        requestMadeDate = new SimpleStringProperty(atData.elementAt(14));
        requestFulfilledDate = new SimpleStringProperty(atData.elementAt(15));
    }

    //----------------------------------------------------------------------------
    public String getId() { return id.get(); }

    //----------------------------------------------------------------------------
    public void setId(String pref) { id.set(pref); }

    //----------------------------------------------------------------------------
    public String getRequesterNetid() { return requesterNetId.get(); }

    //----------------------------------------------------------------------------
    public void setRequesterNetid(String pref) { requesterNetId.set(pref); }

    //----------------------------------------------------------------------------
    public String getPhone() { return phone.get(); }

    //----------------------------------------------------------------------------
    public void setPhone(String pref) { phone.set(pref); }

    //----------------------------------------------------------------------------
    public String getEmail() { return email.get(); }

    //----------------------------------------------------------------------------
    public void setEmail(String pref) { email.set(pref); }


    //----------------------------------------------------------------------------
    public String getLastName() { return lastName.get(); }

    //----------------------------------------------------------------------------
    public void setLastName(String pref) { lastName.set(pref); }

    //----------------------------------------------------------------------------
    public String getFirstName() { return firstName.get(); }

    //----------------------------------------------------------------------------
    public void setFirstName(String pref) { firstName.set(pref); }

    //----------------------------------------------------------------------------
    public String getGender() { return gender.get(); }

    //----------------------------------------------------------------------------
    public void setGender(String pref) { gender.set(pref); }

    //----------------------------------------------------------------------------
    public String getArticleType() { return articleType.get(); }

    //----------------------------------------------------------------------------
    public void setArticleType(String pref) { articleType.set(pref); }

    //----------------------------------------------------------------------------
    public String getColor1() { return color1.get(); }

    //----------------------------------------------------------------------------
    public void setColor1(String pref) { color1.set(pref); }

    //----------------------------------------------------------------------------
    public String getColor2() { return color2.get(); }

    //----------------------------------------------------------------------------
    public void setColor2(String pref) { color2.set(pref); }

    //----------------------------------------------------------------------------
    public String getSize() { return size.get(); }

    //----------------------------------------------------------------------------
    public void setSize(String pref) { size.set(pref); }

    //----------------------------------------------------------------------------
    public String getBrand() { return brand.get(); }

    //----------------------------------------------------------------------------
    public void setBrand(String pref) { brand.set(pref); }

    //----------------------------------------------------------------------------
    public String getStatus() { return status.get(); }

    //----------------------------------------------------------------------------
    public void setStatus(String stat) { status.set(stat); }

    //----------------------------------------------------------------------------
    public String getFulfillItemBarcode() { return fulfillItemBarcode.get(); }

    //----------------------------------------------------------------------------
    public void setFulfillItemBarcode(String pref) { fulfillItemBarcode.set(pref); }

    //----------------------------------------------------------------------------
    public String getRequestMadeDate() { return requestMadeDate.get(); }

    //----------------------------------------------------------------------------
    public void setRequestMadeDate(String pref)  { requestMadeDate.set(pref); }

    //----------------------------------------------------------------------------
    public String getRequestFulfilledDate() { return requestFulfilledDate.get(); }

    //----------------------------------------------------------------------------
    public void setRequestFulfilledDate(String pref) { requestFulfilledDate.set(pref); }
}
