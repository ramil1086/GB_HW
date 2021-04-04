import Lesson3_6.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTask2 {
    Main main;

    @Before
    public void init() {
        System.out.println("Start test");
        main = new Main();

    }

    @Test
    public void testMain1 () {
        int[] expctdArray = {5,6,7,8,9};
        int[] checkArray = {1,2,3,4,5,6,7,8,9};
        Assert.assertArrayEquals(expctdArray, main.returnArrayWithFour(checkArray));
    }

    @Test (expected = RuntimeException.class)
    public void testMain2 () {
        int[] expctdArray = {5,6,7,8,9};
        int[] checkArray = {1,2,3,5,6,8,14};
        Assert.assertArrayEquals(expctdArray, main.returnArrayWithFour(checkArray));
    }

    @Test (expected = RuntimeException.class)
    public void testMain3 () {
        int[] nullArray = null;
        int[] expctdArray = {5,6,7,8,9};
        int[] checkArray = {1,2,3,5,6,8,14};
        Assert.assertArrayEquals(expctdArray, main.returnArrayWithFour(nullArray));
    }

    @Test
    public void testMain4 () {
        int[] expctdArray = {5,6,7,8,9};
        int[] checkArray = {1,2,3,5,6,7,8,9};
        Assert.assertArrayEquals(expctdArray, main.returnArrayWithFour(checkArray));
    }


    @After
    public void shutDown() {
        System.out.println("End test");
    }
}

