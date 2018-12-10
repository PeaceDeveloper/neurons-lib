package neurons.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.drools.process.core.WorkDefinition;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class FileUtil {
	
	public static String inputStreamToString(InputStream is) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    String line;
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    while ((line = br.readLine()) != null) {
	        sb.append(line);
	    }
	    br.close();
	    return sb.toString();
	}
	
	public static Object deserializeXmlFile(String pathXml, Class classe) throws FileNotFoundException, IOException{
		File file = new File(pathXml);
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(file));
        return xmlMapper.readValue(xml, classe);
	}
	
	public static Path getAbsolutPathOfPathFolder(String folder) throws IOException{
		return Paths.get(new File(".").getCanonicalPath() + "/" + folder);
	}
	
	public static void downloadFolder(String uri, String destino) throws IOException{
		URL url = new URL(uri);
	  ReadableByteChannel rbc = Channels.newChannel(url.openStream());
	  FileOutputStream fOutStream = new FileOutputStream(destino);
	  fOutStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	  fOutStream.close();
	  rbc.close();
	}
	
	public void copyFileFromResourceFile(String fileName, String destination) throws IOException{
		//Get file from resources folder
		InputStream in = getClass().getResourceAsStream(fileName); 
		BufferedInputStream bis = null;  
		BufferedOutputStream bos = null;  
		  
		try {  
			bis = new BufferedInputStream(in);  
			bos = new BufferedOutputStream(new FileOutputStream(destination));  
			int data;  
			while ((data = bis.read()) != -1) {  
				//System.out.println(data);  
				bos.write(data);  
			}
		} finally {  
			if (bis != null)  
				bis.close();  
			if (bos != null)  
				bos.close();  
		  }  
		
		
	}
	
	public static void rewriteJavaClass(String file, String newFile,
			String wortToReplace, String replaceTo) throws IOException{
		Path path = Paths.get(file);
		Charset charset = StandardCharsets.UTF_8;

		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(String.format("class %s",wortToReplace), String.format("class %s", replaceTo));
		content = content.replaceAll(String.format("public %s", wortToReplace),
				String.format("public %s", replaceTo));
		Files.write(Paths.get(newFile), content.getBytes(charset));
	}

	public static void deleteIfExistsFile(String path) throws IOException {
		if (Files.exists(Paths.get(path)))
			Files.delete(Paths.get(path));
	}
	
}
