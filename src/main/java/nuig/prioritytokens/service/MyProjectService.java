package nuig.prioritytokens.service;

import java.io.File;
import java.util.ArrayList;

import nuig.prioritytokens.analyzer.MethodA_TFIDF;
import nuig.prioritytokens.analyzer.MethodB_Dictionary;
import nuig.prioritytokens.exception.TokenizedCorpusMissingException;
import nuig.prioritytokens.filereader.EmailReaderService;
import nuig.prioritytokens.tokenizer.TokenizerService;
import nuig.prioritytokens.util.UtilService;

public class MyProjectService 
{

	//File locations
	private final static String singleEmailSource = "input/single/20";	
	private final static String outputSingleEmail = "output/single/20_cleanedReadyForAlgorithm.txt";
	private final static String outputCorpusOfEmails = "output/directory";


	//Service classes
	EmailReaderService emailReaderService;	
	TokenizerService tokenizerService;
	MethodA_TFIDF tfidfAnalyzerService;
	MethodB_Dictionary dictionaryAnalyzerService;

	//List to store data while app running
	ArrayList<String> stopWordsList;	

	//Tokenized Lists
	ArrayList<String> singleEmailList;
	ArrayList<ArrayList<String>> corpusOfEmailsList;


	/**
	 * Generate an object of this class by calling the constructor
	 * The constructor generates the other service objects and the stop words list
	 * 
	 */
	public MyProjectService() 
	{
		emailReaderService = new EmailReaderService();		
		tokenizerService = new TokenizerService();
		tfidfAnalyzerService = new MethodA_TFIDF();
		dictionaryAnalyzerService = new MethodB_Dictionary();

		stopWordsList = emailReaderService.getStopWords();
		System.out.println("Stop words loaded into program : " + stopWordsList.size());
	}





	/**
	 * Load single email from file system
	 * 
	 */
	public void loadSingleEmail() 
	{
		ArrayList<String> emailContentAsArrayList = EmailReaderService.getEmail(singleEmailSource);
		System.out.println("Single Email Read into Program - email word count: " + emailContentAsArrayList.size());
		singleEmailList = tokenizerService.tokenizeEmail(	UtilService.convertForTokenization(emailContentAsArrayList), stopWordsList, outputSingleEmail);

	}




	/**
	 * load tokenized corpus into Program
	 * 
	 * @param reloadTokenizeCorpus sets whether we will reload the corpus from the raw data
	 */
	public void loadTokenizedCorpus(boolean reloadTokenizeCorpus) 
	{
		if(reloadTokenizeCorpus) 
		{
			corpusOfEmailsList = new ArrayList<ArrayList<String>>();

			ArrayList<String> returnedDirectoryList = EmailReaderService.getDirectoryFiles();

			System.out.println(" - generating the tokenized corpus of emails - ");

			for (int i = 0; i< returnedDirectoryList.size(); i++)
			{
				String getFileName = returnedDirectoryList.get(i);
				String outputFileName = ((new File(getFileName)) + "_cleaned.txt").replace("\\", "_").replace(":", "");
				ArrayList<String> emailContentAsList = EmailReaderService.getEmail(getFileName);
				corpusOfEmailsList.add(tokenizerService.tokenizeEmail(	UtilService.convertForTokenization(emailContentAsList), stopWordsList, outputCorpusOfEmails + "\\" +outputFileName));
			}
			System.out.println("The corpus contained " + corpusOfEmailsList.size() + " emails.");
		}
		else 
		{
			System.out.println(" - reloading the tokenized corpus of emails - ");
			corpusOfEmailsList = emailReaderService.getCorpusAsTokens(outputCorpusOfEmails);
			System.out.println("The corpus contained " + corpusOfEmailsList.size() + " emails.");
		}
	}





	/**
	 * Run TFIDF analysis on the singleEmailList by comparing it to corpusOfEmailsList
	 * 
	 * @throws TokenizedCorpusMissingException
	 */
	public void runMethodA_TFIDF() throws TokenizedCorpusMissingException 
	{		
		if( corpusOfEmailsList.size() == 0 )
		{
			throw new TokenizedCorpusMissingException("Tokenized Corpus is empty. Analysis cannot be carried out.");
		}	
		else
		{
			System.out.println(" - Running Method A - TFIDF Analysis - ");
			tfidfAnalyzerService.calculatePriorityTokens(singleEmailList, corpusOfEmailsList);
		}
	}




	/**
	 * Run Dictionary analysis on the singleEmailList by comparing it to corpusOfEmailsList
	 * 
	 * @throws TokenizedCorpusMissingException
	 */
	public void runMethodB_Dictionary() throws TokenizedCorpusMissingException 
	{
		if( corpusOfEmailsList.size() == 0 )
		{
			throw new TokenizedCorpusMissingException("Tokenized Corpus is empty. Analysis cannot be carried out.");
		}	
		else
		{
			System.out.println(" - Running Method B - Dictionary Analysis - ");
			dictionaryAnalyzerService.calculatePriorityTokens(singleEmailList, corpusOfEmailsList);
		}
	}




}
