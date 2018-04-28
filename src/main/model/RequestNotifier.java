package model;

import javafx.scene.Scene;

import java.util.Properties;

public class RequestNotifier extends Transaction {

    private static RequestNotifier myNotifier;

    private RequestCollection myRequestCollection;
    private ClothingItemCollection myClothingItemCollection;

    private String transactionErrorMessage = "";

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

    }

    /*
     * attempts to match all requests with their respective clothing items.
     * If a match occurs, then it will create
     * 1. a view to display all requests
     * 2. selecting a column will match all clothing items, a mere duplicate of Fulfill after the initial
     */
    public boolean pollRequests() {
        boolean requests = false;

        return requests;
    }


}
