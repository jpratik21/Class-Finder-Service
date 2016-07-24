import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import junit.framework.TestCase;

/**
 *
 *
 * @author Pratik Jaiswal
 *
 */

public class ClassFinderTest extends TestCase {

    private ClassFinder classFinderService = null;

    public void setUp() {
        classFinderService = new ClassFinderService();
    }

    public void testFindAnnotatedClassByPackage() throws ClassNotFoundException, NoSuchMethodException, SecurityException {

    	String pattern = "\\bid\\b=\\[(.+?)\\]";
    	Pattern r = Pattern.compile(pattern);
		List<String> classFilePaths = classFinderService.findAnnotatedClassesInPackage("com.package.name", AnnotationInterfaceName.class);
        Set<String> set = new HashSet<>();
        set.addAll(classFilePaths);
        classFilePaths.clear();
        classFilePaths.addAll(set);

        List<String> myList = new ArrayList<String>();

        for(int i = 0; i < classFilePaths.size(); i++) {
        	Class<?> act = Class.forName(classFilePaths.get(i));
        	Method[] allMethods = act.getDeclaredMethods();
        	for (Method method : allMethods) {
        		if (Modifier.isPublic(method.getModifiers())) {
                	Annotation[] annotation = method.getDeclaredAnnotations();
                	for (int j = 0; j < annotation.length; j++) {
                    	Object ob = annotation[j];
                    	String st = ob.toString();
                    	Matcher m = r.matcher(st);
                    	if (m.find()) {
                    		String id = m.group(1);
                    		myList.add(id);
                		}
                    }
                }
        	}
        }

        Set<String> idSet = new HashSet<>();
        idSet.addAll(myList);
        myList.clear();
        myList.addAll(idSet);

        List<Integer> myListIntegers = new ArrayList<Integer>();
        for (String ids : myList) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(ids);
            while (m.find()) {
                myListIntegers.add(Integer.parseInt(m.group()));
            }
        }

        for(int str: myListIntegers)
        {
        	System.out.println(str);
        }
        //return myListIntegers;
    }
}
