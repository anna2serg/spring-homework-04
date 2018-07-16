package ru.homework.common;

public final class TestStatUnit {
	private final boolean isPassed;
    private final int percent;

    public TestStatUnit(boolean isPassed, int percent) {
    	this.isPassed = isPassed;
    	this.percent = percent;
    }
    
    public boolean getIsPassed() {
        return isPassed;
    }    
    
    public int getPercent() {
        return percent;
    }
}
