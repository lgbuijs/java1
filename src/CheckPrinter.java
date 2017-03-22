package java1;

import java.io.*;
import java.net.*;

public class CheckPrinter {
	/**
	 * Query the state of the print queue.
	 *
	 * @param host Internet host name or IP address.
	 * @param queue The name of the queue (traditionally the username).
	 * @param out results of the request are written here.
	 * @param shortResponse true if a short response is desired, false if a long response is desired.
	 * @throws IOException if communication with the printer fails.
	 */
	static boolean checkLPD(String host, String queue, OutputStream out, boolean shortResponse) {
		try {
			Socket socket = new Socket(host, 515);
			socket.setSoTimeout(10);
			OutputStream sout = socket.getOutputStream(); 
			sout.write(new byte[] {(byte)(shortResponse?3:4)});
			sout.write(queue.getBytes());
			sout.write(" List\n".getBytes());
			sout.flush();
			
			InputStream sin = socket.getInputStream();
			byte[] cbuffer = new byte[1024];
			int read;
			while ((read = sin.read(cbuffer)) != -1){
				out.write(cbuffer, 0, read);
			}
			out.flush();
			sout.close();
			sin.close();
			socket.close();
			return(true);
		}
		catch (Exception ex) {
	          return(false);
	    }
	}
	static boolean checkRawPort(String host) {
		try {
			Socket socket = new Socket(host, 9100);
			OutputStream sout = socket.getOutputStream(); 
			sout.flush();
			sout.close();
			socket.close();
			return(true);
		}
		catch (Exception ex) {
	          return(false);
	    }
	}
	public static void main(String args[]) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
			/* boolean s; */

		FileReader in = new FileReader("C:\\Temp\\CheckPrinter\\printers.txt");
	    BufferedReader br = new BufferedReader(in);

	    String printer;
	    while ((printer = br.readLine()) != null) {
	        String[] parmlist = printer.split("\t");
	        out.write(parmlist[2].getBytes());
	        out.write("\r\n".getBytes());
	        System.out.println(parmlist[2] + "\t" + (checkLPD(parmlist[2], "RAW", out, true) ? "true" : "false"));
	        out.write("\r\n".getBytes());
	    }
	    System.out.println();
	    System.out.print(out.toString());
	    in.close();   
	}
}
