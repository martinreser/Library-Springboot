package com.author.service.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UtilClass {

    public static String formatString(String string){
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

    /**
     * Checks if a word is valid (not null and not empty).
     * @param string the input word
     * @return true if the word is valid, false otherwise
     */
    public static boolean isValidString(String string){
        if (string == null || string.equals(""))
            return false;
        return true;
    }

    /**
     * Checks if a word is valid (not null, not empty, and does not contain digits).
     * @param string the input word
     * @return true if the word is valid, false otherwise
     */
    public static boolean isValidWord(String string){
        if (string == null || string.equals("") || string.matches(".*\\d+.*"))
            return false;
        return true;
    }

    /**
     * Checks if a year is valid (a valid integer within the range of years).
     * @param yearRelease the year as a string
     * @return true if the year is valid, false otherwise
     */
    public static boolean isValidYear(String yearRelease){
        int nowYear = LocalDate.now().getYear();
        int year = 0;
        try {
            year = Integer.parseInt(yearRelease);
        }
        catch (NumberFormatException n){}
        if (year > nowYear || year < 0) return false;
        return true;
    }

    /**
     * Validates and parses a date string in the format yyyy-MM-dd.
     * @param date the date as a string
     * @return the LocalDate object if the date is valid, null otherwise
     */
    public static LocalDate formatDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }

        try {
            String[] dates = date.split("-");
            if (dates.length != 3 || !isValidYear(dates[0])) {
                return null;
            }

            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            final LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

            if (!localDate.format(dateTimeFormatter).equals(date)) {
                return null;
            }

            return localDate;
        } catch (DateTimeParseException e) {
            return null;
        }
    }


}
