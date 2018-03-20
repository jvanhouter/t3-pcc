// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;

import userinterface.View;
import userinterface.ViewFactory;

//----------------------------------------------------------
public class FulfilRequestTransaction extends Transaction
{
    private String transactionErrorMessage = "";
}

//----------------------------------------------------------
public FulfilRequestTransaction() throws Exception
{
    super();
}

//----------------------------------------------------------
protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelFulfilRequest", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("ClothingRequestData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }
