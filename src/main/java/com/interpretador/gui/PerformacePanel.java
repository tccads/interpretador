package com.interpretador.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class PerformacePanel extends BorderPane {

    public PerformacePanel() {
        final Label performaceLabel = new Label("");
        performaceLabel.getStyleClass().add("gameOverStyle");
        //setCenter(performaceLabel);
        setBottom(performaceLabel);
    }

}
