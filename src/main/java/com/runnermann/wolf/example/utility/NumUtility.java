package com.runnermann.wolf.example.utility;

import java.util.Random;

public class NumUtility {

    public NumUtility() {/* no args constructor */}

    public String randomAlphaNumeric(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 90; // letter 'Z'
        int targetStringLength = length;
        Random random = new Random();
        // parallel overhead is not faster.
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /*public static void main(String[] args) {
        NumUtility u = new NumUtility();

        long start = System.nanoTime();
        String num = u.randomAlphaNumeric(8);
        long end = System.nanoTime();
        System.out.println(num);
        System.out.println("Total millis: " + (end - start));
    }*/
}
