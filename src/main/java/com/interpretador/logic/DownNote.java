package com.interpretador.logic;

public final class DownNote {
    private final ClearRow clearRow;
    private final ViewData viewData;

    public DownNote(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    public ClearRow getClearRow() {
        return clearRow;
    }

    public ViewData getViewData() {
        return viewData;
    }
}
