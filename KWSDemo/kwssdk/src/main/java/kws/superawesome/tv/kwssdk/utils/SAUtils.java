/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package kws.superawesome.tv.kwssdk.utils;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Class that contains a lot of static aux functions used across the SDK to simplify code
 */
public class SAUtils {

    /**
     * Function that returns a random number between two limits
     *
     * @param min min edge
     * @param max max edge
     * @return    a random integer
     */
    public static int randomNumberBetween(int min, int max){
        Random rand  = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * Validate email
     *
     * @param target the char sequence to check for email validity
     * @return       true of false
     */
    public static boolean isValidEmail(CharSequence target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        return target != null && !target.toString().isEmpty() && pattern.matcher(target).matches();
    }
}
