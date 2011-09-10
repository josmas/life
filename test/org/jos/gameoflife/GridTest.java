package org.jos.gameoflife;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class GridTest {
	
	List<Cell> cellsForGrid;
	
	@Before
	public void setUp(){
		cellsForGrid = new ArrayList<Cell>();
	}
	
    @Test
    public void itShouldDieWithOneCell() {
    	
    	List<Cell> CellForGrid = createCentralUniCellGrid();
    	Grid seedGrid = new Grid(CellForGrid);
    	
    	seedGrid.tick();
    	
    	assertEquals( 0, seedGrid.evolvedGridSize() );
    	List<Cell> empty =  new ArrayList<Cell>();
    	assertEquals("Evolved grid is not empty", empty, seedGrid.getEvolvedGrid() );
    }
    
    private List<Cell> createCentralUniCellGrid(){
        List<Cell> cellForGrid = new ArrayList<Cell>();
        cellForGrid.add(new Cell(0, 0));
        
        return cellForGrid;
    }
    
    @Test
    public void itShouldReturnABlockWithABlock() {
    	
        cellsForGrid.add(new Cell(0, 0)); cellsForGrid.add(new Cell(0, 1));
        cellsForGrid.add(new Cell(1, 0)); cellsForGrid.add(new Cell(1, 1));
        Grid seedGrid = new Grid(cellsForGrid);
        
        seedGrid.tick();
        
        assertEquals( 4, seedGrid.evolvedGridSize() );
        List<Cell> evolvedGrid = seedGrid.getEvolvedGrid();
        assertTrue("0", evolvedGrid.contains(cellsForGrid.get(0)) );
        assertTrue("1", evolvedGrid.contains(cellsForGrid.get(1)) );
        assertTrue("2", evolvedGrid.contains(cellsForGrid.get(2)) );
        assertTrue("3", evolvedGrid.contains(cellsForGrid.get(3)) );
    }
    
    @Test
    public void itShouldFillupTheGridWithDeadAndAliveCells(){
    	
    	List<Cell> cellForGrid = createCentralUniCellGrid();
    	Grid seedGrid = new Grid(cellForGrid);
    	List<Cell> expectedFilledUP = createCentralNeighbourHoodCellGrid();
    	Set<Cell> filledUpGrid = seedGrid.fillUpGrid();

    	assertEquals("size", 9, filledUpGrid.size());
    	assertTrue("0", filledUpGrid.contains(expectedFilledUP.get(0)));
    	assertTrue("1", filledUpGrid.contains(expectedFilledUP.get(1)));
    	assertTrue("2", filledUpGrid.contains(expectedFilledUP.get(2)));
    	assertTrue("3", filledUpGrid.contains(expectedFilledUP.get(3)));
    	assertTrue("4", filledUpGrid.contains(expectedFilledUP.get(4)));
    	assertTrue("5", filledUpGrid.contains(expectedFilledUP.get(5)));
    	assertTrue("6", filledUpGrid.contains(expectedFilledUP.get(6)));
    	assertTrue("7", filledUpGrid.contains(expectedFilledUP.get(7)));
    	assertTrue("8", filledUpGrid.contains(expectedFilledUP.get(8)));
    }
    
    private List<Cell> createCentralNeighbourHoodCellGrid(){
    	List<Cell> cellsForGrid = new ArrayList<Cell>();
    	cellsForGrid.add(new Cell(-1, -1)); cellsForGrid.add(new Cell(-1, 0)); cellsForGrid.add(new Cell(-1, 1));
    	cellsForGrid.add(new Cell(0, -1)); cellsForGrid.add(new Cell(0, 0)); cellsForGrid.add(new Cell(0, 1));
    	cellsForGrid.add(new Cell(1, -1)); cellsForGrid.add(new Cell(1, 0)); cellsForGrid.add(new Cell(1, 1));
    	
    	return cellsForGrid;
    }
    
    @Test
    public void itShouldFillupTheGridWithoutAnyRepeats(){
    	List<Cell> cellForGrid = createCentralUniCellGrid();
    	cellForGrid.add(new Cell(0, 1));
    	Grid seedGrid = new Grid(cellForGrid);
    	
    	Set<Cell> filledUpGrid = seedGrid.fillUpGrid();
    	
    	assertTrue("wrong size", 12 == filledUpGrid.size());
    }
    
    @Test
    public void shouldHaveZeroOrOneNeighbours(){
    	List<Cell> cellForGrid = createCentralUniCellGrid();
    	Grid seedGrid = new Grid(cellForGrid);
    	Set<Cell> filledUpGrid = seedGrid.fillUpGrid();
        
    	Cell cell = new Cell(-1, -1);
        assertTrue("Contains {-1, -1}?", filledUpGrid.contains(cell));
        assertEquals("{-1, -1} neighbours:", 1, seedGrid.countNeighbours(cell));
        
        cell = new Cell(1, 1);
        assertTrue("Contains {1, 1}?", filledUpGrid.contains(cell));
        assertEquals("{1, 1} neighbours", 1, seedGrid.countNeighbours(cell));
        
        cell = new Cell(0, 0);
        assertTrue("Contains {0, 0}?", filledUpGrid.contains(cell));
        assertEquals("{0, 0} neighbours?", 0, seedGrid.countNeighbours(cell));
    }
    
    @Test
    public void itShouldOscilateAnOscilator() {
    	
        cellsForGrid.add( new Cell(0, 1) );
        cellsForGrid.add( new Cell(1, 1) );
        cellsForGrid.add( new Cell(2, 1) );
        Grid seedGrid = new Grid(cellsForGrid);
        List<Cell> expectedOscilator = new ArrayList<Cell>();
        expectedOscilator.add( new Cell(1, 0) );
        expectedOscilator.add( new Cell(1, 1) );
        expectedOscilator.add( new Cell(1, 2) );
        
        seedGrid.tick();
        List<Cell> evolvedGrid = seedGrid.getEvolvedGrid();
        
        assertEquals( 3, seedGrid.evolvedGridSize() );
        assertTrue("Cell 0 exists?", expectedOscilator.contains( evolvedGrid.get(0) ) );
        assertTrue("Cell 1 exists?", expectedOscilator.contains( evolvedGrid.get(1) ) );
        assertTrue("Cell 2 exists?", expectedOscilator.contains( evolvedGrid.get(2) ) );
        System.out.println("Oscilator:   Original     " +cellsForGrid);
        System.out.println("Oscilator: After one tick " +evolvedGrid);
        
        //Another tick with the evolved grid as a seed
        seedGrid = new Grid(evolvedGrid);
        
        seedGrid.tick();
        evolvedGrid = seedGrid.getEvolvedGrid();
        
        assertEquals( 3, seedGrid.evolvedGridSize() );
        System.out.println("Oscilator: After two ticks" +evolvedGrid);
        assertTrue("0", cellsForGrid.contains( evolvedGrid.get(0) ) );
        assertTrue("1", cellsForGrid.contains( evolvedGrid.get(1) ) );
        assertTrue("2", cellsForGrid.contains( evolvedGrid.get(2) ) );
    }
    
    @Test
    public void itShouldEvolveAGlider(){
    	
    	cellsForGrid.add( new Cell(0, 2) );
        cellsForGrid.add( new Cell(1, 0) );
        cellsForGrid.add( new Cell(1, 2) );
        cellsForGrid.add( new Cell(2, 1) );
        cellsForGrid.add( new Cell(2, 2) );
        Grid seedGrid = new Grid(cellsForGrid);
        List<Cell> expectedGlider = new ArrayList<Cell>();
        expectedGlider.add( new Cell(1, 2) );
        expectedGlider.add( new Cell(2, 1) );
        expectedGlider.add( new Cell(2, 2) );
        expectedGlider.add( new Cell(0, 1) );
        expectedGlider.add( new Cell(1, 3) );
        
        seedGrid.tick();
        List<Cell> evolvedGrid = seedGrid.getEvolvedGrid();
        
        assertEquals( 5, seedGrid.evolvedGridSize() );
        assertTrue("0", expectedGlider.contains( evolvedGrid.get(0) ) );
        
        System.out.println("\nGlider:    Original    " + cellsForGrid);
        System.out.println("Glider: After one tick " + evolvedGrid);	
    }
}
