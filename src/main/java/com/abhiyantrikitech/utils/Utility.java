package com.abhiyantrikitech.utils;

import java.util.regex.Pattern;

public class Utility {

    // Mobile number validation 
	public static boolean isValidMobileNumber(String number) {
	    String regex = "^[0-9]{10}$"; 
	    return Pattern.matches(regex, number);
	}

    }

