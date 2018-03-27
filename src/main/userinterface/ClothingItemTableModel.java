package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class ClothingItemTableModel
{
	private final SimpleStringProperty barcode;
	private final SimpleStringProperty gender;
	private final SimpleStringProperty size;
	private final SimpleStringProperty color1;
	private final SimpleStringProperty color2;
	private final SimpleStringProperty brand;
	private final SimpleStringProperty donorPhone;
	private final SimpleStringProperty donorEmail;
	private final SimpleStringProperty donorFirstName;
	private final SimpleStringProperty donorLastName;
	private final SimpleStringProperty notes;
	private final SimpleStringProperty articleType;

	//----------------------------------------------------------------------------
	public ClothingItemTableModel(Vector<String> atData)
	{
		barcode =  new SimpleStringProperty(atData.elementAt(0));
		gender =  new SimpleStringProperty(atData.elementAt(1));
		color1 =  new SimpleStringProperty(atData.elementAt(2));
		color2 =  new SimpleStringProperty(atData.elementAt(3));
		brand =  new SimpleStringProperty(atData.elementAt(4));
		articleType =  new SimpleStringProperty(atData.elementAt(5));
		donorPhone =  new SimpleStringProperty(atData.elementAt(6));
		donorEmail =  new SimpleStringProperty(atData.elementAt(7));
		donorFirstName =  new SimpleStringProperty(atData.elementAt(8));
		donorLastName =  new SimpleStringProperty(atData.elementAt(9));
		notes =  new SimpleStringProperty(atData.elementAt(10));
		size =  new SimpleStringProperty(atData.elementAt(10));
		
	}

	//----------------------------------------------------------------------------
	public String getBarcode() {
        return barcode.get();
    }

	//----------------------------------------------------------------------------
    public void setBarcode(String bc) {
        barcode.set(bc);
    }

    //----------------------------------------------------------------------------
    public String getGender() {
        return gender.get();
    }

    //----------------------------------------------------------------------------
    public void setGender(String gend) {
        gender.set(gend);
    }

    //----------------------------------------------------------------------------
    public String getColor1() {
        return color1.get();
    }

    //----------------------------------------------------------------------------
    public void setColor1(String co1) {
        color1.set(co1);
    }
    
    //----------------------------------------------------------------------------
    public String getColor2() {
        return color2.get();
    }

    //----------------------------------------------------------------------------
    public void setColor2(String co2)
    {
    	color2.set(co2);
    }
    //----------------------------------------------------------------------------
    public String getBrand() {
        return brand.get();
    }

    //----------------------------------------------------------------------------
    public void setBrand(String brd)
    {
    	brand.set(brd);
    }
    //----------------------------------------------------------------------------
    public String getArticleType() {
        return articleType.get();
    }

    //----------------------------------------------------------------------------
    public void setArticleType(String at)
    {
    	articleType.set(at);
    }
    //----------------------------------------------------------------------------
    public String getDonorPhone() {
        return donorPhone.get();
    }

    //----------------------------------------------------------------------------
    public void setDonorPhone(String donorPhoneString)
    {
    	donorPhone.set(donorPhoneString);
    }
    //----------------------------------------------------------------------------
    public String getDonorEmail() {
        return donorEmail.get();
    }

    //----------------------------------------------------------------------------
    public void setDonorEmail(String donorEmailString)
    {
    	donorEmail.set(donorEmailString);
    }
    //----------------------------------------------------------------------------
    public String getDonorFirstName() {
        return donorFirstName.get();
    }

    //----------------------------------------------------------------------------
    public void setDonorFirstName(String donorFirstNameString)
    {
    	donorFirstName.set(donorFirstNameString);
    }
    //----------------------------------------------------------------------------
    public String getDonorLastName() {
        return donorLastName.get();
    }

    //----------------------------------------------------------------------------
    public void setDonorInformation(String donorLastNameString)
    {
    	donorLastName.set(donorLastNameString);
    }
    //----------------------------------------------------------------------------
    public String getNotes() {
        return notes.get();
    }

    //----------------------------------------------------------------------------
    public void setNotes(String note)
    {
    	notes.set(note);
    }
    //----------------------------------------------------------------------------
    public String getSize() {
        return size.get();
    }

    //----------------------------------------------------------------------------
    public void setSize(String sizeString)
    {
    	notes.set(sizeString);
    }
}
