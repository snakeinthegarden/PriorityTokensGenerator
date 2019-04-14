package nuig.prioritytokens.analyzer;

import static java.util.stream.Collectors.toMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MethodA_TFIDF implements GeneratePriorityTokens
{

	SortedMap<String, Double> priorityTokensMap_TFIDF = new TreeMap<String, Double>();
	
	private final static String priorityOutputPathAndFileName_TFIDF = "output/priority/TFIDF_Output.txt"; 
	
	
	
	
	
	/**
	 * Compare the content of singleEmail against the corpusOfEmails to calculate priority tokens for Method A
	 * This method is extended from interface GeneratePriorityTokens
	 * 
	 * @param singleEmailList
	 * @param corpusOfEmailsList
	 */
	@Override
	public void calculatePriorityTokens(ArrayList<String> singleEmailList, ArrayList<ArrayList<String>> corpusOfEmailsList)
	{
		//for each token in the ArrayList (i.e. the email) - calculate it's TDIDF score
		//output that score per token to a Map.
		for (String token: singleEmailList)
		{
			//create a type double to take in the result of the method call for the algorithm
			Double tfidfnew = tfIdf(singleEmailList, corpusOfEmailsList, token);

			//filter out results that are == 0 (i.e. the token isn't in the corpus)
			if (tfidfnew != 0.0)
			{
				//filter out results that are == Infinity (caused by mathematical output of diving by 0)
				if (!tfidfnew.isInfinite())
				{
					//add the remaining tokens and their calculated score to a sorted map to remove duplicate tokens
					priorityTokensMap_TFIDF.put(token, tfidfnew);
				}
			}
		}

		//assign the tokens in the 1st map to the 2nd map to sort it, and return the top tokens by their TDIDF score.
		//this sorts in order of highest TDIDF score (value) at the top for it's relevant token (key).
		Map<String, Double> priorityTokensSortedMap_TFIDF = priorityTokensMap_TFIDF
				.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.limit(15)
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));


		//write the Map to a .txt file by calling the below method
		writePriorityTokensToFileTFIDF(priorityTokensSortedMap_TFIDF);
		
		System.out.println("Method A complete - TFIDF: " + priorityTokensSortedMap_TFIDF.size() + " tokens extracted");
	}


	
	
	
	/**
	 * This returns a TFIDF score for each term in a document compared to a corpus of documents containing terms.
	 * 
	 * @param singleEmail
	 * @param corpusOfEmails
	 * @param term
	 * @return double value (TF-IDF score)
	 */
	public double tfIdf(ArrayList<String> singleEmail, ArrayList<ArrayList<String>> corpusOfEmails, String term) 
	{
		return tf(singleEmail, term) * idf(corpusOfEmails, term);
	}


	
	
	
	/**
	 * This method will determine the frequency of a term in the doc that contains it (passed as an arrayList)
	 * 
	 * @param singleEmail
	 * @param term
	 * @return a double value (term frequency score - TF)
	 */
	public double tf(ArrayList<String> singleEmail, String term)
	{
		double result = 0;
		for (String word : singleEmail) 
		{
			//compare 2 strings (ignoring case conditions for the strings)
			//increment result for each match between "term" and "word"
			if (term.equalsIgnoreCase(word))
			{
				result++;
			}
		}
		//TF = token frequency/total number of tokens in that document 
		return result / singleEmail.size();
	}


	
	
	
	/**
	 * This method will check all arrayLists and return the value of the document with the highest frequency of the term
	 * 
	 * @param corpusOfEmails
	 * @param term
	 * @return a double value (inverse document frequency - IDF)
	 */
	public double idf(ArrayList<ArrayList<String>> corpusOfEmails, String term)
	{
		double n = 0;
		for (ArrayList<String> doc : corpusOfEmails) 
		{
			for (String word : doc) 
			{
				//compare 2 strings (ignoring case conditions for the strings)
				if (term.equalsIgnoreCase(word)) 
				{
					n++;
					//add break in order to stop it adding all doc's n values together.
					//want to return the highest frequency of that word in a doc of the collection
					//without break, will just calculate the total frequency of the word.
					break;
				}
			}
		}
		//get log (N/n) where N is total # of documents and n  = frequency of token in documents.
		return Math.log(corpusOfEmails.size() / n);
	}
	
	
	
	
	
	/**
	 * Method to write the priority tokens to .txt files
	 * 
	 * @param priorityTokensSortedMap_TFIDF
	 */
	private static void writePriorityTokensToFileTFIDF(Map<String, Double> priorityTokensSortedMap_TFIDF) 
	{
		try 
		{ 
			FileWriter fileWriterOutputFile = new FileWriter(priorityOutputPathAndFileName_TFIDF);
			BufferedWriter bufferedWriterOutputFile = new BufferedWriter(fileWriterOutputFile);
			bufferedWriterOutputFile.write("The Priority tokens for this email are:"  + "\r\n");

			//using a FOR loop, write the top priority tokens to the .txt file line by line
			for (String token : priorityTokensSortedMap_TFIDF.keySet()) 
			{
				//access the key (the token) and the value (the score)
				bufferedWriterOutputFile.write(token + " = " + priorityTokensSortedMap_TFIDF.get(token) + "\r\n");
			}

			//output to console so can see that Method for writing the priority tokens has been completed
			System.out.println("The priority tokens for Method A - TF_IDF have been written to a .txt file");

			bufferedWriterOutputFile.close();
			fileWriterOutputFile.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	
	
}
