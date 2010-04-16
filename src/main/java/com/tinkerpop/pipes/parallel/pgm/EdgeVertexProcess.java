package com.tinkerpop.pipes.parallel.pgm;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.pipes.parallel.Channel;
import com.tinkerpop.pipes.parallel.SerialProcess;


/**
 * @author: Marko A. Rodriguez (http://markorodriguez.com)
 */
public class EdgeVertexProcess extends SerialProcess<Edge, Vertex> {

    private final Step step;

    public enum Step {
        IN_VERTEX, OUT_VERTEX
    }

    public EdgeVertexProcess(final Step step) {
        this(step, null, null);
    }

    public EdgeVertexProcess(final Step step, final Channel<Edge> inChannel, final Channel<Vertex> outChannel) {
        super(inChannel, outChannel);
        this.step = step;
    }

    public boolean step() {
        Edge edge = this.inChannel.read();
        if (null != edge) {
            if (this.step == Step.IN_VERTEX)
                this.outChannel.write(edge.getInVertex());
            else
                this.outChannel.write(edge.getOutVertex());
            return true;
        } else {
            return false;
        }
    }
}
