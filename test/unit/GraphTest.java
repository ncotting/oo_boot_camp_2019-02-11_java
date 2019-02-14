/*
 * Copyright (c) 2019 by Fred George
 * May be used freely except for training; license required for training.
 */

package unit;
import graph.Node;
import graph.Path;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Ensures graph algorithms function correctly
class GraphTest {
    private static final double EPSILON = 0.00000001;

    private final static Node A, B, C, D, E, F, G;

    static {
        A = new Node();
        B = new Node();
        C = new Node();
        D = new Node();
        E = new Node();
        F = new Node();
        G = new Node();

        B.cost(5).to(A);
        B.cost(6).to(C).cost(7).to(D).cost(2).to(E).cost(3).to(B).cost(4).to(F);
        C.cost(1).to(D);
        C.cost(8).to(E);
    }

    @Test void canReach() {
        assertTrue(B.canReach(B));
        assertTrue(B.canReach(A));
        assertTrue(B.canReach(F));
        assertTrue(B.canReach(D));
        assertTrue(C.canReach(F));
        assertFalse(G.canReach(B));
        assertFalse(A.canReach(B));
        assertFalse(B.canReach(G));
    }

    @Test void hopCount() {
        assertEquals(0, B.hopCount(B));
        assertEquals(1, B.hopCount(A));
        assertEquals(1, B.hopCount(F));
        assertEquals(2, B.hopCount(D));
        assertEquals(3, C.hopCount(F));
        assertThrows(IllegalArgumentException.class, () -> G.hopCount(B));
        assertThrows(IllegalArgumentException.class, () -> A.hopCount(B));
        assertThrows(IllegalArgumentException.class, () -> B.hopCount(G));
    }

    @Test void cost() {
        assertEquals(0, B.cost(B));
        assertEquals(5, B.cost(A));
        assertEquals(4, B.cost(F));
        assertEquals(7, B.cost(D));
        assertEquals(10, C.cost(F));
        assertThrows(IllegalArgumentException.class, () -> G.cost(B));
        assertThrows(IllegalArgumentException.class, () -> A.cost(B));
        assertThrows(IllegalArgumentException.class, () -> B.cost(G));
    }

    @Test void path() {
        assertPath(A, A, 0, 0);
        assertPath(B, A, 1, 5);
        assertPath(B, F, 1, 4);
        assertPath(B, D, 2, 7);
        assertPath(C, F, 4, 10);
        assertThrows(IllegalArgumentException.class, () -> G.path(A));
        assertThrows(IllegalArgumentException.class, () -> A.path(B));
        assertThrows(IllegalArgumentException.class, () -> B.path(G));
    }

    private void assertPath(Node source, Node destination, long expectedHopCount, long expectedCost) {
        Path p = source.path(destination);
        assertEquals(expectedHopCount, p.hopCount());
        assertEquals(expectedCost, p.cost(), EPSILON);
    }
}
