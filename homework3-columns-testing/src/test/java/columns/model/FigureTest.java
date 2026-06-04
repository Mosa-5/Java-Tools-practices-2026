package columns.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import columns.model.doubles.FakeRandomGenerator;

// Movement and rotation tests for Figure.
class FigureTest {

	private Figure figureWithColors(int c1, int c2, int c3) {
		Figure f = new Figure(new FakeRandomGenerator(0, 1, 2));
		f.c[1] = c1;
		f.c[2] = c2;
		f.c[3] = c3;
		return f;
	}

	@Test
	void newFigureStartsAtTopMiddle() {
		Figure f = new Figure(new FakeRandomGenerator(0, 1, 2));
		assertEquals(4, f.x); // WIDTH/2 + 1
		assertEquals(1, f.y);
		assertEquals(0, f.c[0]);
	}

	@Test
	void constructorMapsRandomValuesToColorsInOneToSevenRange() {
		// abs(v) % 7 + 1, so 0,1,2 give colours 1,2,3
		Figure f = new Figure(new FakeRandomGenerator(0, 1, 2));
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, f.c);
	}

	@Test
	void constructorKeepsColorsInRangeEvenForNegativeRandom() {
		Figure f = new Figure(new FakeRandomGenerator(-100, -8, -1));
		for (int i = 1; i <= 3; i++) {
			assertTrue(f.c[i] >= 1 && f.c[i] <= 7, "colour " + i + " out of range: " + f.c[i]);
		}
	}

	@Test
	void moveRightIncreasesX() {
		Figure f = figureWithColors(1, 2, 3);
		int before = f.x;
		f.moveRight();
		assertEquals(before + 1, f.x);
	}

	@Test
	void moveLeftDecreasesX() {
		Figure f = figureWithColors(1, 2, 3);
		int before = f.x;
		f.moveLeft();
		assertEquals(before - 1, f.x);
	}

	@Test
	void moveDownIncreasesY() {
		Figure f = figureWithColors(1, 2, 3);
		int before = f.y;
		f.moveDown();
		assertEquals(before + 1, f.y);
	}

	@Test
	void rotateUpShiftsColorsUpward() {
		Figure f = figureWithColors(1, 2, 3);
		f.rotateUp();
		// (c1,c2,c3): (1,2,3) -> (2,3,1)
		assertArrayEquals(new int[] { 0, 2, 3, 1 }, f.c);
	}

	@Test
	void rotateDownShiftsColorsDownward() {
		Figure f = figureWithColors(1, 2, 3);
		f.rotateDown();
		// (c1,c2,c3): (1,2,3) -> (3,1,2)
		assertArrayEquals(new int[] { 0, 3, 1, 2 }, f.c);
	}

	@Test
	void rotateDownUndoesRotateUp() {
		Figure f = figureWithColors(4, 5, 6);
		f.rotateUp();
		f.rotateDown();
		assertArrayEquals(new int[] { 0, 4, 5, 6 }, f.c);
	}

	@Test
	void threeRotateUpsReturnToStart() {
		Figure f = figureWithColors(4, 5, 6);
		f.rotateUp();
		f.rotateUp();
		f.rotateUp();
		assertArrayEquals(new int[] { 0, 4, 5, 6 }, f.c);
	}
}
