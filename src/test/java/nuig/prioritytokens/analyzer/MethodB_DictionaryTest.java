package nuig.prioritytokens.analyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import nuig.prioritytokens.analyzer.MethodB_Dictionary;

public class MethodB_DictionaryTest 
{

	/**
	 * This method checks that the priority score is calculated for Method B.
	 * This method contains the tests for the other 2 methods in the class
	 *
	 * In this case, the map contains 2 values: James and John
	 * The for loop overwrites the temporary values so it leaves the loop with values for John at 0.5.
	 * 
	 * The output is visible under the directory "output/priority/Dictionary_output.txt"
	 * The output in the file is James = 1.0.
	 */
	@Test
	public void testCalculatePriorityTokensDictionary() 
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

		MethodB_Dictionary obj1 = new MethodB_Dictionary();
		obj1.calculatePriorityTokens(singleEmailList3, corpusList1);

		Double priorityScoreInMap = null;
		String tokenInMap = null;

		for (String token : obj1.tokenDictionaryMapWithFrequency.keySet()) 
		{
			priorityScoreInMap = obj1.tokenDictionaryMapWithFrequency.get(token);
			tokenInMap = token;
		}
		assertNotNull(obj1);
		assertNotNull(priorityScoreInMap);
		assertNotNull(tokenInMap);
		assertEquals((Double)0.5, priorityScoreInMap);
		assertEquals("John", tokenInMap);
	}

}
