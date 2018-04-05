// tabs=4
//************************************************************
//	COPYRIGHT 2009/2015 Sandeep Mitra and Michael Steves, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package userinterface;

// system imports
import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Group;

// project imports
import impresario.IView;
import impresario.IModel;
import impresario.IControl;
import impresario.ControlRegistry;
import javafx.stage.FileChooser;
import model.ArticleTypeCollection;

//==============================================================
public abstract class View extends Group
	implements IView, IControl
{
	// private data
	protected IModel myModel;
	protected ControlRegistry myRegistry;

	// GUI components
	
		
	// Class constructor
	//----------------------------------------------------------
	public View(IModel model, String classname)
	{
		myModel = model;

		myRegistry = new ControlRegistry(classname);

	}
	
	
	//----------------------------------------------------------
	public void setRegistry(ControlRegistry registry)
	{
		myRegistry = registry;
	}
	
	// Allow models to register for state updates
	//----------------------------------------------------------
	public void subscribe(String key,  IModel subscriber)
	{
		myRegistry.subscribe(key, subscriber);
	}
		
		
	// Allow models to unregister for state updates
	//----------------------------------------------------------
	public void unSubscribe(String key, IModel subscriber)
	{
		myRegistry.unSubscribe(key, subscriber);
	}
   	
}

