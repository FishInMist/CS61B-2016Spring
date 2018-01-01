package editor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Editor extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;

    private Scene scene;
    private Group textRoot;
    private ScrollBar scrollBar = new ScrollBar();
    private FastLinkedList textBuffer;
    private Rectangle cursor;

    public Editor() {
        textBuffer = new FastLinkedList();
        textRoot = new Group();
        double cursorHeight = textBuffer.getCurrentNode().getLayoutBounds().getHeight();
        cursor = new Rectangle(1, cursorHeight);
        cursor.setX(5);
        cursor.setY(0);
        textBuffer.setCursor(cursor);
    }

    /** An EventHandler to handle keys that get pressed. */
    private class KeyEventHandler implements EventHandler<KeyEvent> {
        int textCenterX;
        int textCenterY;

        private static final int STARTING_FONT_SIZE = 20;
        private static final int STARTING_TEXT_POSITION_X = 5;
        private static final int STARTING_TEXT_POSITION_Y = 0;

        /** The Text to display on the screen. */
        private Text displayText = new Text(STARTING_TEXT_POSITION_X, STARTING_TEXT_POSITION_Y, "");
        private int fontSize = STARTING_FONT_SIZE;

        private String fontName = "Verdana";

        KeyEventHandler(final Group root) {
            textCenterX = STARTING_TEXT_POSITION_X;
            textCenterY = STARTING_TEXT_POSITION_Y;
            // Initialize some empty text and add it to root so that it will be displayed.
            displayText = new Text(textCenterX, textCenterY, "");
            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simpler!
            displayText.setTextOrigin(VPos.TOP);
            displayText.setFont(Font.font(fontName, fontSize));

            // All new Nodes need to be added to the root in order to be displayed.
            textRoot.getChildren().add(displayText);
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.isShortcutDown()) {
                if (keyEvent.getCode() == KeyCode.P) {
                    System.out.println("x: " + textBuffer.getCurrentX() + " y: " + textBuffer.getCurrentY());
                } else if (keyEvent.getCode() == KeyCode.EQUALS) {
                    fontSize = fontSize + 4;
                    textBuffer.textSizeUpdate(fontSize);
                    cursorPosUpdate();
                } else if (keyEvent.getCode() == KeyCode.MINUS) {
                    if (fontSize > 4) {
                        fontSize = fontSize - 4;
                        textBuffer.textSizeUpdate(fontSize);
                        cursorPosUpdate();
                    }
                }
            } else if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalization.
                String characterTyped = keyEvent.getCharacter();
                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                    // Ignore control keys, which have non-zero length, as well as the backspace
                    // key, which is represented as a character of value = 8 on Windows and enter key correspond to
                    // value = 13 on Windows.
                    Text textToAdd = new Text(textCenterX, textCenterY, "");
                    textToAdd.setText(characterTyped);
                    textToAdd.setTextOrigin(VPos.TOP);
                    textToAdd.setFont(Font.font(fontName, fontSize));
                    textBuffer.addChar(textToAdd);
                    textRoot.getChildren().add(textToAdd);
                    cursorPosUpdate();
                    keyEvent.consume();
                }
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we can check (KEY_TYPED events don't have an associated
                // KeyCode).
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.BACK_SPACE) {
                    Text textToDel = textBuffer.getCurrentNode();
                    textRoot.getChildren().remove(textToDel);
                    textBuffer.deleteChar();
                    cursorPosUpdate();
                } else if (code == KeyCode.LEFT) {
                    textBuffer.leftShift();
                    cursorPosUpdate();
                } else if (code == KeyCode.RIGHT) {
                    textBuffer.rightShift();
                    cursorPosUpdate();
                } else if (code == KeyCode.UP) {
                    textBuffer.upShift();
                    cursorPosUpdate();
                } else if (code == KeyCode.DOWN) {
                    textBuffer.downShift();
                    cursorPosUpdate();
                }
            }
        }

        public void cursorPosUpdate() {
            System.out.println(textBuffer.getCurrentX() + " " + textBuffer.getCurrentY());
            cursor.setX(textBuffer.getCurrentX());
            cursor.setY(textBuffer.getCurrentY());
            System.out.println(textBuffer.getCurrentNode().getText() + " " + textBuffer.getCurrentNode().getLayoutBounds().getHeight());
            cursor.setHeight(textBuffer.getCurrentNode().getLayoutBounds().getHeight());
        }
    }

    private class cursorBlinkEventHandler implements EventHandler<ActionEvent> {
        private int currentColorIndex = 0;
        private Color[] cursorColors = {Color.BLACK , Color.WHITE};
        cursorBlinkEventHandler() {
            changeColor();
        }
        private void changeColor() {
            cursor.setFill(cursorColors[currentColorIndex]);
            currentColorIndex = (currentColorIndex + 1) % cursorColors.length;
        }

        @Override
        public void handle(ActionEvent event) {
            changeColor();
        }
    }

    public void makeCursorColorChange() {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        cursorBlinkEventHandler cursorChange = new cursorBlinkEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    protected void cursorPosUpdate() {
        cursor.setX(textBuffer.getCurrentX());
        cursor.setY(textBuffer.getCurrentY());
        cursor.setHeight(textBuffer.getCurrentNode().getFont().getSize());
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();
        root.getChildren().add(textRoot);
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);
        textBuffer.setCurScene(scene);
        textBuffer.setScrollBar(scrollBar);
        // To get information about what keys the user is pressing, create an EventHandler.
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx.
        EventHandler<KeyEvent> keyEventHandler =
                new KeyEventHandler(root);

        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(WINDOW_HEIGHT);
        root.getChildren().add(scrollBar);
        double usableScreenWidth = WINDOW_WIDTH - scrollBar.getLayoutBounds().getWidth();
        scrollBar.setLayoutX(usableScreenWidth);
        scrollBar.setMin(0.0);
        scrollBar.setMax(0.0);


        //add a listener of the scene size change.
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                textBuffer.textPosUpdate();
                textBuffer.currentPosUpdate();
                cursorPosUpdate();
                scrollBar.setLayoutX(scene.getWidth() - scrollBar.getLayoutBounds().getWidth());
                textBuffer.scrollBarResize();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollBar.setPrefHeight(scene.getHeight());
                if (newValue.doubleValue() - oldValue.doubleValue() > 0) {
                    textBuffer.expandPage();
                    textBuffer.currentPosUpdate();
                }
                cursorPosUpdate();
                textBuffer.scrollBarResize();
            }
        });

        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                textBuffer.textMoveUp(newValue.doubleValue() - oldValue.doubleValue());
                textBuffer.currentPosUpdate();
                cursorPosUpdate();
            }
        });

        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);
        textRoot.getChildren().add(cursor);
        makeCursorColorChange();
        //scene.setOnMouseClicked(new MouseClickEventHandler());

        primaryStage.setTitle("Editor");

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}