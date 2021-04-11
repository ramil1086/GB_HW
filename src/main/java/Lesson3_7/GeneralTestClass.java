package Lesson3_7;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class GeneralTestClass {

    public static void start(Class cl) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = cl.getDeclaredMethods();
        ArrayList<Method> listOfTestMethods = new ArrayList<>();
        Method beforeMethod = null;
        Method afterMethod = null;
        boolean isBeforeSuitPresent = false;
        boolean isAfterSuitPresent = false;

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuit.class) && !isBeforeSuitPresent) {
                isBeforeSuitPresent = true;
                beforeMethod = m;
            } else if (m.isAnnotationPresent(MyTest.class)) {
                listOfTestMethods.add(m);
            } else if (m.isAnnotationPresent(AfterSuit.class) && !isAfterSuitPresent) {
                isAfterSuitPresent = true;
                afterMethod = m;
            } else if (m.isAnnotationPresent(BeforeSuit.class) && isBeforeSuitPresent) {
                throw new RuntimeException("Проблема с BeforeSuit");
            } else if (m.isAnnotationPresent(AfterSuit.class) && isAfterSuitPresent) {
                throw new RuntimeException("Проблема с AfterSuit");
            }
        }
            listOfTestMethods.sort(Comparator.comparingInt(o -> o.getAnnotation(MyTest.class).priority()));
            listOfTestMethods.add(0,beforeMethod);
            listOfTestMethods.add(afterMethod);

        for (Method m : listOfTestMethods) {
            if ((m.isAnnotationPresent(MyTest.class) && (m.getAnnotation(MyTest.class).priority() <= 10 && m.getAnnotation(MyTest.class).priority() >= 0)) || m.isAnnotationPresent(BeforeSuit.class) || m.isAnnotationPresent(AfterSuit.class)) {
               m.invoke(null);
            }
        }
    }

    public static void start(String cl) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class testClass= Class.forName(cl);
        start(testClass);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
//        start(TestClass.class);
        start("Lesson3_7.TestClass");
    }
}
