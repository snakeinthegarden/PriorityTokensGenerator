package nuig.prioritytokens.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;


public class EmailReaderService 
{

	private final static String corpusOfEmailsSource = "input/directory";
	private final static String stopWordsSource = "input/stopwords/StopWordsListFinal.txt";

	
	
	/**
	 * Load the stop words file into system
	 * 
	 * @return ArrayList<String> containing stop words
	 */
	public ArrayList<String> getStopWords()
	{
		ArrayList<String> stopWordsList = new ArrayList<String>();
		try
		{
			File emailContainingMetadata = new File(stopWordsSource);
			Reader fileReaderContainingStopWords = new FileReader(emailContainingMetadata);
			BufferedReader bufferedReaderContainingStopWords = new BufferedReader(fileReaderContainingStopWords);
			String lineStopWords = null;
			while ((lineStopWords = bufferedReaderContainingStopWords.readLine()) != null)
			{ 
				stopWordsList.add(lineStopWords);
			} 
			bufferedReaderContainingStopWords.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 

		return stopWordsList;
	}

	
	
	
	/**
	 * Get list of all files in given directory path
	 * 
	 * @return ArrayList<String> containing the paths of each file in the directory
	 */
	public static ArrayList<String> getDirectoryFiles()
	{

		ArrayList<String> directoriesList = new ArrayList<String>();

		//create a File object and pass it the directory file path to be tokenized
		final File localDirectoryNameObject = new File(corpusOfEmailsSource);

		getDirectoryFile(localDirectoryNameObject, directoriesList);

		return directoriesList;
	}

	
	
	
	/**
	 * Get single directory file
	 * 
	 * @param localDirectoryNameObject
	 * @param directoriesList
	 */
	public static void getDirectoryFile(final File localDirectoryNameObject, ArrayList<String> directoriesList)
	{
		for (final File fileEntry : localDirectoryNameObject.listFiles())
		{
			if (fileEntry.isDirectory()) 
			{
				getDirectoryFile(fileEntry, directoriesList);
			}
			else 
			{
				//ignore any desktop.ini files that may be in the directories (as do not needed to be tokenized)
				if (	fileEntry.getAbsolutePath().contains("desktop.ini") != true	)
				{			
					directoriesList.add(fileEntry.getAbsolutePath());
				}
			}
		}
	}
	
	
	
	
	/**
	 * Load email, and remove meta data while loading
	 * 
	 * @param emailSource
	 * @return
	 */
	public static ArrayList<String> getEmail(String emailSource)
	{
		String lineToRemove1 = "Message-ID:";
		String lineToRemove2 = "Date:";
		String lineToRemove3 = "From:";
		String lineToRemove4 = "To";
		String lineToRemove5 = "Cc:";
		String lineToRemove6 = "Mime-Version:";
		String lineToRemove7 = "Content-Type:";
		String lineToRemove8 = "Content-Transfer-Encoding:";
		String lineToRemove9 = "X-From:";
		String lineToRemove10 = "X-To:";
		String lineToRemove11 = "X-cc:";
		String lineToRemove12 = "X-bcc:";
		String lineToRemove13 = "X-Folder:";
		String lineToRemove14 = "X-Origin:";
		String lineToRemove15 = "X-FileName:";
		String lineToRemove16 = "Bcc:";
		String lineToRemove17 = "cc:";
		String lineToRemove18 = "----------------------";
		String lineToRemove19 = "	From;";
		//Add this line to resolve the extra lines from BCC and CCC (A constraint of the corpus)
		String linetoRemove20 = "@enron.com";
		//create a new arrayList to hold the email content after the metadata is filtered out.
		ArrayList<String> MetaDataFilterArrayList= new ArrayList<String>();
		try
		{
			File emailContainingMetadata = new File(emailSource);
			Reader fileReaderContainingMetadata = new FileReader(emailContainingMetadata);
			BufferedReader bufferedReaderContainingMetadata = new BufferedReader(fileReaderContainingMetadata);
			String lineMetadata = null;
			while ((lineMetadata = bufferedReaderContainingMetadata.readLine()) != null)
			{	if (	lineMetadata.startsWith(lineToRemove1) || lineMetadata.startsWith(lineToRemove2) || lineMetadata.startsWith(lineToRemove3) || 
						lineMetadata.startsWith(lineToRemove4) || lineMetadata.startsWith(lineToRemove5) || lineMetadata.startsWith(lineToRemove6) || 
						lineMetadata.startsWith(lineToRemove7) || lineMetadata.startsWith(lineToRemove8) || lineMetadata.startsWith(lineToRemove9) || 
						lineMetadata.startsWith(lineToRemove10) ||lineMetadata.startsWith(lineToRemove11) || lineMetadata.startsWith(lineToRemove12) || 
						lineMetadata.startsWith(lineToRemove13) || lineMetadata.startsWith(lineToRemove14) || lineMetadata.startsWith(lineToRemove15) ||
						lineMetadata.startsWith(lineToRemove16) || lineMetadata.startsWith(lineToRemove17) || lineMetadata.startsWith(lineToRemove18) ||
						lineMetadata.startsWith(lineToRemove18) || lineMetadata.endsWith(lineToRemove18) ||	lineMetadata.startsWith(lineToRemove19) ||
						lineMetadata.isEmpty()||lineMetadata.contains(linetoRemove20)	)	
				{	//then don't do anything with these lines if any of the above conditions are true for that line.
				}
				else	
				{
					//add the remaining lines to the ArrayList.
					MetaDataFilterArrayList.add(lineMetadata);
				}
			}
			bufferedReaderContainingMetadata.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return MetaDataFilterArrayList;
	}

	
	
	
	
	/**
	 * Load existing corpus of emails which is already converted into tokens
	 * 
	 * @param tokenDirectoryPath
	 * @return ArrayList<ArrayList<String>> which is an arrayList containing arrayLists of Strings
	 */
	public ArrayList<ArrayList<String>> getCorpusAsTokens(String tokenDirectoryPath)
	{
		ArrayList<ArrayList<String>>  corpusOfEmailsList = new ArrayList<ArrayList<String>>();
		final File localDirectoryNameObject = new File(tokenDirectoryPath);
		for (final File fileEntry : localDirectoryNameObject.listFiles())
		{
			corpusOfEmailsList.add(readInTokenizedWords(fileEntry));
		}
		return corpusOfEmailsList;
	}

	
	
	
	
	/**
	 * Read in tokens from single file
	 * 
	 * @param tokenizedEmail
	 * @return ArrayList<String> of tokenized words from external file
	 */
	public  ArrayList<String> readInTokenizedWords(File tokenizedEmail)
	{
		ArrayList<String> emailList = new ArrayList<String>();
		try
		{
			Reader fileReaderObject = new FileReader(tokenizedEmail);
			BufferedReader bufferedReaderObject = new BufferedReader(fileReaderObject);
			String line = null;
			
			while ((line = bufferedReaderObject.readLine()) != null)
			{ 
				emailList.add(line);
			} 
			bufferedReaderObject.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		return emailList;
	}
	
	
	
}
