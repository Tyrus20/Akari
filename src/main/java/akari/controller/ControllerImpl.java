package akari.controller;

import akari.model.Model;
import akari.model.Puzzle;

public class ControllerImpl implements AlternateMvcController {
    private final Model model;

    public ControllerImpl(Model model) {
        this.model = model;
    }

    @Override
    public void clickNextPuzzle() {
        if (model.getActivePuzzleIndex() == model.getPuzzleLibrarySize() - 1) {
            model.setActivePuzzleIndex(0);
        } else {
            model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
        }
    }

    @Override
    public void clickPrevPuzzle() {
        if (model.getActivePuzzleIndex() == 0) {
            model.setActivePuzzleIndex(model.getPuzzleLibrarySize() - 1);
        } else {
            model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
        }
    }

    @Override
    public void clickRandPuzzle() {
        int randInt = model.getActivePuzzleIndex();
        while (randInt == model.getActivePuzzleIndex()) {
            randInt = (int) (Math.random() * model.getPuzzleLibrarySize());
        }
        model.setActivePuzzleIndex(randInt);
    }

    @Override
    public void clickResetPuzzle() {
        model.resetPuzzle();
    }

    @Override
    public void clickCell(int r, int c) {
        if (model.isLamp(r, c)) {
            model.removeLamp(r, c);
        } else {
            model.addLamp(r, c);
        }
    }

    @Override
    public boolean isLit(int r, int c) {
        return model.isLit(r, c);
    }

    @Override
    public boolean isLamp(int r, int c) {
        return model.isLamp(r, c);
    }

    @Override
    public boolean isLampIllegal(int r, int c) {
        return model.isLampIllegal(r, c);
    }

    @Override
    public boolean isClueSatisfied(int r, int c) {
        return model.isClueSatisfied(r, c);
    }

    @Override
    public boolean isSolved() {
        return model.isSolved();
    }

    @Override
    public Puzzle getActivePuzzle() {
        return model.getActivePuzzle();
    }

    @Override
    public int getActivePuzzleIndex() {
        return model.getActivePuzzleIndex();
    }

    @Override
    public int getPuzzleLibrarySize() {
        return model.getPuzzleLibrarySize();
    }
}
