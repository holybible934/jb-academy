// Do not remove imports
import java.lang.reflect.Method;
import java.util.Scanner;

class TypeVariablesInspector {
    public void printTypeVariablesCount(TestClass obj, String methodName) throws Exception {
        // Add implementation here
        Class myObj = obj.getClass();
        Method method = myObj.getDeclaredMethod(methodName);
        System.out.println(method.getTypeParameters().length);
    }
}