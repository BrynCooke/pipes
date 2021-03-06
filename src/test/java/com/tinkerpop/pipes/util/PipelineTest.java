package com.tinkerpop.pipes.util;

import com.tinkerpop.pipes.IdentityPipe;
import com.tinkerpop.pipes.Pipe;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author: Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PipelineTest extends TestCase {

    public void testPipelineReset() {
        Collection<String> names = Arrays.asList("marko", "peter");
        Pipe<String, String> pipe1 = new IdentityPipe<String>();
        Pipe<String, String> pipe2 = new IdentityPipe<String>();
        Pipe<String, String> pipe3 = new IdentityPipe<String>();
        Pipe<String, String> pipeline = new Pipeline<String, String>(pipe1, pipe2, pipe3);
        pipeline.setStarts(names);

        assertTrue(pipeline.hasNext());
        pipeline.reset();
        assertTrue(pipeline.hasNext());
        pipeline.reset();
        assertFalse(pipeline.hasNext()); // Pipe has consumed and reset has
        // thrown away both items.
    }

    public void testPipelineCast() {

        Pipeline<String, String> pipeline = new Pipeline<String, String>(new IdentityPipe<String>());
        pipeline.setStarts(Arrays.asList("marko", "peter"));
        Pipeline<String, Integer> cast = pipeline.cast(Integer.class);
        assertTrue(((Object) pipeline) == ((Object) cast));
        try {
            Integer next = cast.next();
            fail("Value was actually a string");
        } catch (ClassCastException e) {

        }

    }
}
