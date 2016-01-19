package com.interpretador.app;

import com.interpretador.logic.VerticalMusicSheet;
import com.interpretador.logic.ClearRow;
import com.interpretador.logic.ViewData;
import com.interpretador.logic.DownNote;
import com.interpretador.logic.SimpleSheet;
import com.interpretador.gui.GuiRenderController;
import com.interpretador.logic.events.EventSource;
import com.interpretador.logic.events.InputEventListener;
import com.interpretador.logic.events.MoveEvent;

public class InterpretadorController implements InputEventListener {

    //private VerticalMusicSheet board = new SimpleSheet(25, 10);
    private VerticalMusicSheet board = new SimpleSheet(25, 10);
    
    private final GuiRenderController viewGuiController;
    
    public InterpretadorController(GuiRenderController c) {
        viewGuiController = c;
        board.createNewNote();
        viewGuiController.setEventListener(this);
        viewGuiController.initSheetView(board.getVerticalMusicSheetMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    @Override
    public DownNote onDownEvent(MoveEvent event) {
        boolean canMove = board.moveNoteDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeNoteToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewNote()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getVerticalMusicSheetMatrix());

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new DownNote(clearRow, board.getViewData());
    }

//    @Override
//    public ViewData onLeftEvent(MoveEvent event) {
//        board.moveNoteLeft();
//        return board.getViewData();
//    }

//    @Override
//    public ViewData onRightEvent(MoveEvent event) {
//        board.moveNoteRight();
//        return board.getViewData();
//    }

//    @Override
//    public ViewData onRotateEvent(MoveEvent event) {
//        board.rotateLeftBrick();
//        return board.getViewData();
//    }


    @Override
    public void createNewMusicSheet() {
        board.newMusicSheet();
        viewGuiController.refreshGameBackground(board.getVerticalMusicSheetMatrix());
    }

    @Override
    public DownNote onAEvent(MoveEvent event) {
        //Detonar a nota A
        System.out.println("Apertando A");
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DownNote onSEvent(MoveEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DownNote onDEvent(MoveEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DownNote onFEvent(MoveEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DownNote onGEvent(MoveEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DownNote onHEvent(MoveEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
