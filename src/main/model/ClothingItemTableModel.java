package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class ClothingItemTableModel
{
	private final SimpleStringProperty barcode;
	private final SimpleStringProperty gender;
	private final SimpleStringProperty color1;
	private final SimpleStringProperty color2;
	private final SimpleStringProperty brand;
	private final SimpleStringProperty donorInformation;
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
		donorInformation =  new SimpleStringProperty(atData.elementAt(6));
		notes =  new SimpleStringProperty(atData.elementAt(7));
		
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
    public String getDonorInformation() {
        return donorInformation.get();
    }

    //----------------------------------------------------------------------------
    public void setDonorInformation(String donorInfo)
    {
    	donorInformation.set(donorInfo);
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
}
