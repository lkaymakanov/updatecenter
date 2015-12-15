package net.is_bg.gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuiServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4629808655920980976L;
	/*private String res = 
			" <!DOCTYPE html>\n"+
			" <html>\n"+
			" <head>\n"+
			" <meta charset=\"UTF-8\">\n"+
			" <title>Insert title here</title>\n"+
			" </head>\n"+
			" <body>\n"+
			" <object width=\"400\" height=\"400\" data=\"/pages/GuiApplet.class\"></object>\n"+
			" </body>\n"+
			" </html>\n";*/
	
	private InputStream findResource(String resourceName){
		return  getClass().getClassLoader().getResourceAsStream(resourceName);
	}
	
	private void sendInputStream(InputStream in, OutputStream os) throws IOException{
		try{
			byte [] b = new  byte [1024];
			while(in.read(b)!=-1){ os.write(b);}
		}finally{
			in.close();
			os.flush();
			os.close();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = req.getRequestURL().toString(); String context = req.getContextPath();
		System.out.println(url);
		System.out.println(context);
		String resource = url.split(context + "/")[1];
		//send to client
		sendInputStream(findResource(resource), resp.getOutputStream());
	}
}
