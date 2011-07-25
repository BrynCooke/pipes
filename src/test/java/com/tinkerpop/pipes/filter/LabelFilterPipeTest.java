package com.tinkerpop.pipes.filter;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraphFactory;
import junit.framework.TestCase;

/**
 * @author: Marko A. Rodriguez (http://markorodriguez.com)
 */
public class LabelFilterPipeTest extends TestCase {

    public void testFilterLabels() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        Vertex marko = graph.getVertex("1");
        LabelFilterPipe lfp = new LabelFilterPipe("knows", ComparisonFilterPipe.Filter.EQUAL);
        lfp.setStarts(marko.getOutEdges().iterator());
        assertTrue(lfp.hasNext());
        int counter = 0;
        while (lfp.hasNext()) {
            Edge e = lfp.next();
            assertEquals(e.getOutVertex(), marko);
            assertTrue(e.getInVertex().getId().equals("2") || e.getInVertex().getId().equals("4"));
            counter++;
        }
        assertEquals(counter, 2);

        lfp = new LabelFilterPipe("knows", ComparisonFilterPipe.Filter.NOT_EQUAL);
        lfp.setStarts(marko.getOutEdges().iterator());
        assertTrue(lfp.hasNext());
        counter = 0;
        while (lfp.hasNext()) {
            Edge e = lfp.next();
            assertEquals(e.getOutVertex(), marko);
            assertTrue(e.getInVertex().getId().equals("3"));
            counter++;
        }
        assertEquals(counter, 1);
    }
}
