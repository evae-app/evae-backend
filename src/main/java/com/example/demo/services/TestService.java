package com.example.demo.services;

public class TestService {
	
	public static boolean isInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	public static boolean isStartWithNumber(String inputString) {
	    if (inputString != null && !inputString.isEmpty()) {
	        char firstChar = inputString.charAt(0);
	        if (Character.isDigit(firstChar)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static boolean intPositifNegatif(int number) {
	    if (number > 0) {
	        return true;
	    } else if (number < 0) {
	        return false;
	    } else {
	        return false;
	    }
	}
	
	public static boolean doublePositifNegatif(double number) {
	    if (number >= 0) {
	        return true;
	    } else if (number < 0) {
	        return false;
	    } else {
	        return false;
	    }
	}
	
	public static boolean isNumeric(String str) {
	    try {
	        Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}


}
