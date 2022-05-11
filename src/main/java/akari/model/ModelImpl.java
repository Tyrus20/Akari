package akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private final List<ModelObserver> observers;
  private boolean[][] activePuzzleLamps;
  private Puzzle activePuzzle;
  private int activePuzzleIndex;

  public ModelImpl(PuzzleLibrary library) {
    this.library = library;
    this.activePuzzle = library.getPuzzle(0);
    this.activePuzzleIndex = 0;
    this.activePuzzleLamps = new boolean[activePuzzle.getHeight()][activePuzzle.getWidth()];
    this.observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r < 0 || r >= activePuzzle.getHeight() || c < 0 || c >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException();
    } else if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      activePuzzleLamps[r][c] = true;
      notify(this);
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r < 0 || r >= activePuzzle.getHeight() || c < 0 || c >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException();
    } else if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      activePuzzleLamps[r][c] = false;
      notify(this);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r < 0 || r >= activePuzzle.getHeight() || c < 0 || c >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException();
    } else if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      for (int newR = r; newR < activePuzzle.getHeight(); newR++) {
        if (activePuzzleLamps[newR][c]) {
          return true;
        } else if (activePuzzle.getCellType(newR, c) != CellType.CORRIDOR) {
          break;
        }
      }
      for (int newR = r; newR >= 0; newR--) {
        if (activePuzzleLamps[newR][c]) {
          return true;
        } else if (activePuzzle.getCellType(newR, c) != CellType.CORRIDOR) {
          break;
        }
      }
      for (int newC = c; newC < activePuzzle.getWidth(); newC++) {
        if (activePuzzleLamps[r][newC]) {
          return true;
        } else if (activePuzzle.getCellType(r, newC) != CellType.CORRIDOR) {
          break;
        }
      }
      for (int newC = c; newC >= 0; newC--) {
        if (activePuzzleLamps[r][newC]) {
          return true;
        } else if (activePuzzle.getCellType(r, newC) != CellType.CORRIDOR) {
          break;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r < 0 || r >= activePuzzle.getHeight() || c < 0 || c >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException();
    } else if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      return activePuzzleLamps[r][c];
    }
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r < 0 || r >= activePuzzle.getHeight() || c < 0 || c >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException();
    } else if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else {
      for (int newR = r + 1; newR < activePuzzle.getHeight(); newR++) {
        if (activePuzzleLamps[newR][c]) {
          return true;
        } else if (activePuzzle.getCellType(newR, c) != CellType.CORRIDOR) {
          break;
        }
      }
      for (int newR = r - 1; newR >= 0; newR--) {
        if (activePuzzleLamps[newR][c]) {
          return true;
        } else if (activePuzzle.getCellType(newR, c) != CellType.CORRIDOR) {
          break;
        }
      }
      for (int newC = c + 1; newC < activePuzzle.getWidth(); newC++) {
        if (activePuzzleLamps[r][newC]) {
          return true;
        } else if (activePuzzle.getCellType(r, newC) != CellType.CORRIDOR) {
          break;
        }
      }
      for (int newC = c - 1; newC >= 0; newC--) {
        if (activePuzzleLamps[r][newC]) {
          return true;
        } else if (activePuzzle.getCellType(r, newC) != CellType.CORRIDOR) {
          break;
        }
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(activePuzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return activePuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException();
    } else {
      activePuzzleIndex = index;
      resetPuzzle();
    }
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    Puzzle puzzle = getActivePuzzle();
    activePuzzle = getActivePuzzle();
    this.activePuzzleLamps = new boolean[puzzle.getHeight()][puzzle.getWidth()];
    notify(this);
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < activePuzzle.getHeight(); i++) {
      for (int j = 0; j < activePuzzle.getWidth(); j++) {
        if (activePuzzle.getCellType(i, j) == CellType.CLUE && !isClueSatisfied(i, j)) {
          return false;
        }
        if (activePuzzle.getCellType(i, j) == CellType.CORRIDOR && !isLit(i, j)) {
          return false;
        }
        if (activePuzzleLamps[i][j] && isLampIllegal(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    int adj = 0;
    if (r < 0 || r >= activePuzzle.getHeight() || c < 0 || c >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException();
    } else if (activePuzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    if (r + 1 < activePuzzle.getHeight()) {
      if (activePuzzleLamps[r + 1][c]) {
        adj++;
      }
    }
    if (c + 1 < activePuzzle.getWidth()) {
      if (activePuzzleLamps[r][c + 1]) {
        adj++;
      }
    }
    if (r - 1 >= 0) {
      if (activePuzzleLamps[r - 1][c]) {
        adj++;
      }
    }
    if (c - 1 >= 0) {
      if (activePuzzleLamps[r][c - 1]) {
        adj++;
      }
    }
    return adj == activePuzzle.getClue(r, c);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notify(Model model) {
    for (ModelObserver observer : observers) {
      observer.update(model);
    }
  }
}
