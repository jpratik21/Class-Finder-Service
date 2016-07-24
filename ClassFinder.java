import java.util.List;

/**
 * ClassFinder Api
 *
 * @author Pratik Jaiswal
 *
 */

public interface ClassFinder {

	List<String> findClassesByPackage(final String packagePath);

	List<String> findAnnotatedClassesInPackage(final String packagePath,final Class annotatedClass);

}
