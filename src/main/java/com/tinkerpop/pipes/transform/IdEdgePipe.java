package com.tinkerpop.pipes.transform;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.pipes.AbstractPipe;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class IdEdgePipe<S> extends AbstractPipe<S, Edge> {

    private final Graph graph;

    public IdEdgePipe(final Graph graph) {
        this.graph = graph;
    }

    protected Edge processNextStart() {
        return this.graph.getEdge(this.starts.next());
    }
}