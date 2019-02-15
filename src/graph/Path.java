/*
 * Copyright (c) 2019 by Fred George
 * May be used freely except for training; license required for training.
 */

package graph;

import java.util.ArrayList;
import java.util.List;

// Understands a way to get from one Node to another
public abstract class Path {

    public abstract int hopCount();

    public abstract double cost();

    void prepend(Link link) { } // Do nothing

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
        void prepend(Link link) {
            links.add(0, link);
        }
    }
}
