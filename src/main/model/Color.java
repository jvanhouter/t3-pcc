package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IView;

/**
 * Jan 30, 2018
 * @author Jackson Taber & Kyle Darling
 */

public class Color {

    private static final String myTableName = "Color";

    protected Properties dependencies;


    public Color(String colorId) throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (colorID = " + colorId + ")";
    }


}
