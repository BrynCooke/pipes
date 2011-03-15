package com.tinkerpop.pipes.pgm;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraphFactory;
import com.tinkerpop.pipes.SingleIterator;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class BothEdgesPipeTest extends TestCase {

    public void testBothEdges() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        Vertex josh = graph.getVertex("4");
        BothEdgesPipe pipe = new BothEdgesPipe();
        pipe.setStarts(new SingleIterator<Vertex>(josh));
        int counter = 0;
        while (pipe.hasNext()) {
            counter++;
            Edge edge = pipe.next();
            assertTrue(edge.getId().equals("8") || edge.getId().equals("10") || edge.getId().equals("11"));
        }
        assertEquals(counter, 3);
    }

    public void testBothEdgesWithLabels() {

        Graph graph = TinkerGraphFactory.createTinkerGraph();
        Vertex marko = graph.getVertex("1");
        BothEdgesPipe pipe = new BothEdgesPipe("knows");
        pipe.setStarts(new SingleIterator<Vertex>(marko));
        int counter = 0;
        while (pipe.hasNext()) {
            counter++;
            Edge edge = pipe.next();
            assertTrue(edge.getInVertex().getProperty("name").equals("josh") || edge.getInVertex().getProperty("name").equals("vadas"));
        }
        assertEquals(counter, 2);
    }
}
