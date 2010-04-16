package com.tinkerpop.pipes.parallel.pgm;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.pipes.parallel.Channel;
import com.tinkerpop.pipes.parallel.SerialProcess;

/**
 * @author: Marko A. Rodriguez (http://markorodriguez.com)
 */
public class VertexEdgeProcess extends SerialProcess<Vertex, Edge> {

    private final Step step;

    public enum Step {
        OUT_EDGES, IN_EDGES
    }

    public VertexEdgeProcess(final Step step) {
        this(step, null, null);
    }

    public VertexEdgeProcess(final Step step, final Channel<Vertex> inChannel, final Channel<Edge> outChannel) {
        super(inChannel, outChannel);
        this.step = step;
    }

    public boolean step() {
        Vertex vertex = inChannel.read();
        if (null != vertex) {
            Iterable<Edge> edges;
            if (this.step == Step.OUT_EDGES) {
                edges = vertex.getOutEdges();
            } else {
                edges = vertex.getInEdges();
            }
            for (Edge edge : edges) {
                this.outChannel.write(edge);
            }
            return true;
        } else {
            return false;
        }
    }
}
