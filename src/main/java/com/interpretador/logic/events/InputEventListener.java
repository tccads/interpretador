package com.interpretador.logic.events;

import com.interpretador.logic.ViewData;
import com.interpretador.logic.DownNote;

/**
 * 
 * 
 */
public interface InputEventListener {

    DownNote onDownEvent(MoveEvent event);
    
    DownNote onAEvent(MoveEvent event);
    
    DownNote onSEvent(MoveEvent event);
    
    DownNote onDEvent(MoveEvent event);
    
    DownNote onFEvent(MoveEvent event);

    DownNote onGEvent(MoveEvent event);
    
    DownNote onHEvent(MoveEvent event);
    
//    ViewData onLeftEvent(MoveEvent event);

//    ViewData onRightEvent(MoveEvent event);

//    ViewData onRotateEvent(MoveEvent event);

    void createNewMusicSheet();
}
