/*
 * Copyright (c) 2019 by Fred George
 * May be used freely except for training; license required for training.
 */

package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Understands its neighbors
public class Node {
    private final List<Link> links = new ArrayList<>();

    public LinkBuilder cost(double amount) {
        return new LinkBuilder(amount, links);
    }

    public boolean canReach(Node destination) {
        return path(destination, noVisitedNodes(), Path.LEAST_COST) != Path.NONE;
    }

    public int hopCount(Node destination) {
        return path(destination, Path.FEWEST_HOPS).hopCount();
    }

    public double cost(Node destination) {
        return path(destination).cost();
    }

    public Path path(Node destination) {
        return path(destination, Path.LEAST_COST);
    }

    private Path path(Node destination, Comparator<Path> strategy) {
        Path result = this.path(destination, noVisitedNodes(), strategy);
        if (result == Path.NONE) throw new IllegalArgumentException("Unreachable destination");
        return result;
    }

    Path path(Node destination, List<Node> visitedNodes, Comparator<Path> strategy) {
        if (this == destination) return new Path.ActualPath();
        if (visitedNodes.contains(this)) return Path.NONE;
        return links.stream()
                .map(link -> link.path(destination, copyWithThis(visitedNodes), strategy))
                .min(strategy)
                .orElse(Path.NONE);
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
