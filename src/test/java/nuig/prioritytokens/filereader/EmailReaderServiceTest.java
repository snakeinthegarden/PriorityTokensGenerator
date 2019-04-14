package nuig.prioritytokens.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import nuig.prioritytokens.filereader.EmailReaderService;

public class EmailReaderServiceTest
{

	/**
	 * This tests that the stop words file populates an arrayList from the required folder
	 */
	@Test
	public void testGetStopWords() 
	{
		EmailReaderService emailReaderObject = new EmailReaderService();
		ArrayList<String> stopWordList1 = emailReaderObject.getStopWords();
		assertNotNull(stopWordList1);
		assertEquals(819, stopWordList1.size());
	}
	
	
	/**
	 * This tests that the directory's files from the path provided populates an arrayList
	 */
	@Test 
	public void testGetDirectoryFilesSomethingInDirectory() 
	{

		ArrayList<String> testReturnedDirectoryList1 = EmailReaderService.getDirectoryFiles();
		assertNotNull(testReturnedDirectoryList1);
		assertEquals(308, testReturnedDirectoryList1.size());
	}

	
	/**
	 * This tests that the metadata is filtered from an email correctly
	 */
	@Test
	public void testGetEmailNotBlank() 
	{
		String metadataTest1 = "input/unitTests/2";
		ArrayList<String> filteredMetadataList1 = EmailReaderService.getEmail(metadataTest1);
		assertNotNull(filteredMetadataList1);
		assertEquals(10, filteredMetadataList1.size());
	}

	
	/**
	 * This tests that if the email is blank, there is no issue, it just returns nothing.
	 */
	@Test
	public void testGetEmailBlank() 
	{
		String metadataTest2 = "input/unitTests/emptyFile";
		ArrayList<String> filteredMetadataList2 = EmailReaderService.getEmail(metadataTest2);
		assertNotNull(filteredMetadataList2);
		assertEquals(0, filteredMetadataList2.size());
	}
	
	
	/**
	 * This tests that if the directory contains some emails, if so it reads into the program.
	 * This includes the test for method .readInTokenizedWords() as it is called within the method
	 */
	@Test
	public void testCorpusContainsSomeEmails()
	{
		String directoryPathTest = "input/unitTests/directory";
		EmailReaderService emailReaderObject2 = new EmailReaderService();
		ArrayList<ArrayList<String>> corpusOfEmailsListTest = emailReaderObject2.getCorpusAsTokens(directoryPathTest);
		assertNotNull(corpusOfEmailsListTest);
		assertEquals(1, corpusOfEmailsListTest.size());
	}

	
	/**
	 * This tests that if the directory contains no emails, if so it reads into the program.
	 * This includes the test for method .readInTokenizedWords() as it is called within the method
	 */
	@Test
	public void testCorpusContainsNoEmails()
	{
		String directoryPathTest = "input/unitTests/TestIfNoFilesInDirectory";
		EmailReaderService emailReaderObject2 = new EmailReaderService();
		ArrayList<ArrayList<String>> corpusOfEmailsListTest = emailReaderObject2.getCorpusAsTokens(directoryPathTest);
		assertNotNull(corpusOfEmailsListTest);
		assertEquals(0, corpusOfEmailsListTest.size());
	}
}
