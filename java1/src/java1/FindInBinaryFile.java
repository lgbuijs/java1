package java1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class FindInBinaryFile {
	



    public static void main(String[] args) throws IOException {

    	FindInBinaryFile.copy("C:\\HOME\\ftpuser\\AMBN06362\\CDPOS.TXT", "C:\\HOME\\ftpuser\\AMBN06362\\CDPOS_LFBK.TXT");
    }
    
    public static void copy(String in, String out) throws FileNotFoundException, IOException {
        try (

                //Open the input and out files for the streams
                InputStream fileInputStream = new BufferedInputStream(new FileInputStream(in));
                FileOutputStream fileOutputStream = new FileOutputStream(out);
        )  
        {
                int c = 0;
                //Read data into buffer and then write to the output file
                byte[] buffer = new byte[1024];                    
                int bytesRead;                                    
                while ((bytesRead = fileInputStream.read(buffer)) != -1)     
                {     
                    
                	for (int i = 0; i < bytesRead; ++i) {
                		c = (c == 0 && buffer[i] == 'L') ? 1 : 
                				(c == 1 && buffer[i] == 'F') ? 2  : 
                					(c == 2 && buffer[i] == 'B') ? 3  : 
                						(c == 3 && buffer[i] == 'K') ? 4 : 0;
                		if (c == 4) {
                			fileOutputStream.write(buffer, 0, bytesRead);
                			c = 0;
                		}
                	}
                }  

        }//try-with-resource    

        System.out.println(in + " copied to " + out);
    }

}