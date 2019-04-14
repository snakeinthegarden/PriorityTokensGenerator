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

public class MethodB_Dictionary implements GeneratePriorityTokens
{

	SortedMap<String, Double> tokenDictionaryMapWithFrequency = new TreeMap<String, Double>();
	SortedMap<String, Double> singleEmailWithDictionaryFrequencyMap = new TreeMap<String, Double>();	 
	SortedMap<String, Integer> tokenDictionaryMapWithCount = new TreeMap<String, Integer>();

	private final static String priorityOutputPathAndFileName_Dictionary = "output/priority/Dictionary_output.txt"; 

	static double totalSetTokenCount = 0;





	/**
	 * Compare the content of singleEmail against the corpusOfEmails to calculate priority tokens for Method B
	 * This method is extended from interface GeneratePriorityTokens
	 * 
	 * @param singleEmailList
	 * @param corpusOfEmailsList
	 */
	@Override
	public void calculatePriorityTokens(ArrayList<String> singleEmailList, ArrayList<ArrayList<String>> corpusOfEmailsList) 
	{
		populateTokenDictionaryMapWithCount(corpusOfEmailsList);

		for (String tokenInDictionary : tokenDictionaryMapWithCount.keySet()) 
		{
			// count all tokens in the Set and return to a int value
			totalSetTokenCount = totalSetTokenCount += tokenDictionaryMapWithCount.get(tokenInDictionary);

			//generate the ratio of the token to the frequency of the token in the dictionary
			int uniqueTokenCount = tokenDictionaryMapWithCount.get(tokenInDictionary);
			double uniqueTokenFrequency = (uniqueTokenCount/totalSetTokenCount);

			//put each token and it's new frequency value into a new Map
			tokenDictionaryMapWithFrequency.put(tokenInDictionary, uniqueTokenFrequency);
		}


		for (String tokenInEmail: singleEmailList)
		{
			//if the dictionary contains a token in the email, put it into a new map
			//else, ignore (i.e. it's not a priority token if it is not in the dictionary)
			if (tokenDictionaryMapWithFrequency.containsKey(tokenInEmail))
			{
				String tokenToAdd = tokenInEmail;
				Double scoreToAdd = tokenDictionaryMapWithFrequency.get(tokenInEmail);
				//the new map holds the unsorted priority tokens from the single email.
				singleEmailWithDictionaryFrequencyMap.put(tokenToAdd, scoreToAdd);
			}
		}


		//assign the tokens in the 1st map to the 2nd map to sort it, and return the top 15 tokens by their score.
		//this sorts in order of highest score (value) at the top for it's relevant token (key).
		Map<String, Double> priorityTokensSortedMap_Dictionary = singleEmailWithDictionaryFrequencyMap
				.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.limit(15)
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));


		//write the Map to a .txt file by calling the below method
		writePriorityTokensToFileDictionary(priorityTokensSortedMap_Dictionary);

		System.out.println("Method B complete - Dictionary: " + priorityTokensSortedMap_Dictionary.size() + " tokens extracted");
	}




	/**
	 * populate token dictionary with the content from the corpusOfEmails
	 * 
	 * @param corpusOfEmailsList
	 */
	private void populateTokenDictionaryMapWithCount(ArrayList<ArrayList<String>> corpusOfEmailsList) 
	{
		for(int i = 0; i < corpusOfEmailsList.size(); i ++ ) 
		{
			ArrayList<String> emailInCorpus = corpusOfEmailsList.get(i);

			for(int j = 0; j < emailInCorpus.size(); j++) 
			{
				String tokenInEmail = emailInCorpus.get(j);

				if (tokenDictionaryMapWithCount.containsKey(tokenInEmail))
				{
					//if present, then increment the counter of the key that is passed.
					//.get() will return the int based on this hashmap setup as the int is the value and the token is the key
					tokenDictionaryMapWithCount.put(tokenInEmail, tokenDictionaryMapWithCount.get(tokenInEmail)+ 1);
				}
				else
				{
					//else, assign it a counter of 1 and add the token to the list.
					tokenDictionaryMapWithCount.put(tokenInEmail, 1);
				}
			}
		}
	}


	/**
	 * Method to write the priority tokens to .txt files
	 * 
	 * @param priorityTokensSortedMap_Dictionary
	 */
	private static void writePriorityTokensToFileDictionary(Map<String, Double> priorityTokensSortedMap_Dictionary) 
	{
		try 
		{ 
			FileWriter fileWriterOutputFile = new FileWriter(priorityOutputPathAndFileName_Dictionary);	
			BufferedWriter bufferedWriterOutputFile = new BufferedWriter(fileWriterOutputFile);
			bufferedWriterOutputFile.write("The Priority tokens for this email are:"  + "\r\n");

			//using a FOR loop, write the top priority tokens to the .txt file line by line
			for (String token : priorityTokensSortedMap_Dictionary.keySet()) 
			{
				//access the key (the token) and the value (the score)
				bufferedWriterOutputFile.write(token + " = " + priorityTokensSortedMap_Dictionary.get(token) + "\r\n");
			}

			//output to console so can see that Method for writing the priority tokens has been completed
			System.out.println("The priority tokens for Method B - Dictionary have been written to a .txt file");

			bufferedWriterOutputFile.close();
			fileWriterOutputFile.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


}
