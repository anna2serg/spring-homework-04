package ru.homework.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUnit {
	
    private String question;
    private List<Answer> answerList;
    private List<Integer> rightAnswers;

    public TestUnit() {
    	answerList = new ArrayList<Answer>();
    	rightAnswers = new ArrayList<Integer>();
    }
    
    public String getQuestion() {
        return question;
    }    
    
    public void setQuestion(String question) {
        this.question = question;
    }      
    
    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answerList);
    }	       
    
    public void addAnswer(Answer answer) {
        answerList.add(answer);
    }	 
    
    public void setRightAnswers(List<Integer> rightAnswers) {
    	this.rightAnswers.clear();
    	this.rightAnswers.addAll(rightAnswers);
    }	    
    
	public boolean isMultiChoice() {
		return rightAnswers.size()>1;
	}	   
	
	public boolean isRightAnswers(List<Integer> userAnswers) {
		return (userAnswers.containsAll(rightAnswers) 
				&& rightAnswers.containsAll(userAnswers));		
	}
       
}
