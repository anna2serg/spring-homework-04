package ru.homework.dao;

import ru.homework.common.TestUnit;

public interface TestBoxDao {

	boolean isEOF(); 
	int count();  
	TestUnit nextTest();
	TestUnit getTest();
	
}