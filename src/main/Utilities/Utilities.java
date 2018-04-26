// specify the package
package Utilities;

// system imports

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// project imports

/**
 * Useful Utilities
 */
//==============================================================
public class Utilities {
    // Hash String, String maps to ID, Description
    private static HashMap<String, ArticleType> articleTypeHash;
    private static HashMap<String, Color> colorHash;
    private static HashMap<String, ClothingItem> clothingHash;
    private static HashMap<String, ClothingRequest> clothingRequestHash;

    //----------------------------------------------------------
    public static void removeArticleHashData(String id) {
        articleTypeHash = collectArticleTypeHash();
        articleTypeHash.remove(id);
    }

    //----------------------------------------------------------
    public static void removeColorHashData(String id) {
        colorHash = collectColorHash();
        colorHash.remove(id);
    }

    //----------------------------------------------------------
    public static void removeClothingHash(String id) {
        clothingHash = collectClothingHash();
        clothingHash.remove(id);
    }

    //----------------------------------------------------------
    public static void removeClothingRequestHash(String id) {
        clothingRequestHash = collectClothingRequestHash();
        clothingRequestHash.remove(id);
    }
    //----------------------------------------------------------
    public static HashMap<String, ClothingRequest> collectClothingRequestHash() {
        if (clothingRequestHash == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Preparing clothing request...");
            alert.setHeaderText("Loading...");
            alert.setContentText("Please wait while Clothing Request is loaded.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.lookupButton(ButtonType.OK).setDisable(true);
            dialogPane.lookupButton(ButtonType.OK).setVisible(false);
            alert.show();
            clothingRequestHash = new HashMap<>();
            RequestCollection rq = new RequestCollection();
            rq.findAll();
            Vector clothingEntryList = (Vector) rq.getState("Requests");
            if (clothingEntryList.size() > 0) {
                Enumeration entries = clothingEntryList.elements();

                while (entries.hasMoreElements()) {
                    ClothingRequest nextCI = (ClothingRequest) entries.nextElement();
                    clothingRequestHash.put((String) nextCI.getState("ID"), nextCI);
                }
            }
            alert.close();
        }
        return clothingRequestHash;
    }

    //----------------------------------------------------------
    public static HashMap<String, ClothingItem> collectClothingHash() {
        if (clothingHash == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Preparing clothing item...");
            alert.setHeaderText("Loading...");
            alert.setContentText("Please wait while ClothingItem is loaded.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.lookupButton(ButtonType.OK).setDisable(true);
            dialogPane.lookupButton(ButtonType.OK).setVisible(false);
            alert.show();
            clothingHash = new HashMap<>();
            ClothingItemCollection clothing = new ClothingItemCollection();
            clothing.findAll();
            Vector clothingEntryList = (Vector) clothing.getState("ClothingItems");
            if (clothingEntryList.size() > 0) {
                Enumeration entries = clothingEntryList.elements();

                while (entries.hasMoreElements()) {
                    ClothingItem nextCI = (ClothingItem) entries.nextElement();
                    clothingHash.put((String) nextCI.getState("ID"), nextCI);
                }
            }
            alert.close();
        }
        return clothingHash;
    }
    //----------------------------------------------------------
    public static HashMap<String, Color> collectColorHash() {
        if (colorHash == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Preparing color...");
            alert.setHeaderText("Loading...");
            alert.setContentText("Please wait while Color is loaded.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.lookupButton(ButtonType.OK).setDisable(true);
            dialogPane.lookupButton(ButtonType.OK).setVisible(false);
            alert.show();
            colorHash = new HashMap<>();
            ColorCollection colors = new ColorCollection();
            colors.findAll();
            Vector colorEntryList = (Vector) colors.getState("Colors");
            if (colorEntryList.size() > 0) {
                Enumeration entries = colorEntryList.elements();

                while (entries.hasMoreElements()) {
                    model.Color nextCT = (model.Color) entries.nextElement();
                    colorHash.put((String) nextCT.getState("ID"), nextCT);
                }
            }
            alert.close();
        }
        return colorHash;
    }
    //----------------------------------------------------------
    public static HashMap<String, ArticleType> collectArticleTypeHash() {
        if (articleTypeHash == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Preparing article type...");
            alert.setHeaderText("Loading...");
            alert.setContentText("Please wait while Article Type is loaded.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.lookupButton(ButtonType.OK).setDisable(true);
            dialogPane.lookupButton(ButtonType.OK).setVisible(false);

            alert.show();
            articleTypeHash = new HashMap<>();
            ArticleTypeCollection atc = new ArticleTypeCollection();
            atc.findAll();
            Vector entryList = (Vector) atc.getState("ArticleTypes");
            if (entryList.size() > 0) {
                Enumeration entries = entryList.elements();

                while (entries.hasMoreElements()) {
                    ArticleType nextAT = (ArticleType) entries.nextElement();
                    articleTypeHash.put((String) nextAT.getState("ID"), nextAT);
                }
            }
            alert.close();
        }
        return articleTypeHash;
    }

    //----------------------------------------------------------
    public static void putClothingRequestHash(String id, ClothingRequest item) {
        clothingRequestHash = collectClothingRequestHash();
        clothingRequestHash.put(id, item);
    }

    //----------------------------------------------------------
    public static void putClothingHash(String id, ClothingItem item) {
        clothingHash = collectClothingHash();
        clothingHash.put(id, item);
    }
    //----------------------------------------------------------
    public static void putColorHash(String id, Color color) {
        colorHash = collectColorHash();
        colorHash.put(id, color);
    }
    //----------------------------------------------------------
    public static void putArticleTypeHash(String id, ArticleType article) {
        articleTypeHash = collectArticleTypeHash();
        articleTypeHash.put(id, article);
    }

    //----------------------------------------------------------
    // auto fill dashes, placing here for universal constant.
    // commander we probably should've done this earlier
    public static String autoFillDashes(String input)
    {
        if(input.matches("[0-9]{4}"))
        {
            return input.substring(0, input.length() - 1) + "-" + input.substring(input.length() - 1);
        }
        if(input.matches("[0-9]{3}-[0-9]{5}")) {
            return input.substring(0, input.length() - 2) + "-" + input.substring(input.length() - 2);
        }
        return input;
    }

    //----------------------------------------------------------
    // developing method to reformat size for query optimization for approximating sizes based on input value
    public static String rebufferSize(String size) {
        if (size.matches("[0-9]+")) {

        }
        return size;
    }

    //----------------------------------------------------------
    // great for query manipulation i.e appending AND then stripping the last AND
    public static String replaceLast(String find, String replace, String string) {
        int lastIndex = string.lastIndexOf(find);

        if (lastIndex == -1) {
            return string;
        }

        String beginString = string.substring(0, lastIndex);
        String endString = string.substring(lastIndex + find.length());

        return beginString + replace + endString;
    }

    //----------------------------------------------------------
    public static String convertToDefaultDateFormat(Date theDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        String valToReturn = formatter.format(theDate);

        return valToReturn;

    }

    //----------------------------------------------------------
    public static String convertDateStringToDefaultDateFormat(String dateStr) {

        Date theDate = validateDateString(dateStr);

        if (theDate == null) {
            return null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

            String valToReturn = formatter.format(theDate);

            return valToReturn;
        }
    }

    //----------------------------------------------------------
    protected static Date validateDateString(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        Date theDate = null;

        try {
            theDate = formatter.parse(str);
            return theDate;
        } catch (ParseException ex) {
            SimpleDateFormat formatter2 =
                    new SimpleDateFormat("yyyy-MM-dd");

            try {
                theDate = formatter2.parse(str);
                return theDate;
            } catch (ParseException ex2) {
                SimpleDateFormat formatter3 =
                        new SimpleDateFormat("yyyy/MMdd");

                try {
                    theDate = formatter3.parse(str);
                    return theDate;
                } catch (ParseException ex3) {
                    SimpleDateFormat formatter4 =
                            new SimpleDateFormat("yyyyMM/dd");

                    try {
                        theDate = formatter4.parse(str);
                        return theDate;
                    } catch (ParseException ex4) {
                        return null;
                    }
                }
            }
        }
    }

    //----------------------------------------------------------
    protected String mapMonthToString(int month) {
        if (month == Calendar.JANUARY) {
            return "January";
        } else if (month == Calendar.FEBRUARY) {
            return "February";
        } else if (month == Calendar.MARCH) {
            return "March";
        } else if (month == Calendar.APRIL) {
            return "April";
        } else if (month == Calendar.MAY) {
            return "May";
        } else if (month == Calendar.JUNE) {
            return "June";
        } else if (month == Calendar.JULY) {
            return "July";
        } else if (month == Calendar.AUGUST) {
            return "August";
        } else if (month == Calendar.SEPTEMBER) {
            return "September";
        } else if (month == Calendar.OCTOBER) {
            return "October";
        } else if (month == Calendar.NOVEMBER) {
            return "November";
        } else if (month == Calendar.DECEMBER) {
            return "December";
        }

        return "";
    }

    //----------------------------------------------------------
    protected int mapMonthNameToIndex(String monthName) {
        if (monthName.equals("January") == true) {
            return Calendar.JANUARY;
        } else if (monthName.equals("February") == true) {
            return Calendar.FEBRUARY;
        } else if (monthName.equals("March") == true) {
            return Calendar.MARCH;
        } else if (monthName.equals("April") == true) {
            return Calendar.APRIL;
        } else if (monthName.equals("May") == true) {
            return Calendar.MAY;
        } else if (monthName.equals("June") == true) {
            return Calendar.JUNE;
        } else if (monthName.equals("July") == true) {
            return Calendar.JULY;
        } else if (monthName.equals("August") == true) {
            return Calendar.AUGUST;
        } else if (monthName.equals("September") == true) {
            return Calendar.SEPTEMBER;
        } else if (monthName.equals("October") == true) {
            return Calendar.OCTOBER;
        } else if (monthName.equals("November") == true) {
            return Calendar.NOVEMBER;
        } else if (monthName.equals("December") == true) {
            return Calendar.DECEMBER;
        }

        return -1;
    }


    //----------------------------------------------------
    protected boolean checkProperLetters(String value) {
        for (int cnt = 0; cnt < value.length(); cnt++) {
            char ch = value.charAt(cnt);

            if ((ch >= 'A') && (ch <= 'Z') || (ch >= 'a') && (ch <= 'z')) {
            } else if ((ch == '-') || (ch == ',') || (ch == '.') || (ch == ' ')) {
            } else {
                return false;
            }
        }

        return true;
    }

    //----------------------------------------------------
    protected boolean checkProperPhoneNumber(String value) {
        if ((value == null) || (value.length() < 7)) {
            return false;
        }

        for (int cnt = 0; cnt < value.length(); cnt++) {
            char ch = value.charAt(cnt);

            if ((ch >= '0') && (ch <= '9')) {
            } else if ((ch == '-') || (ch == '(') || (ch == ')') || (ch == ' ')) {
            } else {
                return false;
            }
        }

        return true;
    }

}

