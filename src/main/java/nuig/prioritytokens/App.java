package nuig.prioritytokens;

import nuig.prioritytokens.exception.TokenizedCorpusMissingException;
import nuig.prioritytokens.service.MyProjectService;

public class App 
{

	/**
	 * main method to run project
	 * If the arg is = "loadCorpus" it will generate the corpus
	 * Else it will search for a previously generated corpus
	 * However, if no prior tokenized corpus exists, an error will be thrown.
	 * 
	 * @param args
	 */
	public static void main( String[] args )
	{
		boolean reloadTokenizeCorpus = false;    
		
		if (args.length > 0) 
		{
			if ("loadCorpus".equals(args[0])) 
			{
				reloadTokenizeCorpus = true;    	
			}
		}

		System.out.println(" - Starting project - ");
		System.out.println("");

		//call the master service class constructor to call all the service class objects.
		MyProjectService myProjectService = new MyProjectService();

		//load the Single email
		myProjectService.loadSingleEmail();
		System.out.println("");

		//Load the tokenized corpus
		myProjectService.loadTokenizedCorpus(reloadTokenizeCorpus);
		System.out.println("");

		try 
		{
			//run MethodA
			myProjectService.runMethodA_TFIDF();
		} 
		catch (TokenizedCorpusMissingException e) 
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		System.out.println("");
		
		try 
		{
			//run MethodB
			myProjectService.runMethodB_Dictionary();
		} 
		catch (TokenizedCorpusMissingException e) 
		{
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
	}
	
	
}
