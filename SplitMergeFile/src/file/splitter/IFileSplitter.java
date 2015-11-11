package file.splitter;

/**
 * The main interface for splitting files either in memory or on the disk!!!
 * @author lubo
 *
 */
public interface IFileSplitter {

	/**
	 * A method that splits the file into chunks!!!
	 */
	public void split();
	
	
	/**
	 * The number of chunks that the file is split into!!!
	 * @return
	 */
	public long getChunksNumber();
	
	
	
	/***
	 * Gets the chunk number!!!
	 * @param number - the zero based index of the requested chunk!!!
	 * @return
	 */
	public byte[] getChunk(int number);
	
	
	/***
	 * The chunk size in bytes of chunk with index equal to number!!!!
	 * @return
	 */
	public long getChunkSize(int number);
	
	
	/**
	 * Releases the resource associated with this splitter!!!
	 */
	public void release();
	
	/**
	 * Release the resources associated with the chunk number!! 
	 * @param number
	 */
	public void releaseChunk(int number);
	
	
}
