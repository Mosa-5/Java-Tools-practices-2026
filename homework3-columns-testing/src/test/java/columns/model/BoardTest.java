package columns.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import columns.model.doubles.FakeRandomGenerator;
import columns.model.doubles.RecordingModelListener;

// Tests for Board. Note the field is indexed [column][row],
// columns 1..7 and rows 1..15.
class BoardTest {

	private Board board;
	private RecordingModelListener listener;

	@BeforeEach
	void setUp() {
		board = new Board();
		board.initFields();
		listener = new RecordingModelListener();
		board.setModelListener(listener);
	}

	private Figure figureAt(int x, int y, int c1, int c2, int c3) {
		Figure f = new Figure(new FakeRandomGenerator(0, 1, 2));
		f.x = x;
		f.y = y;
		f.c[1] = c1;
		f.c[2] = c2;
		f.c[3] = c3;
		return f;
	}

	// initBoard

	@Test
	void initBoardClearsFieldScoreLevelAndCounter() {
		board.level = 5;
		board.Score = 1234;
		board.figuresMatchedCounter = 9;
		board.newField[1][1] = 7;

		board.initBoard();

		assertEquals(0, board.level);
		assertEquals(0, board.Score);
		assertEquals(0, board.figuresMatchedCounter);
		assertEquals(0, board.newField[1][1]);
	}

	// pasteFigure

	@Test
	void pasteFigureWritesThreeColorsIntoColumn() {
		Figure f = figureAt(4, 5, 1, 2, 3);

		board.pasteFigure(f);

		assertEquals(1, board.newField[4][5]);
		assertEquals(2, board.newField[4][6]);
		assertEquals(3, board.newField[4][7]);
	}

	// dropFigure

	@Test
	void dropFigureFallsToBottomOnEmptyColumn() {
		Figure f = figureAt(4, 1, 1, 2, 3);

		board.dropFigure(f);

		// lands so the figure occupies rows 13,14,15 -> y = 13
		assertEquals(13, f.y);
	}

	@Test
	void dropFigureLandsOnTopOfExistingStack() {
		board.newField[4][15] = 6; // one block at the very bottom
		Figure f = figureAt(4, 1, 1, 2, 3);

		board.dropFigure(f);

		// must rest above the block at row 15 -> bottom cell at row 14 -> y = 12
		assertEquals(12, f.y);
	}

	// horizontal movement boundaries

	@Test
	void cannotMoveLeftAtLeftWall() {
		board.figure = figureAt(1, 1, 1, 2, 3);
		assertFalse(board.canMoveLeft());
	}

	@Test
	void canMoveLeftWhenSpaceIsFree() {
		board.figure = figureAt(4, 1, 1, 2, 3);
		assertTrue(board.canMoveLeft());
	}

	@Test
	void cannotMoveLeftWhenBlockingCellPresent() {
		board.figure = figureAt(4, 1, 1, 2, 3);
		board.newField[3][3] = 5; // cell at [x-1][y+2]
		assertFalse(board.canMoveLeft());
	}

	@Test
	void cannotMoveRightAtRightWall() {
		board.figure = figureAt(GameConfig.WIDTH, 1, 1, 2, 3); // x = 7
		assertFalse(board.canMoveRight());
	}

	@Test
	void canMoveRightWhenSpaceIsFree() {
		board.figure = figureAt(4, 1, 1, 2, 3);
		assertTrue(board.canMoveRight());
	}

	@Test
	void cannotMoveRightWhenBlockingCellPresent() {
		board.figure = figureAt(4, 1, 1, 2, 3);
		board.newField[5][3] = 5; // cell at [x+1][y+2]
		assertFalse(board.canMoveRight());
	}

	// vertical movement boundary

	@Test
	void figureMayMoveDownWhenSpaceBelowIsFree() {
		board.figure = figureAt(4, 1, 1, 2, 3);
		assertTrue(board.figureMayMoveDown());
	}

	@Test
	void figureMayNotMoveDownAtBottom() {
		board.figure = figureAt(4, GameConfig.DEPTH - 2, 1, 2, 3); // y = 13
		assertFalse(board.figureMayMoveDown());
	}

	@Test
	void figureMayNotMoveDownWhenCellBelowIsOccupied() {
		board.figure = figureAt(4, 1, 1, 2, 3);
		board.newField[4][4] = 5; // cell at [x][y+3]
		assertFalse(board.figureMayMoveDown());
	}

	// match detection

