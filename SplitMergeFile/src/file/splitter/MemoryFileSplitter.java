package file.splitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MemoryFileSplitter implements IFileSplitter{
	
	private String fileName;
	private File file;
	private int chunkSize;
	private long fileSize;
	private boolean splitted = false;
	private List<ByteChunk>  fileChunks = new ArrayList<ByteChunk>();
	
	
	public MemoryFileSplitter(String fname, int chunkSize){
		this.fileName = fname;
		this.file = new File(fname);
		this.chunkSize = chunkSize;
		this.fileSize = file.length();
	}
	
	public MemoryFileSplitter(File file, int chunkSize){
		this.file = file;
		this.chunkSize = chunkSize;
		this.fileSize = file.length();
	}
	

	@Override
	public byte[] getChunk(int number) {
		// TODO Auto-generated method stub
		return fileChunks.get(number).buffer;
	}
	
	
	@Override
	public long getChunksNumber() {
		// TODO Auto-generated method stub
		return fileChunks.size();
	}
	
	
	@Override
	public void split() {
		// TODO Auto-generated method stub
		if(splitted) return; 
		//read the file & fill the buffers
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			ByteChunk c = null;
			do
			{
				byte [] arr = new byte [chunkSize];  //allocate memory for each chunk
				c = new ByteChunk();
				c.size = in.read(arr);
				c.buffer = arr;
				if(c.size > 0){
					if(c.size <  chunkSize) c.buffer = shrinkBuffer(c.buffer, c.size);    //shrinks the buffer if its size is less than chunksize!!!
					fileChunks.add(c);
				}
			}while(c.size > 0);
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		printChunkTable();
		splitted = true;
	}
	
	@Override
	public long getChunkSize(int number) {
		// TODO Auto-generated method stub
		return fileChunks.get(number).size;
	}
	
	
	public String getFileName() {
		return fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	
	@Override
	public void release() {
		// TODO Auto-generated method stub
		fileChunks.clear();
	}
	
	
	@Override
	public void releaseChunk(int number) {
		// TODO Auto-generated method stub
		fileChunks.set(number, null);
	}
	
	
	/***
	 * Shrinks the buffer to the number of bytes specified by shrinkTo param!!!
	 * @param buffer
	 * @param shrinkTo
	 * @return
	 */
	private byte [] shrinkBuffer(byte [] buffer, int shrinkTo){
		if(buffer.length <= shrinkTo)  return buffer;
		return Arrays.copyOf(buffer, shrinkTo);
	}
	
	private void printChunkTable(){
		System.out.println("===============   CHUNK TABLE FOR FILE " + file.getName() + "  =================");
		int i = 0;
		for(ByteChunk c: fileChunks){
			System.out.println(" Chunk number " + i + " -  size " + c.buffer.length + " bytes... ");
			i++;
		}
		System.out.println("============================ END OF CHUNK TABLE ================================");
	}
	
}
