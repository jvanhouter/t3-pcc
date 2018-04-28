package model;

import Utilities.Utilities;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import Utilities.UiConstants;

public class RequestNotifier
{

    private static RequestNotifier myNotifier;

    private RequestCollection myRequestCollection;
    private ClothingItemCollection myClothingItemCollection;

    private String transactionErrorMessage = "";

    private RequestNotifier() throws Exception
    {

    }

    public static RequestNotifier getInstance() {
        try {
            if (myNotifier == null) myNotifier = new RequestNotifier();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myNotifier;
    }

    /*
     * attempts to match all requests with their respective clothing items.
     * If a match occurs, then it will create
     * 1. a view to display all requests
     * 2. selecting a column will match all clothing items, a mere duplicate of Fulfill after the initial
     */
    public Vector<ClothingRequest> pollRequests() {
        Vector<ClothingRequest> vcr = new Vector<>();
        Iterator requests = Utilities.collectClothingRequestHash().entrySet().iterator();
        while(requests.hasNext()) {
            Map.Entry pair = (Map.Entry)requests.next();
            ClothingRequest cq = (ClothingRequest) pair.getValue();
            if(match(cq)) {
                vcr.add(cq);
            }
        }
        return vcr;
    }

    //------------------------------------------------------
    private boolean match(ClothingRequest cq) {
        String size = (String) cq.getState("RequestedSize");
        String gender = (String) cq.getState("RequestedGender");
        String articleType = (String) cq.getState("RequestedArticleType");
        if(size.equals("")) size = "" + UiConstants.GENERIC_SIZE;
        return search(gender, size, articleType);
    }

    //------------------------------------------------------
    private boolean search(String gender, String size, String articleType) {
        Iterator iter = Utilities.collectClothingHash().entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            ClothingItem ci = (ClothingItem) pair.getValue();
            if(ci.getState("Status").equals("Donated")) {
                if (((String) ci.getState("Gender")).equals(gender) || ((String) ci.getState("Gender")).equals("Unisex")) {
                    if (size.equals("" + UiConstants.GENERIC_SIZE) || ((String) ci.getState("Size")).equals(size) || ((String) ci.getState("Size")).equals("" + UiConstants.GENERIC_SIZE)) {
                        if (((String) ci.getState("ArticleType")).equals(articleType)) {
                           // System.out.println("Success! " + gender + " and " + size + " and " + articleType);
                            //System.out.println("CI: " + ci.getState("Gender") + " and " + ci.getState("Size") + " and " + ci.getState("ArticleType"));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


}
