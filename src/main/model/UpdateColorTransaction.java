package model;

import javafx.scene.Scene;

public class UpdateColorTransaction extends Transaction {

    /**
     * Constructor for this class.
     */
    protected UpdateColorTransaction() throws Exception {
    }

    @Override
    protected void setDependencies() {

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
}