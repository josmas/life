package org.jos.gameoflife;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid {
	
	private final List<Cell> seedGrid;
	private Set<Cell> deadAndAliveGrid;
	private List<Cell> evolvedGrid;

	private static final int[][] HOOD_INDEXES = { 
		{ -1, -1 }, { -1, 0 }, { -1, 1 },
		{ 0, -1 }, { 0, 1 }, 
		{ 1, -1 }, { 1, 0 }, { 1, 1 } 
	};

	public Grid(List<Cell> cellForGrid) {
		this.seedGrid = cellForGrid;
		this.deadAndAliveGrid = fillUpGrid();
		this.evolvedGrid = new ArrayList<Cell>();
	}

	public void tick() {
		for (Cell currentCell : deadAndAliveGrid) {
			int numberOfNeighbours = countNeighbours(currentCell);
			applyEvolutionRules(currentCell, numberOfNeighbours);
		}
	}

	private void applyEvolutionRules(Cell currentCell, int numberOfNeighbours) {
		if ( (numberOfNeighbours == 2 || numberOfNeighbours == 3) && seedGrid.contains(currentCell) )
			evolvedGrid.add(currentCell);
		if ( numberOfNeighbours == 3 && !seedGrid.contains(currentCell) )
			evolvedGrid.add(currentCell);
	}

	public int evolvedGridSize() {
		return evolvedGrid.size();
	}

	public List<Cell> getEvolvedGrid() {
		return evolvedGrid;
	}

	Set<Cell> fillUpGrid() {
		Set<Cell> filledUpGrid = new HashSet<Cell>();
		
		for (Cell aliveCell : seedGrid) {
			for (int i = 0; i < HOOD_INDEXES.length; i++) {
				int [] currentNeighbour = HOOD_INDEXES[i];
				filledUpGrid.add( new Cell( aliveCell.getX() - currentNeighbour[0], 
						aliveCell.getY() - currentNeighbour[1]) );
			}
			filledUpGrid.add(aliveCell);
		}
		
		return filledUpGrid;
	}

	public int countNeighbours(Cell cell) {
		int totalNeighbours = 0;
		
		for (int i = 0; i < HOOD_INDEXES.length; i++) {
			int [] currentNeighbour = HOOD_INDEXES[i];
			Cell cellToCheck = new Cell( cell.getX() - currentNeighbour[0], 
				cell.getY() - currentNeighbour[1]);
			if (seedGrid.contains(cellToCheck))
				++totalNeighbours;
		}
		
		return totalNeighbours;
	}
}
