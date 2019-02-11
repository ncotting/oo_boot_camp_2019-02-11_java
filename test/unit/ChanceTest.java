/*
 * Copyright (c) 2019 by Fred George
 * May be used freely except for training; license required for training.
 */

package unit;

import org.junit.jupiter.api.Test;
import probability.Chance;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// Ensures Chance operates correctly
class ChanceTest {
    private static final Chance IMPOSSIBLE = new Chance(0.0);
    private static final Chance UNLIKELY = new Chance(0.25);
    private static final Chance EQUALLY_LIKELY = new Chance(0.5);
    private static final Chance LIKELY = new Chance(0.75);
    private static final Chance CERTAIN = new Chance(1.0);

    @Test void equality() {
        assertEquals(LIKELY, new Chance(0.75));
        assertNotEquals(LIKELY, UNLIKELY);
        assertNotEquals(LIKELY, new Object());
        assertNotEquals(LIKELY, null);
    }

    @Test void setOperations() {
        assertTrue(new HashSet<>(Collections.singletonList(LIKELY)).contains(new Chance(0.75)));
        assertEquals(1, new HashSet<>(Arrays.asList(LIKELY, new Chance(0.75))).size());
    }

    @Test void hash() {
        assertEquals(LIKELY.hashCode(), new Chance(0.75).hashCode());
    }

    @Test void not() {
        assertEquals(UNLIKELY, LIKELY.not());
        assertEquals(LIKELY, LIKELY.not().not());
        assertEquals(CERTAIN, IMPOSSIBLE.not());
        assertEquals(EQUALLY_LIKELY, EQUALLY_LIKELY.not());
    }
}
