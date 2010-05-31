package com.tinkerpop.pipes.merge;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ExhaustiveMergePipeTest extends TestCase {

    public void testMergePipe() {
        List<String> marko = Arrays.asList("marko", "antonio", "rodriguez");
        List<String> peter = Arrays.asList("peter", "neubauer");
        List<String> josh = Arrays.asList("joshua", "shinavier");
        ExhaustiveMergePipe<String> pipe = new ExhaustiveMergePipe<String>();
        pipe.setStarts(Arrays.asList(marko.iterator(), peter.iterator(), josh.iterator()).iterator());
        assertTrue(pipe.hasNext());
        int counter = 0;
        while (pipe.hasNext()) {
            counter++;
            String name = pipe.next();
            //System.out.println(name);
            assertTrue(marko.contains(name) || peter.contains(name) || josh.contains(name));
        }
        assertEquals(counter, 7);
    }

    public void testMergePipeEmpties() {
        List<String> marko = Arrays.asList();
        List<String> peter = Arrays.asList("peter", "neubauer");
        List<String> josh = Arrays.asList();
        ExhaustiveMergePipe<String> pipe = new ExhaustiveMergePipe<String>();
        pipe.setStarts(Arrays.asList(marko.iterator(), peter.iterator(), josh.iterator()).iterator());
        assertTrue(pipe.hasNext());
        int counter = 0;
        while (pipe.hasNext()) {
            counter++;
            String name = pipe.next();
            assertTrue(marko.contains(name) || peter.contains(name) || josh.contains(name));
        }
        assertEquals(counter, 2);
    }

    public void testMergePipeAllEmpties() {
        List<String> marko = Arrays.asList();
        List<String> peter = Arrays.asList();
        List<String> josh = Arrays.asList();
        ExhaustiveMergePipe<String> pipe = new ExhaustiveMergePipe<String>();
        pipe.setStarts(Arrays.asList(marko.iterator(), peter.iterator(), josh.iterator()).iterator());
        assertFalse(pipe.hasNext());
        int counter = 0;
        while (pipe.hasNext()) {
            counter++;
            pipe.next();
        }
        assertEquals(counter, 0);
    }

}
