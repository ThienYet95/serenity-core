package net.thucydides.junit.finder;

import com.google.common.collect.Lists;
import net.serenity_bdd.junit.runners.DataDrivenAnnotations;
import org.junit.runners.model.TestClass;

import java.io.IOException;
import java.util.List;

/**
 * Returns all of the Thucydides classes under the specified package.
 */
public class DataDrivenTestFinder extends TestFinder {
    public DataDrivenTestFinder(final String rootPackage) {
        super(rootPackage);
    }

    @Override
    public List<Class<?>> getClasses() {
        return sorted(Lists.newArrayList(getDataDrivenTestClasses()));
    }

    @Override
    public int countTestMethods() {
        int totalTestMethods = 0;
        for(Class testClass : getDataDrivenTestClasses()) {
            try {
                totalTestMethods += DataDrivenAnnotations.forClass(new TestClass(testClass)).countDataEntries();
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to read test data for " + testClass);
            }
        }
        return totalTestMethods;
    }

}
