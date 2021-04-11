package Lesson3_7;


import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.Test;

public class TestClass {

    @BeforeSuit
    public static void beforeMethod (){
        System.out.println("Before");
    }
//    @BeforeSuit
//    public static void beforeMethod1 (){
//        System.out.println("Before");
//    }
    @MyTest (priority = 6)
    public static void doTest1 () {
        System.out.println("Test1");
    }
    @MyTest (priority = 3)
    public static void doTest2 () {
        System.out.println("Test2");
    }
    @MyTest (priority = 12)
    public static void doTest3 () {
        System.out.println("Test3");
    }
    @MyTest (priority = 1)
    public static void doTest4 () {
        System.out.println("Test4");
    }
    @MyTest (priority = 3)
    public static void doTest5 () {
        System.out.println("Test5");
    }
    public static void doTest6 () {
        System.out.println("Test6");
    }
    @Test
    public static void checkDZ1 (Object m) throws Exception {
        try {

            Assert.assertEquals("task1", m);
            System.out.println("Успешно задание 1");
        } catch (ComparisonFailure e) {
            throw new ComparisonFailure("В задании ошибка", "task1", (String) m);
        }
        }
    @Test
    public static void checkDZ2 (Object m) throws Exception {
        try {

            Assert.assertEquals(5,m);
            System.out.println("Успешно задание 2");
        } catch (AssertionError e) {
            throw new AssertionError("Ошибка в задании 2");
        }
    }
    @AfterSuit
    public static void afterMethod () {
        System.out.println("After");
    }
//    @AfterSuit
//    public static void afterMethod1 () {
//        System.out.println("After");
//    }

}
