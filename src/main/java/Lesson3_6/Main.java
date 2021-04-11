package Lesson3_6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Main {
    static final Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        int[] ar = {1,2,3,4,5,6,7,8,9};

        logger.info((Arrays.toString(returnArrayWithFour(ar))));
        logger.info((isOneOrFourPresentIntArray(ar)));

    }

    public static int[] returnArrayWithFour (int[] array) {
        if (array.length == 0) {
            logger.error("RTE - Массив пустой");
            throw new RuntimeException("Массив пустой");
        }
        for (int i = array.length-1; i >0; i--) {
            if (array[i] == 4) {
            return Arrays.copyOfRange(array, i+1,array.length);}
        }
        logger.error("RTE - Нет четверки");
        throw new RuntimeException("Нет четверки");
    }

    public static boolean isOneOrFourPresentIntArray(int[] array) {
        boolean containsOne = false;
        boolean containsFour = false;
        for (int i : array) {
            if (containsOne && containsFour) break;
            if (i == 1) containsOne = true;
            if (i == 4) containsFour = true;
        }
        return containsOne && containsFour;


//
    }
}
