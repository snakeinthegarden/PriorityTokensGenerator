package nuig.prioritytokens.util;

import java.util.ArrayList;

public class UtilService
{

	
	/** 
	 * Method to convert array of strings to a single string as is required by the TokenizerME class
	 * @param listToBeConverted
	 * @return  String containing the contents of the listToBeConverted
	 */
	public static String convertForTokenization(ArrayList<String> listToBeConverted) 
	{
		String emailAsSentence = "";
		for (String str : listToBeConverted)
		{
			emailAsSentence += str + " " ;
		}
		return emailAsSentence;
	}
	
}
