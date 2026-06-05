# Assignment 3 Test Report

## What I did

I wrote unit tests for the refactored Columns game, covering the model
(`Figure`, `Board`) and the controller events (`GameController.processEvent`).
There are 51 tests and they all pass. Nothing waits on real time, randomness or a
real screen, so the suite runs in under a second.

I did not change any production code. The model classes are package-private but I
could reach them by putting the tests in the same package (`columns.model`), and
`Platform`, `Screen`, `RandomGenerator` and `ModelListener` are already
interfaces, so I just wrote fake versions of them. There was nothing I needed to
make public.

## Framework

JUnit 5 (Jupiter), run with Maven. I also set up JaCoCo for the coverage bonus.

## Test doubles

All hand-written, in `columns.model.doubles`:

- `FakeRandomGenerator` returns a fixed sequence of ints, so figure colours are
  predictable.
- `FakeScreen` records draw calls as strings instead of drawing anything, so I
  can check that drawing happened without opening a window.
- `FakePlatform` never sleeps and time only moves when I tell it to. It also lets
  me script key presses, which I needed for the pause test.
- `RecordingModelListener` remembers which callbacks the board fired (level
  changes, triplets, field updates, score updates).

One small thing that bit me: I first stored the recorded score as a `Long`, and
`assertEquals(120, lastScore)` failed because it compared an `Integer` to a
`Long`. I store it as a primitive `long` now.

## What the tests check

`Figure`: start position, colours mapped into 1..7, moving left/right/down, and
both rotations. I also check that rotate down undoes rotate up and that three
rotate ups come back to the start.

`Board`: initBoard clearing everything, pasteFigure writing the three colours,
dropFigure landing on an empty column and on top of a stack, the left/right/down
boundaries (walls and occupied cells), match detection for vertical, horizontal
and both diagonals, scoring (a match is worth `(level+1)*10`, checked at level 0
and 2), collapse packing cells down and adding the drop score, level up with the
threshold and the max-level cap, and isFieldFull for game over (row 3).

`GameController` events: left/right move when allowed and stop at walls and
blocks, up/down rotate, drop sends the figure to the bottom and resets the timer,
level up/down stay inside 0..7 and reset the match counter, a move actually draws
through the fake screen, and pause (optional) loops once then exits when I script
a key press.

## What was hard

The main game loop (`runGameLoop`) was the hard part. It mixes figure creation,
falling, event polling, time checks, matching, collapse, drawing and game-over in
one big nested loop, and its exit depends on time comparisons. Testing it end to
end would be fragile, so I tested the smaller methods and the events on their own
instead, which is what the assignment suggested.

Pause was annoying because it spins on `while (!isKeyPressed())` and a naive fake
hangs forever. I gave the fake platform a small queue of scripted answers so the
loop ends.

Drawing was the other one. It goes through a lot of `drawBox` calls with computed
pixel positions. Asserting exact pixels would just be testing implementation
details, so I only check that drawing happened and let the position/score
assertions do the real checking.

## Design problems the tests showed

Every controller event also draws and some touch timing, so even a plain "move
left" can only be tested with a fake screen and a fake platform present. The
event logic isn't really separable from its side effects.

The game loop is one big method with a time-based exit, which is why I couldn't
cover it and why the coverage on `GameController` and `View` is only partial.

`Board` and `Figure` expose their fields publicly. That made setup easy but it's
also a smell, so I tried to assert behaviour (figure moved, cells cleared, score
went up) instead of poking at every field.

## Coverage (bonus)

JaCoCo is wired into the `test` phase. Run `mvn test`, then open
`target/site/jacoco/index.html`.

| Class | Instruction | Line |
|---|---:|---:|
| `Board` | 100% | 100% |
| `Figure` | 100% | 100% |
| `GameEvent` | 100% | 100% |
| `GameController` | ~50% | ~51% |
| `View` | ~49% | ~52% |
| Overall | ~74% | ~72% |

The core rules (`Board`, `Figure`) are fully covered. The uncovered part is the
game loop, the time/delay polling, the full-screen drawing helpers and applet
glue, which the assignment said not to test directly.

## How to run

```
cd homework3-columns-testing
mvn test
```

## Project layout note

This is a self-contained Maven project. It bundles a copy of the
`columns.model` and `columns.model.kernel` source so the tests compile and run
and so JaCoCo can measure the code. I left out the applet classes (`Columns`,
`AppletScreen`) because they are pure AWT glue and the assignment says to ignore
them. To put this into the course repo, the test sources and the doubles can go
next to the existing `Java2026/src/columns` source with JUnit 5 on the classpath.
