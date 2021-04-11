package Lesson3_7;



import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class CheckDZ {
    public static void main(String[] args) throws Exception {
        File file = new File("src/DZ");
        String[] str = file.list();

        for (String o : str) {

            String[] mass = o.split("\\.");
            if (!mass[1].equalsIgnoreCase("class")) {
                throw new RuntimeException(o, new Exception());
            }

            Class ch = URLClassLoader.newInstance(new URL[]{file.toURL()}).loadClass(mass[0]);
            Constructor constructor = ch.getConstructor();

            Object currentStudent = constructor.newInstance();
            Method m = ch.getDeclaredMethod("info");
            m.invoke(currentStudent);
            Method m1 = ch.getDeclaredMethod("task1", String.class, String.class);
            TestClass.checkDZ1(m1.invoke(currentStudent, "ta", "sk1"));
            Method m2 = ch.getDeclaredMethod("task2", int.class, int.class);
            TestClass.checkDZ2(m2.invoke(currentStudent, 3, 2));

        }
    }
}
