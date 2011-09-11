describe("The game of life", function() {
  it("should return an empty grid for a seed of 1", function() {
    var seed = [ [0,0] ];
    var grid = new Grid(seed);
    grid.tick();
    expect(grid.evolvedSize()).toEqual(0);
  });

  it("should return a block for a seed of block", function() {
    var seed = [ [0,0], [0,1], [1,0], [1,1] ];
    var grid = new Grid(seed);
    expect(grid.evolvedSize()).toEqual(4);
  });
  
  it("should create a neighbourhood from one cell", function() {
    var seed = [ [0,0] ];
    var grid = new Grid(seed);
    grid.init();
    expect(grid.filledUpGridSize()).toEqual(9);
  });
  
  it("should create a neighbourhood with no repeated cells", function() {
    var seed = [ [0,0], [0,1] ];
    var grid = new Grid(seed);
    grid.init();
    expect(grid.filledUpGridSize()).toEqual(12);
  });

  it("should have one neighbourh for a seed of one", function() {
    var seed = [ [0,0] ];
    var grid = new Grid(seed);
    grid.init();
    expect(grid.countNeighbours([-1,-1])).toEqual(1);
  });
});  

function Grid(seed) {

  var grid = {};
  var seed = seed;
  
  var evolvedGrid = seed;
  var filledUpGrid = [];

  var hood = [ [-1, -1], [-1, 0], [-1, 1],
               [0, -1], [0, 1],
               [1, -1], [1, 0], [1, 1] ];

  grid.init = function() {
    filledUpGrid = grid.fillUpGrid();
  };

  grid.fillUpGrid = function() {
  
    var filledGridObject = {}, //use an object to automatically reject duplicates
        filledGrid = [];
    for (var j = 0, seedLength = seed.length; j < seedLength; j++) {
      currentCell = seed[j];
      for (var i = 0, hoodLength = hood.length; i < hoodLength ; i++) {
        var currentHood = hood[i];
        var newCell = [currentCell[0] + currentHood[0], currentCell[1] + currentHood[1]];
        filledGridObject[newCell]=0;
      }
      filledGridObject[currentCell]=0;
    }

    for (var keyCell in filledGridObject){
      filledGrid.push(keyCell);
    }

    return filledGrid; 
  };
  
  grid.filledUpGridSize = function() {
    return filledUpGrid.length;
  };

  grid.countNeighbours = function(cell) {
    var neighbours = 0;
    for (var i = 0, hoodLength = hood.length; i < hoodLength ; i++) {
      var currentHood = hood[i];
      var potentialCell = [cell[0] + currentHood[0], cell[1] + currentHood[1]];
      //TODO if potential cell is in seed --> neighbours += neighbourhs;
    }
    return neighbours;
  };

  grid.tick = function() {
    if (seed.length < 3)
      evolvedGrid = [];
    else
      evolvedGrid = seed;

    return evolvedGrid;
  };

  grid.evolvedSize = function evolvedSize() {
    return evolvedGrid.length;
  };

  return grid;
};
