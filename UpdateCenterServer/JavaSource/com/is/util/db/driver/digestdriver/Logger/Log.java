package com.is.util.db.driver.digestdriver.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Logs all errors to STDOUT and in digestDriverErrors sub-directory of the working directory 
 * Each error is being logged only once, in file named E concatenated with MD5 sum of error's text 
 */
public class Log {

	private static final Logger LOG = Logger.getLogger("DigestDriver");
	private static final String LOG_DIRECTORY="digestDriverErrors"; 
	
	public static void convErr(Throwable t, String parameter) {
//		LOG.throwing("Log", "convErr", t);
		
		
//		System.out.println("----------------------------------------------------------------------");
//		t.printStackTrace();
//		System.out.println("======================================================================");
		
		try {
			File logDir = new File(LOG_DIRECTORY);
			if (!logDir.exists()) logDir.mkdir(); 
			
			String exceptionText = getExceptionText(t)+" Parameter="+parameter;
			String hash = MD5(exceptionText);
			String fileName="E"+hash;
			File logFile = new File(LOG_DIRECTORY+File.separator+fileName);
			
			if (!logFile.exists()) {
				FileWriter logFileWriter = new FileWriter(logFile);
				logFileWriter.write(exceptionText);
				logFileWriter.flush();
				logFileWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
	
	private static String getExceptionText(Throwable t) {
		StringBuffer result = new StringBuffer();
		result.append(t.getMessage());
		
		for(int i=0; i<t.getStackTrace().length; i++) {
			result.append("\n");
			result.append(t.getStackTrace()[i].toString());
		}
		
		return result.toString();
	}
	
	
	private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
 
    public static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("utf8"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    } 

}
