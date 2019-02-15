/*
 * Copyright (c) 2019 by Fred George
 * May be used freely except for training; license required for training.
 */

package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// Understands its neighbors
public class Node {
    private static final double UNREACHABLE = Double.POSITIVE_INFINITY;
    private final List<Link> links = new ArrayList<>();

    public LinkBuilder cost(double amount) {
        return new LinkBuilder(amount, links);
    }

    public boolean canReach(Node destination) {
        return cost(destination, noVisitedNodes(), Link.LEAST_COST) != UNREACHABLE;
    }

    public int hopCount(Node destination) {
        return (int)cost(destination, Link.FEWEST_HOPS);
    }

    public double cost(Node destination) {
        return cost(destination, Link.LEAST_COST);
    }

    public Path path(Node destination) {
        Path result = this.path(destination, noVisitedNodes());
        if (result == Path.NONE) throw new IllegalArgumentException("Unreachable destination");
        return result;
    }

    Path path(Node destination, List<Node> visitedNodes) {
        if (this == destination) return new Path.ActualPath();
        if (visitedNodes.contains(this)) return Path.NONE;
        return links.stream()
                .map(link -> link.path(destination, copyWithThis(visitedNodes)))
                .min(Comparator.comparing(Path::cost))
                .orElse(Path.NONE);
    }

    private double cost(Node destination, Link.CostStrategy strategy) {
        double result = this.cost(destination, noVisitedNodes(), strategy);
        if (result == UNREACHABLE) throw new IllegalArgumentException("Unreachable destination");
        return result;
    }

    double cost(Node destination, List<Node> visitedNodes, Link.CostStrategy strategy) {
        if (this == destination) return 0;
        if (visitedNodes.contains(this)) return UNREACHABLE;
        return links.stream()
                .mapToDouble(link -> link.cost(destination, copyWithThis(visitedNodes), strategy))
                .min()
                .orElse(UNREACHABLE);
    }

    private List<Node> noVisitedNodes() {
        return new ArrayList<>();
    }

    private List<Node> copyWithThis(List<Node> originals) {
        var results = new ArrayList<>(originals);
        results.add(this);
        return results;
    }

    public static class LinkBuilder {
        private final double cost;
        private final List<Link> links;

        private LinkBuilder(double cost, List<Link> links) {
            this.cost = cost;
            this.links = links;
        }

        public Node to(Node neighbor) {
            links.add(new Link(cost, neighbor));
            return neighbor;
        }
    }
}
