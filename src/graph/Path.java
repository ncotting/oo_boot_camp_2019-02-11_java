/*
 * Copyright (c) 2019 by Fred George
 * May be used freely except for training; license required for training.
 */

package graph;

import java.util.ArrayList;
import java.util.List;

// Understands a way to get from one Node to another
public abstract class Path {
    static final Path NONE = new NoPath();

    public abstract int hopCount();

    public abstract double cost();

    Path prepend(Link link) { return this; } // Do nothing

    static class ActualPath extends Path {
        private final List<Link> links = new ArrayList<>();

        @Override
        public int hopCount() {
            return links.size();
        }

        @Override
        public double cost() {
            return Link.totalCost(links);
        }

        @Override
        Path prepend(Link link) {
            links.add(0, link);
            return this;
        }
    }

    static class NoPath extends Path {

        @Override
        public int hopCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public double cost() {
            return Double.POSITIVE_INFINITY;
        }
    }
}
