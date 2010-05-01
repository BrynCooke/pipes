package com.tinkerpop.pipes.serial.merge;


import com.tinkerpop.pipes.serial.AbstractPipe;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author: Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ExhaustiveMergePipe<S> extends AbstractMergePipe<S> {

    protected Iterator<S> currentEnds;

    protected S processNextStart() {
        if (null != this.currentEnds && this.currentEnds.hasNext()) {
            return this.currentEnds.next();
        } else {
            if ((null == this.currentEnds || !this.currentEnds.hasNext()) && this.starts.hasNext()) {
                this.currentEnds = this.starts.next();
                return processNextStart();
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
