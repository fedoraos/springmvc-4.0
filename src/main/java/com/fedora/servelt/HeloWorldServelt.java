package com.fedora.servelt;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeloWorldServelt  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1125682821268346898L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		PrintWriter printWriter;
		try {
			printWriter = response.getWriter();
			printWriter.println("<html>");
			printWriter.println("<head><title>helo,i'm linuxer</title></head>");
			printWriter.println("<body>");
			printWriter.println("<h1> hi, i am coding on linux</hi>");
			printWriter.println("</body>");
			printWriter.println("</html>");
			printWriter.checkError();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