	@Test
	void findMatchesDetectsVerticalTriplet() {
		board.newField[3][5] = 5;
		board.newField[3][6] = 5;
		board.newField[3][7] = 5;

		board.findMatches();

		assertFalse(board.noChanges, "a match should clear the noChanges flag");
		assertEquals(1, listener.tripletCount);
		assertEquals(10, board.Score, "level 0 match scores (0+1)*10");
		assertEquals(1, board.figuresMatchedCounter);
	}

	@Test
	void findMatchesDetectsHorizontalTriplet() {
		board.newField[2][6] = 4;
		board.newField[3][6] = 4;
		board.newField[4][6] = 4;

		board.findMatches();

		assertFalse(board.noChanges);
		assertEquals(1, listener.tripletCount);
		assertEquals(10, board.Score);
	}

	@Test
	void findMatchesDetectsMainDiagonalTriplet() {
		board.newField[2][5] = 6;
		board.newField[3][6] = 6;
		board.newField[4][7] = 6;

		board.findMatches();

		assertFalse(board.noChanges);
		assertEquals(1, listener.tripletCount);
		assertEquals(10, board.Score);
	}

	@Test
	void findMatchesDetectsAntiDiagonalTriplet() {
		board.newField[4][5] = 2;
		board.newField[3][6] = 2;
		board.newField[2][7] = 2;

		board.findMatches();

		assertFalse(board.noChanges);
		assertEquals(1, listener.tripletCount);
		assertEquals(10, board.Score);
	}

	@Test
	void findMatchesIgnoresNonMatchingCells() {
		board.newField[3][5] = 4;
		board.newField[3][6] = 5;
		board.newField[3][7] = 6;

		board.findMatches();

		assertTrue(board.noChanges, "no triplet -> noChanges stays true");
		assertEquals(0, listener.tripletCount);
		assertEquals(0, board.Score);
	}

	@Test
	void matchScoreScalesWithLevel() {
		board.level = 2;
		board.newField[3][5] = 5;
		board.newField[3][6] = 5;
		board.newField[3][7] = 5;

		board.findMatches();

		assertEquals(30, board.Score, "level 2 match scores (2+1)*10");
	}

	// collapse / packing

	@Test
	void collapsePacksRemainingCellsDownward() {
		// working field has two cells with a gap between them
		board.oldField[3][10] = 5;
		board.oldField[3][12] = 6;

		board.collapse();

		// gravity packs them to the bottom, preserving relative order
		assertEquals(6, board.newField[3][15]);
		assertEquals(5, board.newField[3][14]);
		assertEquals(0, board.newField[3][13]);
		assertEquals(0, board.newField[3][10]);
	}

	@Test
	void collapseAddsDropScoreAndNotifiesListener() {
		board.Score = 100;
		board.DScore = 20;

		board.collapse();

		assertEquals(120, board.Score);
		assertEquals(120L, listener.lastScore);
		assertEquals(1, listener.scoreUpdateCount);
		assertEquals(1, listener.fieldUpdateCount);
	}

	// level changes

	@Test
	void levelIncreasesWhenMatchThresholdReached() {
		board.level = 0;
		board.figuresMatchedCounter = GameConfig.NEXT_LEVEL_THRESHOLD; // 33

		board.changeLevelIfNeeded();

		assertEquals(1, board.level);
		assertEquals(0, board.figuresMatchedCounter, "counter resets after a level up");
		assertEquals(1, listener.levelChanges.size());
		assertEquals(1, listener.levelChanges.get(0));
	}

	@Test
	void levelDoesNotChangeBelowThreshold() {
		board.level = 3;
		board.figuresMatchedCounter = GameConfig.NEXT_LEVEL_THRESHOLD - 1; // 32

		board.changeLevelIfNeeded();

		assertEquals(3, board.level);
		assertEquals(32, board.figuresMatchedCounter);
		assertTrue(listener.levelChanges.isEmpty());
	}

	@Test
	void levelIsCappedAtMaximum() {
		board.level = GameConfig.MAX_LEVEL; // 7
		board.figuresMatchedCounter = GameConfig.NEXT_LEVEL_THRESHOLD;

		board.changeLevelIfNeeded();

		assertEquals(GameConfig.MAX_LEVEL, board.level, "level never exceeds the maximum");
		assertEquals(1, listener.levelChanges.size());
	}

	// game over

	@Test
	void fieldIsNotFullWhenRowThreeIsEmpty() {
		board.newField[1][4] = 5; // block, but not in the game-over row
		assertFalse(board.isFieldFull());
	}

	@Test
	void fieldIsFullWhenRowThreeHasAnyBlock() {
		board.newField[5][3] = 5; // a block in the game-over check row
		assertTrue(board.isFieldFull());
	}
}
