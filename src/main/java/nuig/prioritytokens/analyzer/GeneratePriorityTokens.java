package nuig.prioritytokens.analyzer;

import java.util.ArrayList;

public interface GeneratePriorityTokens
{
	/**
	 * Generate method that requires the calculation of priority tokens
	 * This will generally compare the tokens in the singleEmailList against the tokens in the corpusOfEmailList
	 * Various method may be used to compare 
	 * 
	 * @param singleEmailList
	 * @param corpusOfEmailsList
	 */
	public void calculatePriorityTokens(ArrayList<String> singleEmailList, ArrayList<ArrayList<String>> corpusOfEmailsList);
}
