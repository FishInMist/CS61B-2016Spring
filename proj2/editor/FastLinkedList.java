package editor;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class FastLinkedList {

    private int currentPos;
    private Node sentinel;
    private Node currentNode;
    private double currentX;
    private double currentY;
    private Scene curScene;
    private Rectangle cursor;
    private ScrollBar scrollBar;

    private class Node{
        Text node;
        Node prev, next;

        public Node(Text node, Node prev, Node next) {
            this.node = node;
            this.prev = prev;
            this.next = next;
        }
    }
    private class MouseClickEventHandler implements EventHandler<MouseEvent> {
        private Node nodeClicked;
        public MouseClickEventHandler(Node nodeClicked) {
            this.nodeClicked = nodeClicked;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            System.out.println("clicked: " + nodeClicked.node.getText());
            double centerX = nodeClicked.node.getX() + nodeClicked.node.getLayoutBounds().getWidth() / 2;
            if (mouseEvent.getX() >= centerX) {
                currentNode = nodeClicked;
            } else {
                currentNode = nodeClicked.prev;
            }

            currentPosUpdate();
            cursor.setX(currentX);
            cursor.setY(currentY);
            cursor.setHeight(currentNode.node.getFont().getSize());
        }
    }

    public FastLinkedList() {
        Text sentinelNode = new Text(5, 0, "Sentinel");
        sentinelNode.setFont(Font.font("Verdana", 20));
        sentinel = new Node(sentinelNode, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        currentNode = sentinel;
        currentX = 5.0;
        currentY = 0.0;
    }

    public void setCurScene(Scene curScene) {
        this.curScene = curScene;
    }

    public void setScrollBar(ScrollBar scrollBar) {
        this.scrollBar = scrollBar;
    }

    public void setCursor(Rectangle cursor) {
        this.cursor = cursor;
    }

    public void addChar(Text x) {
        Node nodeToAdd = new Node(x, currentNode, currentNode.next);
        x.setX(currentX);
        x.setY(currentY);
        x.setOnMouseClicked(new MouseClickEventHandler(nodeToAdd));
        currentNode.next.prev = nodeToAdd;
        currentNode.next = nodeToAdd;
        currentNode = nodeToAdd;
        textPosUpdate();
        currentPosUpdate();
        topDetection();
        bottomDetection();
        testPrint();
    }

    public void deleteChar() {
        if (currentNode != sentinel) {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            currentNode = currentNode.prev;
        }
        textPosUpdate();
        currentPosUpdate();
        topDetection();
        bottomDetection();
        testPrint();
    }

    public int currentPos() {
        return currentPos;
    }

    public Text getCurrentNode() {
        return currentNode.node;
    }

    public double getCurrentX() {
        return currentX;
    }

    public double getCurrentY() {
        return currentY;
    }

    public void leftShift() {
        if (currentNode != sentinel) {
            currentNode = currentNode.prev;
            currentPosUpdate();
            topDetection();
            bottomDetection();
        }
        testPrint();
    }

    public void rightShift() {
        if(currentNode.next != sentinel) {
            currentNode = currentNode.next;
            currentPosUpdate();
            topDetection();
            bottomDetection();
        }
        testPrint();
    }

    public void upShift() {
        Node runner =currentNode;
        while (runner.node.getY() == currentNode.node.getY()) {
            runner = runner.prev;
            if (runner == sentinel) {
                break;
            }
        }
        if (runner != sentinel) {
            while (runner != sentinel && runner.node.getX() >= currentX) {
                runner = runner.prev;
            }
            currentNode = runner.prev;
            currentPosUpdate();
            topDetection();
            bottomDetection();
        }
        testPrint();
    }

    public void downShift() {
        Node runner = currentNode;
        while (runner.node.getY() == currentNode.node.getY()) {
            runner = runner.next;
            if (runner == sentinel) {
                break;
            }
        }
        if (runner != sentinel) {
            while (runner != sentinel && runner.node.getText().charAt(0) != 13 && runner.node.getX() <= currentX) {
                runner = runner.next;
            }
            currentNode = runner.prev;
            currentPosUpdate();
            bottomDetection();
        }
        testPrint();
    }


    protected void currentPosUpdate() {
        currentX = Math.round(currentNode.node.getX() + currentNode.node.getLayoutBounds().getWidth());
        currentY = currentNode.node.getY();
        if (currentNode.node.getText().charAt(0) == 13) {
            currentX = 5.0;
            currentY = Math.round(currentY + currentNode.node.getLayoutBounds().getHeight() / 2);
        }
        if (currentNode == sentinel) {
            currentX = sentinel.node.getX();
            currentY = sentinel.node.getY();
        }
    }

    protected void topDetection() {
        if (currentY < 0) {
            double distanceY = currentY;
            textMoveUp(distanceY);
            scrollBarResize();
            scrollBar.setValue(-sentinel.node.getY());
            textMoveUp(-distanceY);
            currentPosUpdate();
        }
    }

    protected void bottomDetection() {
        double Height;
        if (currentNode.node.getText().charAt(0) == 13) {
            Height = currentNode.node.getLayoutBounds().getHeight() / 2;
        } else {
            Height = currentNode.node.getLayoutBounds().getHeight();
        }
        if (currentY + Height> curScene.getHeight()) {
            double distanceY = currentY + Height - curScene.getHeight();
            System.out.println("bottom Moving up: " + distanceY);
            textMoveUp(distanceY);
            scrollBarResize();
            scrollBar.setValue(-sentinel.node.getY());
            textMoveUp(-distanceY);
            currentPosUpdate();
        }
    }

    protected void expandPage() {
        if (sentinel.node.getY() < 0 && sentinel.prev.node.getY() < curScene.getHeight()) {
            double disTop = - sentinel.node.getY();
            double disBottom = curScene.getHeight() - sentinel.prev.node.getY();
            double distance = Math.min(disTop, disBottom);
            textMoveUp(-distance);
        }
    }

    protected double getTextHeight() {
        double posY;
        posY = Math.round(sentinel.prev.node.getY() + sentinel.prev.node.getLayoutBounds().getHeight());
        return posY - sentinel.node.getY();
    }

    protected double getSentienlPos() {
        return sentinel.node.getY();
    }

    protected void scrollBarResize() {
        double textHeight = getTextHeight();
        if (textHeight > curScene.getHeight()) {
            scrollBar.setMax(textHeight - curScene.getHeight());
        } else {
            scrollBar.setMax(0.0);
        }
    }

    protected void textSizeUpdate(int textSize) {
        Node runner = sentinel.next;
        while(runner != sentinel) {
            runner.node.setFont(Font.font(textSize));
            runner = runner.next;
        }
        textPosUpdate();
        currentPosUpdate();
    }

    protected void textPosUpdate() {
        double xPos = sentinel.node.getX();
        double yPos = sentinel.node.getY();
        Node runner = sentinel.next;
        while (runner != sentinel) {
            runner.node.setX(xPos);
            runner.node.setY(yPos);
            if (runner.node.getText().charAt(0) != 13) {
                xPos = Math.round(xPos + runner.node.getLayoutBounds().getWidth());
                if (xPos > curScene.getWidth() - 30.0) {
                    Node runner2 = runner;
                    while(runner2.node.getText().charAt(0) !=  ' ') {
                        runner2 = runner2.prev;
                        if (runner2 == sentinel) {
                            break;
                        }
                    }
                    if (runner2 != sentinel && runner2.node.getY() == runner.node.getY()) {
                        runner = runner2;
                    }
                    xPos = 5.0;
                    yPos = Math.round(runner.node.getY() + runner.node.getLayoutBounds().getHeight());
                }
            } else {
                xPos = 5.0;
                yPos = Math.round(runner.node.getY() + runner.node.getLayoutBounds().getHeight() / 2);
            }
            runner = runner.next;
        }
    }

    protected void textMoveUp(double distance) {
        Node runner = sentinel.next;
        while (runner.next != sentinel) {
            runner.node.setY(runner.node.getY() - distance);
            runner = runner.next;
        }
        runner.node.setY(runner.node.getY() - distance);
        sentinel.node.setY(sentinel.node.getY() - distance);
    }

    private void testPrint() {
        System.out.println("Current Node: " + currentNode.node.getText());
        System.out.println("current node y: " + currentNode.node.getY() + " " + currentNode.node.getFont().getSize());
        if (currentNode.node.getText().charAt(0) == 13) {
            System.out.println("we meet a enter button");
        }
        System.out.println("Next Node: " + currentNode.next.node.getText());
        //System.out.println("Sentinel pos: :" + sentinel.node.getY());
    }
}