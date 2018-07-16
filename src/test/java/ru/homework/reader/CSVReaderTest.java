package ru.homework.reader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

@SpringBootTest(properties = {
		InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
	})
public class CSVReaderTest {
	
	@Rule
	public final TemporaryFolder tempFolder = new TemporaryFolder();
	
	@Test
	public void testParse() throws IOException {
		File csvFile = tempFolder.newFile("test.csv");
		String rows = "0;test0\r\n" + 
				      "test1;test2;test3;1;2;3;45.67;0;\r\n" + 
				      "test4;89;10000000";
		Files.write(csvFile.toPath(), rows.getBytes());
		CSVReader csvReader = new CSVReader();
		List<List<String>> parsedStringList = csvReader.parse(csvFile);
		assertTrue(parsedStringList.size()==3);
		assertTrue(parsedStringList.get(0).size()==2);
		assertTrue(parsedStringList.get(1).size()==8);
		assertTrue(parsedStringList.get(2).size()==3);
	}

}
