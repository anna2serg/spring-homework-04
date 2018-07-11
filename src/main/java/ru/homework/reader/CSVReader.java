package ru.homework.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CSVReader {
	
    public List<List<String>> parse(BufferedReader file) {
    	List<List<String>> result = new ArrayList<List<String>>();
    	
    	String row = "";
        String splitBy = ";";
        
        try {
			while ((row = file.readLine()) != null) {
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
