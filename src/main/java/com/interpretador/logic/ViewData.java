package com.interpretador.logic;

public final class ViewData {

    private final int[][] noteBrickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextNoteBrickData;

    //Constrói o tamanho da visão de acordo com os parâmetros passados.
    public ViewData(int[][] noteData, int xPosition, int yPosition, int[][] nextNoteData) {
        this.noteBrickData = noteData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextNoteBrickData = nextNoteData;
    }

    public int[][] getNoteData() {
        return MatrixOperations.copy(noteBrickData);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int[][] getNextNoteData() {
        return MatrixOperations.copy(nextNoteBrickData);
    }
}
