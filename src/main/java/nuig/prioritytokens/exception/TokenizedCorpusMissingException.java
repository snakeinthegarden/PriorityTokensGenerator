package nuig.prioritytokens.exception;

public class TokenizedCorpusMissingException extends Exception
{
	
	
	/**
	 * Generated serial Version UID.
	 */
	private static final long serialVersionUID = -8206731560116580056L;
	
	
	
	/**
	 * Exception for when Tokenized Corpus not found
	 * 
	 * @param errorMessage
	 */
	public TokenizedCorpusMissingException(String errorMessage) 
	{
        super(errorMessage);
    }
	
	
}
