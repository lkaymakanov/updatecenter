package file.splitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Splits the file into Base64 encoded Strings out of a ISplitter!!!!
 * @author lubo
 *
 */
public final class StringChunkFileSplitter implements IFileSplitter {
	
	IFileSplitter splitter;
	List<String> fileChunks = new ArrayList<String>();
	

	public  StringChunkFileSplitter(IFileSplitter splitter){
		this.splitter = splitter;
	}

	@Override
	public void split() {
		// TODO Auto-generated method stub
		splitter.split();
		long chunks = splitter.getChunksNumber();
		
		for(int i = 0; i < chunks; i++){
			byte [] ch = splitter.getChunk(i);
			//convert to string
			String s = ch.toString();
			fileChunks.add(s);
			splitter.releaseChunk(i);
		}
	}

	@Override
	public long getChunksNumber() {
		// TODO Auto-generated method stub
		return fileChunks.size();
	}

	@Override
	public byte[] getChunk(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChunkSize(int number) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		fileChunks.clear();
		splitter.release();
	}

	@Override
	public void releaseChunk(int number) {
		// TODO Auto-generated method stub
		fileChunks.set(number, null);
	}
	
	public String getStringChunk(int number){
		return fileChunks.get(number);
	}
	
	
	
}
