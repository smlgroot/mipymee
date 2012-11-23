package com.kalimeradev.mipymee.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImagesServletImpl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Set content type
		resp.setContentType("image/gif");

		// Up to you!
		byte[] binaryData = getDataFromDataStore();

		ByteArrayInputStream bis = new ByteArrayInputStream(binaryData);
		OutputStream out = resp.getOutputStream();

		// Copy the contents of the file to the output stream
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = bis.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		bis.close();
		out.close();
	}

	private byte[] getDataFromDataStore() {
		
		return null;
	}
}
