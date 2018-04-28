package model;

import Utilities.Utilities;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

<<<<<<< HEAD
import Utilities.UiConstants;

public class RequestNotifier
{
=======
public class RequestNotifier extends Transaction {
>>>>>>> d6b04599fd846f52de7fc52a481b6f03a9849829

    private static RequestNotifier myNotifier;

    private RequestCollection myRequestCollection;
    private ClothingItemCollection myClothingItemCollection;

    private String transactionErrorMessage = "";

<<<<<<< HEAD
    private RequestNotifier() throws Exception
    {
=======
    private RequestNotifier() throws Exception {
        super();
    }

    public static RequestNotifier getInstance() {
        try {
            if (myNotifier == null) myNotifier = new RequestNotifier();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myNotifier;
    }

    @Override
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelRequest", "CancelTransaction");
        dependencies.setProperty("CancelClothingItemList", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("ProcessRequest", "TransactionError");
        dependencies.setProperty("ClothingRequestData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    @Override
    protected Scene createView() {
        return null;
    }

    @Override
    public Object getState(String key) {
        return null;
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
>>>>>>> d6b04599fd846f52de7fc52a481b6f03a9849829

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
            if (((String) ci.getState("Gender")).equals(gender) || ((String) ci.getState("Gender")).equals("Unisex")) {
                if (size.equals("" + UiConstants.GENERIC_SIZE) || ((String) ci.getState("Size")).equals(size) || ((String) ci.getState("Size")).equals("" + UiConstants.GENERIC_SIZE)) {
                    if (((String) ci.getState("ArticleType")).equals(articleType)) {
                        //System.out.println("Success! " + gender + " and " + size + " and " + articleType);
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
