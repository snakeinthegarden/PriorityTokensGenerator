package nuig.prioritytokens.tokenizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import nuig.prioritytokens.tokenizer.TokenizerService;

public class TokenizerServiceTest 
{

	/**
	 * This test includes the method for tokenizing and the method for writing to file.
	 * It checks that the created file has the content tokenized and without the stop words.
	 */
	@Test
	public void testTokenizeEmail()
	{
		String emailAsSentenceTest = "This is a test";
		ArrayList<String> stopWordsTest = new ArrayList<String>();
		stopWordsTest.add("is");
		stopWordsTest.add("a");
		String outputSingleEmailTest = "output\\Tests" + "\\" + "testTokenizer1.txt";
		TokenizerService tokenizeTestObject = new TokenizerService();
		ArrayList<String> returnFromWriteToFileTest  = tokenizeTestObject.tokenizeEmail(emailAsSentenceTest, stopWordsTest, outputSingleEmailTest);
		try
		{
			File newFile = new File("output\\Tests\\testTokenizer1.txt");
			FileReader fileReader = new FileReader(newFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				returnFromWriteToFileTest.add(line);
			}
			reader.close();
		}
		catch (IOException e)
		{ 
			e.printStackTrace();
		} 
		assertNotNull(returnFromWriteToFileTest);
		assertEquals("This", returnFromWriteToFileTest.get(0));
		assertEquals("test", returnFromWriteToFileTest.get(1));
	}
	
	/**
	 * This test includes the method for tokenizing and the method for writing to file.
	 * It checks that the process still writes to file, even without a sentence to check stop words against.
	 */
	@Test
	public void testTokenizeEmailWithNoSentenceValue()
	{
		String emailAsSentenceTest = "";
		ArrayList<String> stopWordsTest = new ArrayList<String>();
		stopWordsTest.add("is");
		stopWordsTest.add("a");
		String outputSingleEmailTest = "output\\Tests" + "\\" + "testTokenizer2.txt";
		TokenizerService tokenizeTestObject = new TokenizerService();
		ArrayList<String> returnFromWriteToFileTest = tokenizeTestObject.tokenizeEmail(emailAsSentenceTest, stopWordsTest, outputSingleEmailTest);
		try
		{
			File newFile = new File("output\\Tests\\testTokenizer2.txt");
			FileReader fileReader = new FileReader(newFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				returnFromWriteToFileTest.add(line);
			}
			reader.close();
		}
		catch (IOException e)
		{ 
			e.printStackTrace();
		} 
		assertNotNull(returnFromWriteToFileTest);
		assertEquals(0, returnFromWriteToFileTest.size());
	}
	
	
	/**
	 * This test includes the method for tokenizing and the method for writing to file.
	 * It checks that the process still writes to file, even without stop words list the sentence against.
	 */	
	@Test
	public void testTokenizeEmailWithEmptyStopWordsList()
	{
		String emailAsSentenceTest = "This is a test";
		ArrayList<String> stopWordsTest = new ArrayList<String>();
		String outputSingleEmailTest = "output\\Tests" + "\\" + "testTokenizer3.txt";
		TokenizerService tokenizeTestObject = new TokenizerService();
		ArrayList<String> returnFromWriteToFileTest = tokenizeTestObject.tokenizeEmail(emailAsSentenceTest, stopWordsTest, outputSingleEmailTest);
		try
		{
			File newFile = new File("output\\Tests\\testTokenizer3.txt");
			FileReader fileReader = new FileReader(newFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				returnFromWriteToFileTest.add(line);
			}
			reader.close();
		}
		catch (IOException e)
		{ 
			e.printStackTrace();
		} 
		assertNotNull(returnFromWriteToFileTest);
		assertEquals("This", returnFromWriteToFileTest.get(0));
		assertEquals("is", returnFromWriteToFileTest.get(1));
		assertEquals("a", returnFromWriteToFileTest.get(2));
		assertEquals("test", returnFromWriteToFileTest.get(3));
	}
	
}
