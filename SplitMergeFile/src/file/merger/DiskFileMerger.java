package file.merger;

/**
 * <pre>
 * Merges files on disk into one file!!!
 * The file chunks must be in the same directory!!!
 * @author lubo
 *</pre
 */
public class DiskFileMerger implements IFileMerger{
	
	private String inputDir;
	private String outFile;
	private String fileChunksxRegex;
	
	/**
	 * 
	 * @param inputDir - the input chunk directory
	 * @param outFile  - the path & name to output file
	 * @param fileExt - the extension of the merged file
	 * @param fileSuffixRegex the reg ex describing file chunks
	 */
	public DiskFileMerger(String inputDir, String outFile, String fileExt, String fileChunksxRegex){
		this.inputDir = inputDir;
		this.outFile = outFile;
		this.fileChunksxRegex = fileChunksxRegex;
	}
	

	@Override
	public void merge() {
		// TODO Auto-generated method stub
		
	}

}
