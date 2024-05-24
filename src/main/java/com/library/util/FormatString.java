package com.library.util;

public class FormatString {

    public static String f (String string){
        String[] words = string.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(capitalizeFirstLetter(word)).append(" ");
        }
        String finalResult = result.toString().trim();
        return finalResult;
    }

    private static String capitalizeFirstLetter(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }


}
