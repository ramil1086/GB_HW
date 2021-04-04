import Lesson3_6.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

public class MassTestTask3 {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {0,0,1,0,0,4,0,0},
                {1,2,3,4,5,6,7,8},
                {4,6,7,8,9,1},
                {1,2,3,5,6,4,7}
        });
    }

    private int[] arrayToCheck;

    public MassTestTask3 (int[] array) {
        arrayToCheck = array;
    }

    Main main;

    @Before
    public void init() {
        System.out.println("Start test");
        main = new Main();
    }

    @Test
    public void massTestWithResultTrue () {
        Assert.assertTrue(main.isOneOrFourPresentIntArray(arrayToCheck));
    }


    @After
    public void shutDown() {
        System.out.println("End test");
    }
}
