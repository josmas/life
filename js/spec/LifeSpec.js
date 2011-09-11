describe("The game of life", function() {
  it("should return an empty grid for a seed of 1", function() {
    var seed = { "0,0": 0 };
    var grid = new Grid(seed);
    grid.tick();
    expect(grid.evolvedSize()).toEqual(0);
  });

  it("should return a block for a seed of block", function() {
    var seed = { "0,0": 0, "0,1": 0, "1,0": 0, "1,1": 0 };
    var grid = new Grid(seed);
    grid.tick();
    expect(grid.evolvedSize()).toEqual(4);
  });
  
  it("should create a neighbourhood from one cell", function() {
    var seed = { "0,0": 0 };
    var grid = new Grid(seed);
    grid.init();
    expect(grid.filledUpGridSize()).toEqual(9);
  });
  
  it("should create a neighbourhood with no repeated cells", function() {
    var seed = { "0,0": 0, "0,1": 0 };
    var grid = new Grid(seed);
    grid.init();
    expect(grid.filledUpGridSize()).toEqual(12);
  });

  it("should have the right amonut of neighbours for a seed of one", function() {
    var seed = { "0,0": 0 };
    var grid = new Grid(seed);
    grid.init();
    expect(grid.countNeighbours([-1,-1])).toEqual(1);
    expect(grid.countNeighbours([-1,1])).toEqual(1);
    expect(grid.countNeighbours([0,0])).toEqual(0);
    expect(grid.countNeighbours([1,1])).toEqual(1);
  });

  it("should oscilate and oscilator", function() {
    var seed = { "0,1": 0, "1,1": 0, "2,1": 0 };
    var expectedOscilator = [ [1,0], [1,1], [1,2] ];
    var grid = new Grid(seed);
    grid.tick();
    expect(grid.evolvedSize()).toEqual(3);
    expect(grid.getEvolvedGrid()).toEqual(expectedOscilator);
  });
});  
