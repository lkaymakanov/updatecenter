package test;

import java.io.IOException;

import version.LtfVersionDescription;







public class Test {

	public static void main(String [] test) throws IOException{
		
		LtfVersionDescription des = new LtfVersionDescription();
		
		System.out.println(des.getVersionInfo());
		
		/*Session s = null;
		do{
		s=	SessionRegister.getSession(null, true);
			System.out.println("Session id = " +  s.getSessionId());
		}
		while(s!=null && s.getStatus() == SESSION_STATUS.ACTIVE);*/
		
	}
}
