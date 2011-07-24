package com.tinkerpop.pipes.transform;

import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraphFactory;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.util.SingleIterator;
import junit.framework.TestCase;

import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PropertyMapPipeTest extends TestCase {

    public void testPropertyMapPipe() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        Pipe<Vertex, Map<String, Object>> pipe = new PropertyMapPipe<Vertex>();
        pipe.setStarts(new SingleIterator<Vertex>(graph.getVertex(1)));
        int counter = 0;
        while (pipe.hasNext()) {
            Map<String, Object> map = pipe.next();
            counter++;
            assertEquals(map.get("name"), "marko");
            assertEquals(map.get("age"), 29);
            assertNull(map.get("blah"));
        }
        assertEquals(counter, 1);
    }
}
