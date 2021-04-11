import Lesson3_6.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTask3 {
  Main main;

    @Before
    public void init() {
        System.out.println("Start test");
        main = new Main();
    }

    @Test
    public void testWithResultTrue1 () {
    int[] arrayToCheck =  {0,0,1,0,0,4,0,0};
        Assert.assertTrue(main.isOneOrFourPresentIntArray(arrayToCheck));
    }

    @Test
    public void testWithResultTrue2 () {
        int[] arrayToCheck =  {1,2,3,5,7};
        Assert.assertTrue(main.isOneOrFourPresentIntArray(arrayToCheck));
    }
    @Test
    public void testWithResultFalse1 () {
        int[] arrayToCheck = {1,2,3,5,6,7};
        Assert.assertFalse(main.isOneOrFourPresentIntArray(arrayToCheck));
    }
    @Test
    public void testWithResultFalse2 () {
        int[] arrayToCheck = {1,2,3,5,6,4};
        Assert.assertFalse(main.isOneOrFourPresentIntArray(arrayToCheck));
    }
    @After
    public void shutDown() {
        System.out.println("End test");
    }
}
