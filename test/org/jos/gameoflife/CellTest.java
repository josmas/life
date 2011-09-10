package org.jos.gameoflife;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class CellTest {
	
    @Test
    public void testEqualityInSetsWithCels(){
    	
    	Set<Cell> filledUpGrid = new HashSet<Cell>();
    	Cell celCero = new Cell(0, 0);
    	Cell celZero = new Cell(0, 0);
    	filledUpGrid.add(celCero);
    	
    	assertFalse("Element was there?", filledUpGrid.add(celZero));
    	assertTrue("The cels are Equals?", celCero.equals(celZero));
    	assertTrue("Size seems to NOT be one", filledUpGrid.size() == 1);
    }
}
