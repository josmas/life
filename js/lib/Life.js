function Grid(seed) {

  var grid = {};
  var seed = seed;
  var evolvedGrid = [];
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
    for(var current in seed) {
      if (seed.hasOwnProperty(current)) {
        var currentCell = current.split(",");
        for (var i = 0, hoodLength = hood.length; i < hoodLength ; i++) {
          var currentHood = hood[i];
          var newCell = [parseInt(currentCell[0]) + currentHood[0], parseInt(currentCell[1]) + currentHood[1]];
          filledGridObject[newCell]=0;
        }
      filledGridObject[currentCell]=0;
      }
    }

    for (var keyCell in filledGridObject){
      filledGrid.push(keyCell.split(","));
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
      if (potentialCell in seed)
        neighbours += 1;
    }
    return neighbours;
  };

  grid.tick = function() {

    filledUpGrid = grid.fillUpGrid();
    for (var i = 0, filledUpGridlength = filledUpGrid.length; i < filledUpGridlength; i++) {
      var currentStringCell = filledUpGrid[i];
      var currentCell = [ parseInt(currentStringCell[0]), parseInt(currentStringCell[1]) ];
      var neighbours = grid.countNeighbours(currentCell);
      if ( (neighbours === 2 || neighbours === 3) && currentCell+"" in seed )
        evolvedGrid.push(currentCell);
      if (neighbours === 3 && !(currentCell in seed) )
        evolvedGrid.push(currentCell);
    };
    return evolvedGrid;
  };

  grid.evolvedSize = function evolvedSize() {
    return evolvedGrid.length;
  };

  grid.getEvolvedGrid = function () {
    return evolvedGrid;
  };
  return grid;
};
