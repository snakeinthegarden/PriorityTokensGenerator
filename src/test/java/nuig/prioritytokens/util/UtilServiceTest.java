package nuig.prioritytokens.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import nuig.prioritytokens.util.UtilService;

public class UtilServiceTest
{
	/**
	 * This test checks the conversion from arrayList of Strings to 1 string value.
	 */
	@Test 
	public void testConvertForTokenization()
	{
		ArrayList<String> testArrayList = new ArrayList<String>();
		testArrayList.add("This");
		testArrayList.add("is");
		testArrayList.add("a");
		testArrayList.add("test");
		
		String testSentence = UtilService.convertForTokenization(testArrayList);
		
		assertNotNull(testSentence);
		//note the method adds a space after each word
		assertEquals("This is a test ", testSentence.toString());
	}
	
	/**
	 * This test checks the conversion from arrayList of no Strings to 1 string value.
	 */
	@Test
	public void testConvertForTokenizationWithEmptyArrayList()
	{
		ArrayList<String> testArrayList = new ArrayList<String>();
		
		String testSentence = UtilService.convertForTokenization(testArrayList);
		
		assertNotNull(testSentence);
		assertEquals(0, testSentence.length());
	}

}