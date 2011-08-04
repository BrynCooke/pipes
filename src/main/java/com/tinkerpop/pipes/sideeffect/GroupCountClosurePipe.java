package com.tinkerpop.pipes.sideeffect;

import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.PipeClosure;

import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class GroupCountClosurePipe<S> extends AbstractPipe<S, S> implements SideEffectPipe<S, Map<Object, Number>> {

    private Map<Object, Number> countMap;
    private final PipeClosure valueClosure;
    private final PipeClosure keyClosure;

    public GroupCountClosurePipe(final Map<Object, Number> countMap, final PipeClosure keyClosure, final PipeClosure valueClosure) {
        this.countMap = countMap;
        this.valueClosure = valueClosure;
        this.keyClosure = keyClosure;
    }

    protected S processNextStart() {
        final S s = this.starts.next();
        final Object key = this.getKey(s);
        this.countMap.put(key, this.getValue(key));
        return s;
    }

    public Map<Object, Number> getSideEffect() {
        return this.countMap;
    }

    public void reset() {
        try {
            this.countMap = this.countMap.getClass().getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        super.reset();
    }

    private Object getKey(final S start) {
        if (null == keyClosure) {
            return start;
        } else {
            return keyClosure.compute(start);
        }
    }

    private Number getValue(final Object key) {
        Number number = this.countMap.get(key);
        if (null == number) {
            number = 0l;
        }
        if (null == valueClosure) {
            if (number instanceof Double) {
                return 1.0d + number.doubleValue();
            } else if (number instanceof Long) {
                return 1l + number.longValue();
            } else if (number instanceof Float) {
                return 1.0f + number.floatValue();
            } else {
                return 1 + number.intValue();
            }
        } else {
            return (Number) this.valueClosure.compute(number);
        }

    }
}