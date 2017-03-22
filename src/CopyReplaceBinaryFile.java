package java1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class CopyReplaceBinaryFile {
	



    public static void main(String[] args) throws IOException {

    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\BSEG20121.TXT", "C:\\HOME\\ftpuser\\BSEG20121#.TXT");
    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\BSEG20122.TXT", "C:\\HOME\\ftpuser\\BSEG20122#.TXT");
    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\BSEG20123.TXT", "C:\\HOME\\ftpuser\\BSEG20123#.TXT");
    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\BSEG20124.TXT", "C:\\HOME\\ftpuser\\BSEG20124#.TXT");
    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\BSEG20131.TXT", "C:\\HOME\\ftpuser\\BSEG20131#.TXT");
    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\BSEG20132.TXT", "C:\\HOME\\ftpuser\\BSEG20132#.TXT");
    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\BSEG20133.TXT", "C:\\HOME\\ftpuser\\BSEG20133#.TXT");
    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\BSEG20134.TXT", "C:\\HOME\\ftpuser\\BSEG20134#.TXT");
    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\BSEG20141.TXT", "C:\\HOME\\ftpuser\\BSEG20141#.TXT");        
    	CopyReplaceBinaryFile.copy("C:\\HOME\\ftpuser\\AMBN06362\\BSAK.TXT", "C:\\HOME\\ftpuser\\AMBN06362\\BSAK#.TXT");        
 
    }
    
    public static void copy(String in, String out) throws FileNotFoundException, IOException {
        try (

                //Open the input and out files for the streams
                InputStream fileInputStream = new BufferedInputStream(new FileInputStream(in));
                FileOutputStream fileOutputStream = new FileOutputStream(out);
        )  
        {
                //Read data into buffer and then write to the output file
                byte[] buffer = new byte[1024];                    
                int bytesRead;                                    
                while ((bytesRead = fileInputStream.read(buffer)) != -1)     
                {     
                    for (int i = 0; i < bytesRead; ++i) {
                    	if (buffer[i] == '"') buffer[i] = '#';
                    }
                	fileOutputStream.write(buffer, 0, bytesRead);           
                }  

        }//try-with-resource    

        System.out.println(in + " copied to " + out);
    }

}