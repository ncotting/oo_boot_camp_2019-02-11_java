/*
 * Copyright (c) 2019 by Fred George
 * May be used freely except for training; license required for training.
 */

package graph;

import java.util.Comparator;
import java.util.List;

// Understands a connnection from one Node to another
class Link {
    private final double cost;
    private final Node target;

    Link(double cost, Node target) {
        this.cost = cost;
        this.target = target;
    }

    Path path(Node destination, List<Node> visitedNodes, Comparator<Path> strategy) {
        return target.path(destination, visitedNodes, strategy).prepend(this);
    }

    static double totalCost(List<Link> links) {
        return links.stream().mapToDouble(l -> l.cost).sum();
    }
}
