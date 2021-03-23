package org.aahri.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.aahri.model.MetaTables;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReadFiles{
	public static List<File> getXMLsFromResource(String folder) throws URISyntaxException, IOException{
		ClassLoader classLoader = ReadFiles.class.getClassLoader();
		URL resource = classLoader.getResource(folder);

		return Files.walk(Paths.get(Objects.requireNonNull(resource).toURI()))
				.filter(Files :: isRegularFile)
				.map(Path :: toFile)
				.collect(Collectors.toList());
	}

	public static InputStream getXMLFiles(String filename) throws IOException{
		ClassLoader classLoader = ReadFiles.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(filename);

		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + filename);
		} else {
			return inputStream;
		}
	}

	private static void printInputStream(InputStream is) {
		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
			 BufferedReader reader = new BufferedReader(streamReader)) {

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void marshal() throws IOException{
		XmlMapper mapper = new XmlMapper();
		MetaTables metaTables = mapper.readValue(getXMLFiles("data"), MetaTables.class);
		System.out.println(metaTables);
	}
	
	public static InputStream getXMLsFromResource(String folder) throws URISyntaxException, IOException{
		ClassLoader classLoader = ReadFiles.class.getClassLoader();
		URL resource = classLoader.getResource(folder);

		return (InputStream) Files.walk(Paths.get(Objects.requireNonNull(resource).toURI()))
				.filter(Files :: isRegularFile)
				.map(Path :: toFile)
				.collect(toList());
	}

	public static String getXMLs(String data) throws IOException{
		mapper = new XmlMapper();
		metaTables = mapper.readValue(data.getBytes(), MetaTables.class);

		System.out.println("POJOs " + metaTables);
		return "";
	}

	public static void marshal() throws IOException, URISyntaxException{
		XmlMapper xmlMapper = new XmlMapper();
		MetaTables metaTables = xmlMapper.readValue(getXMLsFromResource("data"), MetaTables.class);
		System.out.println("Metatable = " + metaTables);
		System.out.println("Mapper = " + xmlMapper);
	}

	public static void main(String[] args){
//		ReadFiles app = new ReadFiles();
		String folder = "data";

		try {
			System.out.println("using getResource()");
			List<File> result = getXMLsFromResource(folder);
			for(File file : result) {
				System.out.println("file: " + file);
			}

			System.out.println("==========");
			System.out.println("using getResourceAsStream()");

			InputStream inputStream = getXMLFiles(folder);
			printInputStream(inputStream);

			marshal();
		}catch(IOException | URISyntaxException e) {
			e.printStackTrace();
		}

	}
}
