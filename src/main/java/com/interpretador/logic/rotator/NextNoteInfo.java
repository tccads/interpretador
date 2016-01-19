package com.interpretador.logic.rotator;

import com.interpretador.logic.MatrixOperations;

public final class NextNoteInfo {

    private final int[][] shape;
    private final int position;

    public NextNoteInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    public int getPosition() {
        return position;
    }
}
