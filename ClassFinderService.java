import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.pandora.qa.mobile.common.utils.finder.ClassFinder;
import com.pandora.qa.mobile.common.utils.finder.ClassFinderUtils;
import com.pandora.qa.mobile.common.utils.finder.ClassFinderException;

public final class ClassFinderService implements ClassFinder {


    public List<String> findClassesByPackage(final String packagePath) {

        if (packagePath == null || packagePath.isEmpty())
            throw new ClassFinderException("The parameter passed cannot be null or empty");

        List<String> filePaths = ClassFinderUtils.retrieveClassFilesByLoader(this.getClass());

        List<String> fileList = new LinkedList<String>();

        String convertedPackagePath = packagePath.replaceAll("\\.", "\\" + File.separator);
        for (String urlPath : filePaths) {
            ClassFinderUtils.retrieveFilePath(urlPath, urlPath, convertedPackagePath, fileList);
        }

        if (fileList.isEmpty())
            throw new ClassFinderException("No classes found in the path : " + packagePath);

        return fileList;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> findAnnotatedClassesInPackage(final String packagePath, final Class annotatedClass) {
        if (packagePath == null || packagePath.isEmpty())
            throw new ClassFinderException("The parameter passed cannot be null or empty");

        List<String> annotatedClasses = new LinkedList<String>();
        List<String> filePaths = findClassesByPackage(packagePath);

        for (String classFound : filePaths) {
            try {
                Class cl = Class.forName(classFound);
                if (cl.isAnnotationPresent(annotatedClass)) {
                    annotatedClasses.add(classFound);
                }

                else if (cl.getMethods() != null && cl.getMethods().length > 0) {
                    for (Method method : cl.getMethods()) {
                        method.setAccessible(true);
                        if (method.isAnnotationPresent(annotatedClass))
                            annotatedClasses.add(classFound);
                    }
                } else if (cl.getFields() != null && cl.getFields().length > 0) {
                    for (Field field : cl.getFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(annotatedClass))
                            annotatedClasses.add(classFound);
                    }
                }

            } catch (ClassNotFoundException e) {
                throw new ClassFinderException(e);
            }

        }

        return annotatedClasses;
    }

}
