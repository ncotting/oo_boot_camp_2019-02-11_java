/*
 * Copyright (c) 2019 by Fred George
 * May be used freely except for training; license required for training.
 */

package graph;

import java.util.ArrayList;
import java.util.List;

// Understands a way to get from one Node to another
public class Path {
    private final List<Link> links = new ArrayList<>();

    public int hopCount() {
        return links.size();
    }

    public double cost() {
        return Link.totalCost(links);
    }

    void prepend(Link link) {
        links.add(0, link);
    }
}
