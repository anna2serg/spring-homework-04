package ru.homework.common;

public final class Answer {
	private int id;
    private String variant;

    public Answer(int id, String variant) {
    	this.id = id;
        this.variant = variant;
    }

    public int getId() {
        return id;
    }    
    
    public String getVariant() {
        return variant;
    }
    
    public String toString() { 
    	return variant;  
    }     
    
}
