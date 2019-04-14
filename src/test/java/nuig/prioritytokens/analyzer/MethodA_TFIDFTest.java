package nuig.prioritytokens.analyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import nuig.prioritytokens.analyzer.MethodA_TFIDF;

public class MethodA_TFIDFTest
{

	/**
	 * This method checks that the priority score is calculated for Method A.
	 * This method contains the tests for the other 3 methods in the class
	 * 
	 * This test returns the value from the output file (unlike Method B)
	 * 
	 * The output is visible under the directory "output/priority/TFIDF_Output.txt"
	 * The output in the file is James = 0.6931471805599453
	 */
	@Test
	public void testCalculatePriorityTokensTFIDF() 
	{
		ArrayList<String> singleEmailList1 = new ArrayList<String>();
		ArrayList<String> singleEmailList2 = new ArrayList<String>();
		singleEmailList1.add("James");
		singleEmailList2.add("John");
		ArrayList<ArrayList<String>> corpusList1 = new ArrayList<ArrayList<String>>();
		corpusList1.add(singleEmailList1);
		corpusList1.add(singleEmailList2);

		ArrayList<String> singleEmailList3 = new ArrayList<String>();
		singleEmailList3.add("James");

		MethodA_TFIDF obj1 = new MethodA_TFIDF();
		obj1.calculatePriorityTokens(singleEmailList3, corpusList1);

		Double priorityScoreInMap = null;
		String tokenInMap = null;

		for (String token : obj1.priorityTokensMap_TFIDF.keySet()) 
		{
			priorityScoreInMap = obj1.priorityTokensMap_TFIDF.get(token);
			tokenInMap = token;
		}
		assertNotNull(obj1);
		assertNotNull(priorityScoreInMap);
		assertNotNull(tokenInMap);
		assertEquals((Double)0.6931471805599453, priorityScoreInMap);
		assertEquals("James", tokenInMap);
	}


}
