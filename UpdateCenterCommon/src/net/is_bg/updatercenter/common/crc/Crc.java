package net.is_bg.updatercenter.common.crc;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/***
 * Calculates the Crc32 hash code of a file!!!
 * @author lubo
 *
 */
public class Crc {

	/**
	 * Calculates the Crc32 hash code of a file! Input Stream method!!!
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static long checksumInputStream(String filepath) throws IOException {
		  InputStream inputStreamn = new FileInputStream(filepath);
		
		  CRC32 crc = new CRC32();
		  int cnt;
		  while ((cnt = inputStreamn.read()) != -1) {
			  crc.update(cnt);
		  }
		  inputStreamn.close();
		  return crc.getValue();
	}
				
	 
	/***
	 * Calculates the Crc32 hash code of a file! Buffered Input Stream method!
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static long checksumBufferedInputStream(String filepath) throws IOException {
				
	  InputStream inputStream = new BufferedInputStream(new FileInputStream(filepath));
	  CRC32 crc = new CRC32();
	
	
	  int cnt;
	
	  while ((cnt = inputStream.read()) != -1) {
		  	crc.update(cnt);
	  }
	  inputStream.close();
	  return crc.getValue();
	}

	    
	/**
	 * Calculates the Crc32 hash code of a file! Random Access File method!
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static long checksumRandomAccessFile(String filepath) throws IOException {

	  RandomAccessFile randAccfile = new RandomAccessFile(filepath, "r");
	  long length = randAccfile.length();

	  CRC32 crc = new CRC32();

	  for (long i = 0; i < length; i++) {
		  	randAccfile.seek(i);
		  	int cnt = randAccfile.readByte();
		  	crc.update(cnt);

	  }
	  randAccfile.close();
	  return crc.getValue();
	 }

	
	/**
	 *  Calculates the Crc32 hash code of a file! Mapped File method!
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	 public static long checksumMappedFile(String filepath) throws IOException {

		  FileInputStream inputStream = new FileInputStream(filepath);
		  FileChannel fileChannel = inputStream.getChannel();
	
	
		  int len = (int) fileChannel.size();
	
	
		  MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, len);
		  CRC32 crc = new CRC32();
	
	
		  for (int cnt = 0; cnt < len; cnt++) {
			  	int i = buffer.get(cnt);
			  	crc.update(i);
	
		  }
		  inputStream.close();
		  fileChannel.close();
		  return crc.getValue();
	  }

	  
	 /***
	  * Calculates the check sum of a zip File!
	  * @param filepath
	  * @return
	  */
	 public static long checksumZip(String filepath){
		 ZipFile zipFile = null;
            long crc = 0;
			try {

				// open a zip file for reading
				zipFile = new ZipFile(filepath);

				// get an enumeration of the ZIP file entries
				Enumeration<? extends ZipEntry> e = zipFile.entries();

				while (e.hasMoreElements()) {
					long crcCurrent = 0;
					ZipEntry entry = e.nextElement();

					// get the name of the entry
					String entryName = entry.getName();
					
					// get the CRC-32 checksum of the uncompressed entry data, or -1 if not known
					crcCurrent = entry.getCrc();
					
					//additional modification
					crc = ((crc + crcCurrent) << 32) ^ (crcCurrent >> 32);

					System.out.println(entryName + " with CRC-32: " + crcCurrent);

				}

			}
			catch (IOException ioe) {
				System.out.println("Error opening zip file" + ioe);
			}
			 finally {
				 try {
					 if (zipFile!=null) {
						 zipFile.close();
					 }
				 }
				 catch (IOException ioe) {
						System.out.println("Error while closing zip file" + ioe);
				 }
			 }
			return crc;
	 }
	 
	  
	 
	 /***
	  * Test function!!!
	  * @param args
	  * @throws IOException
	  */
	 public static void main(String[] args) throws IOException {


	  String orgfilepath = "D:\\updatecenterroot\\LTF-1.2-6866.war";
	  String receivedFilePath = "D:\\updatecenter_test\\LTF-1.2-6866.war";
	  
	  System.out.println("Original file CRC = " + checksumBufferedInputStream(orgfilepath));
	  System.out.println("Received file CRC = " + checksumBufferedInputStream(receivedFilePath));
	  /*
	  System.out.println("Input Stream method:");

	  long start_timer = System.currentTimeMillis();

	  long crc = checksumInputStream(filepath);

	  long end_timer = System.currentTimeMillis();


	  System.out.println(Long.toHexString(crc));

	  System.out.println((end_timer - start_timer) + " ms");

	  System.out.println("///////////////////////////////////////////////////////////");


	  System.out.println("Buffered Input Stream method:");

	  start_timer = System.currentTimeMillis();

	  crc = checksumBufferedInputStream(filepath);

	  end_timer = System.currentTimeMillis();

	  System.out.println(Long.toHexString(crc));

	  System.out.println((end_timer - start_timer) + " ms");


	  System.out.println("///////////////////////////////////////////////////////////");


	  System.out.println("Random Access File method:");

	  start_timer = System.currentTimeMillis();

	  crc = checksumRandomAccessFile(filepath);

	  end_timer = System.currentTimeMillis();

	  System.out.println(Long.toHexString(crc));

	  System.out.println((end_timer - start_timer) + " ms");


	  System.out.println("///////////////////////////////////////////////////////////");


	  System.out.println("Mapped File method:");

	  start_timer = System.currentTimeMillis();

	  crc = checksumMappedFile(filepath);

	  end_timer = System.currentTimeMillis();

	  System.out.println(Long.toHexString(crc));

	  System.out.println((end_timer - start_timer) + " ms");
	   */
	 }

}
