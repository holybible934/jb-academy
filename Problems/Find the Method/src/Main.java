import java.lang.reflect.Method;
import java.util.Arrays;

class MethodFinder {

    public static String findMethod(String methodName, String[] classNames) throws ClassNotFoundException {
        for (String className : classNames) {
            Method[] target = Class.forName(className).getMethods();
            if (Arrays.stream(target)
                    .anyMatch(method -> methodName.equals(method.getName()))) {
                return className;
            }
        }
        return null;
    }
}