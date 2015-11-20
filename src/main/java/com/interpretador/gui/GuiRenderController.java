package com.interpretador.gui;

import com.interpretador.logic.events.EventType;
import com.interpretador.logic.events.EventSource;
import com.interpretador.logic.events.MoveEvent;
import com.interpretador.logic.events.InputEventListener;
import com.interpretador.logic.DownNote;
import com.interpretador.logic.ViewData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import javafx.application.ConditionalFeature;
import javafx.scene.control.Label;

public class GuiRenderController implements Initializable {

    private static final int BRICK_NOTE_WIDTH = 20;
    private static int brickNoteHeight = 50;
    
    @FXML
    private GridPane musicSheetPanel;

    @FXML
    private Text scoreValue;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane nextNote;

    @FXML
    private GridPane notePanel;

    @FXML
    private ToggleButton pauseButton;

    @FXML
    private GameOverPanel gameOverPanel;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;
//  timeLineMusic define a velocidade da música. 
//  o andamento da música pode ser inserido aqui.
    private Timeline timeLineMusic;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        musicSheetPanel.setFocusTraversable(true);
        musicSheetPanel.requestFocus();
        musicSheetPanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            /** Inserir aqui os eventos do usuário quanto ao acerto das notas ou não.
             * 
             * @param keyEvent 
             */
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
//                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
//                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
//                        keyEvent.consume();
//                    }
//                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
//                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
//                        keyEvent.consume();
//                    }
//                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
//                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
//                        keyEvent.consume();
//                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
                if (keyEvent.getCode() == KeyCode.P) {
                    pauseButton.selectedProperty().setValue(!pauseButton.selectedProperty().getValue());
                }

            }
        });
        gameOverPanel.setVisible(false);
        pauseButton.selectedProperty().bindBidirectional(isPause);
        pauseButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    timeLineMusic.pause();
                    pauseButton.setText("Resume");
                } else {
                    timeLineMusic.play();
                    pauseButton.setText("Pause");
                }
            }
        });
        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
        scoreValue.setEffect(reflection);
    }

    /** Método que inicializa a visão da tela na pauta
     * 
     */
    public void initSheetView(int[][] boardMatrix, ViewData note) {
        
        Label noteName = new Label("5");
        brickNoteHeight = ThreadLocalRandom.current().nextInt();
        
        //Tamanhos da tela:
        int xSize = boardMatrix.length;
        int ySize = boardMatrix[0].length;
        
        //Inicia a tela com os seus tamanhos
        displayMatrix = new Rectangle[xSize][ySize];
        for (int i = 2; i < xSize; i++){
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_NOTE_WIDTH, BRICK_NOTE_WIDTH);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setArcHeight(2);
                rectangle.setArcWidth(2);
                displayMatrix[i][j] = rectangle;
                musicSheetPanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[note.getNoteData().length][note.getNoteData()[0].length];
        for (int i = 0; i < note.getNoteData().length; i++) {
            for (int j = 0; j < note.getNoteData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_NOTE_WIDTH, brickNoteHeight);
                rectangle.setFill(getFillColor(note.getNoteData()[i][j]));
                rectangles[i][j] = rectangle;
                //Enfiar o número do traste aqui
                notePanel.add(rectangle, j, i);
            }
        }
        notePanel.setLayoutX(musicSheetPanel.getLayoutX() + note.getxPosition() * notePanel.getVgap() + note.getxPosition() * BRICK_NOTE_WIDTH);
        notePanel.setLayoutY(-42 + musicSheetPanel.getLayoutY() + note.getyPosition() * notePanel.getHgap() + note.getyPosition() * BRICK_NOTE_WIDTH);

        generatePreviewPanel(note.getNextNoteData());

        //Aqui, será necessário descobrir quanto cada andamento em bpm's corresponde em milisegundos
        timeLineMusic = new Timeline(new KeyFrame(
                Duration.millis(60),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        
        //Aqui define-se quando o ciclo da animação deve acabar.
        timeLineMusic.setCycleCount(Timeline.INDEFINITE);
        timeLineMusic.play();
    }

    /** Método que pinta as notas que descem na verticalMusicSheet de acordo
     * com a corda de cada nota.
     * 
     */
    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                //returnPaint = Color.GREY;
                break;
            case 1:
                //returnPaint = Color.AQUA;
                returnPaint = Color.GREY;
                break;
            case 2:
                //returnPaint = Color.BLUEVIOLET;
                returnPaint = Color.PURPLE;
                break;
            case 3:
                //returnPaint = Color.DARKGREEN;
                returnPaint = Color.GREEN;
                break;
            case 4:
                //returnPaint = Color.YELLOW;
                returnPaint = Color.BLACK;
                break;
            case 5:
                //returnPaint = Color.RED;
                returnPaint = Color.RED;
                
                break;
            case 6:
                //returnPaint = Color.BEIGE;
                returnPaint = Color.GOLDENROD;
                break;
//            case 7:
//                returnPaint = Color.BURLYWOOD;
//                break;
            default:
                returnPaint = Color.TRANSPARENT;
                break;
        }
        return returnPaint;
    }

    private void generatePreviewPanel(int[][] nextBrickData) {
        nextNote.getChildren().clear();
        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_NOTE_WIDTH, BRICK_NOTE_WIDTH);
                setRectangleData(nextBrickData[i][j], rectangle);
                if (nextBrickData[i][j] != 0) {
                    nextNote.add(rectangle, j, i);
                }
            }
        }
    }

    private void refreshNote(ViewData note) {
        if (isPause.getValue() == Boolean.FALSE) {
            notePanel.setLayoutX(musicSheetPanel.getLayoutX() + note.getxPosition() * notePanel.getVgap() + note.getxPosition() * BRICK_NOTE_WIDTH);
            notePanel.setLayoutY(-42 + musicSheetPanel.getLayoutY() + note.getyPosition() * notePanel.getHgap() + note.getyPosition() * BRICK_NOTE_WIDTH);
            for (int i = 0; i < note.getNoteData().length; i++) {
                for (int j = 0; j < note.getNoteData()[i].length; j++) {
                    setRectangleData(note.getNoteData()[i][j], rectangles[i][j]);
                }
            }
            generatePreviewPanel(note.getNextNoteData());
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownNote downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshNote(downData.getViewData());
        }
        musicSheetPanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreValue.textProperty().bind(integerProperty.asString());
    }

    public void gameOver() {
        timeLineMusic.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);

    }

    public void newGame(ActionEvent actionEvent) {
        timeLineMusic.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewMusicSheet();
        musicSheetPanel.requestFocus();
        timeLineMusic.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void pauseGame(ActionEvent actionEvent) {
        musicSheetPanel.requestFocus();
    }
}
