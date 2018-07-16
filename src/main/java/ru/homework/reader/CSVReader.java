package ru.homework.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CSVReader {
	
    public List<List<String>> parse(File file) {
    	
    	InputStream inputStream = null;
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		if (!file.exists()) {
			inputStream = classloader.getResourceAsStream(file.getPath());		
		} else {
			try {
				inputStream =  new FileInputStream(file);
			} catch (FileNotFoundException e) {
				inputStream = null;
			}
		}
		
		if (inputStream==null) {
			System.out.println("Упс! Файл не найден. Надо бы проверить имя и путь к файлу в настройках программы");
			return null;			
		}			

		InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);

    	List<List<String>> result = new ArrayList<List<String>>();
    	
    	String row = "";
        String splitBy = ";";
        
        try {
			while ((row = reader.readLine()) != null) {
				String[] columns = row.split(splitBy);  
				
				result.add(new ArrayList<>(Arrays.asList(columns)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return result;
    }
    
}
