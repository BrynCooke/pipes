package com.tinkerpop.pipes.filter;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraphFactory;
import com.tinkerpop.pipes.Pipe;
import com.tinkerpop.pipes.transform.InVertexPipe;
import com.tinkerpop.pipes.transform.OutEdgesPipe;
import com.tinkerpop.pipes.util.Pipeline;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author: Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PropertyFilterPipeTest extends TestCase {

    public void testPropertyFilter() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        Vertex marko = graph.getVertex("1");
        Pipe<Vertex, Edge> pipe1 = new OutEdgesPipe();
        Pipe<Edge, Vertex> pipe2 = new InVertexPipe();
        Pipe pipe3 = new PropertyFilterPipe<Vertex, String>("lang", "java", FilterPipe.Filter.EQUAL);
        Pipeline<Vertex, Vertex> pipeline = new Pipeline<Vertex, Vertex>(Arrays.asList(pipe1, pipe2, pipe3));
        pipeline.setStarts(Arrays.asList(marko).iterator());
        assertTrue(pipeline.hasNext());
        int counter = 0;
        while (pipeline.hasNext()) {
            counter++;
            Vertex vertex = pipeline.next();
            assertEquals(vertex.getId(), "3");
            assertEquals(vertex.getProperty("lang"), "java");
            assertEquals(vertex.getProperty("name"), "lop");
        }
        assertEquals(counter, 1);
        try {
            pipeline.next();
            assertTrue(false);
        } catch (NoSuchElementException e) {
            assertFalse(false);
        }

    }

    public void testPropertyFilter2() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        Vertex marko = graph.getVertex("1");
        Pipe<Vertex, Edge> pipe1 = new OutEdgesPipe();
        Pipe<Edge, Vertex> pipe2 = new InVertexPipe();
        Pipe pipe3 = new PropertyFilterPipe<Vertex, String>("lang", "java", FilterPipe.Filter.NOT_EQUAL);
        Pipeline<Vertex, Vertex> pipeline = new Pipeline<Vertex, Vertex>(Arrays.asList(pipe1, pipe2, pipe3));
        pipeline.setStarts(Arrays.asList(marko).iterator());
        assertTrue(pipeline.hasNext());
        int counter = 0;
        while (pipeline.hasNext()) {
            counter++;
            Vertex vertex = pipeline.next();
            assertTrue(vertex.getId().equals("2") || vertex.getId().equals("4"));
            assertTrue(vertex.getProperty("name").equals("josh") || vertex.getProperty("name").equals("vadas"));
            assertTrue(vertex.getProperty("age").equals(27) || vertex.getProperty("age").equals(32));
        }
        assertEquals(counter, 2);
        try {
            pipeline.next();
            assertTrue(false);
        } catch (NoSuchElementException e) {
            assertFalse(false);
        }

    }
}
