// tabs=4
//************************************************************
//	COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
//
// This file is the product of ArchSynergy, Ltd. and cannot be 
// reproduced, copied, or used in any shape or form without 
// the express written consent of ArchSynergy, Ltd.
//************************************************************
//
//	$Source: /cvsroot/ATM/implementation/exception/MultiplePrimaryKeysException.java,v $
//
//	Reason: This class indicates an exception that is thrown 
//			if the primary key is not properly supplied to the 
//			data access model object as it seeks to retrieve 
//			a record from the repository
//
//	Revision History: See end of file.
//
//*************************************************************

/** @author		$Author: smitra $ */
/** @version	$Revision: 1.1 $ */

// specify the package
package exception;

// system imports

// local imports

/** 
 * This class indicates an exception that is thrown if the primary
 * key supplied to the data access model object is found multiple times as
 * it seeks to retrieve a record from the database
 * 
 */
//--------------------------------------------------------------
public class MultiplePrimaryKeysException
	extends Exception
{	
	/**
	 * Constructor with message
	 *
	 * @param mesg The message associated with the exception
	 */
	//--------------------------------------------------------
	public MultiplePrimaryKeysException(String message)
	{
		super(message);
	}
}

		

//**************************************************************
//	Revision History:
//
//	$Log: MultiplePrimaryKeysException.java,v $
//	Revision 1.1  2004/06/17 04:40:56  smitra
//	First check in
//	
