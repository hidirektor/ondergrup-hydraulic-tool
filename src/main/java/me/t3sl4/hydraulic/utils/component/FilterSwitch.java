package me.t3sl4.hydraulic.utils.component;

import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Objects;

public class FilterSwitch extends Region {
    private static final double ARTBOARD_WIDTH  = 90;
    private static final double ARTBOARD_HEIGHT = 27;
    private static final double CENTER = ARTBOARD_WIDTH / 2;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH  = 50;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 800;

    private static final int BACKGROUND_RECT_ARC_HEIGHT = 67;
    private static final int BACKGROUND_RECT_ARC_WIDTH = 67;
    private static final int TOGGLE_CIRCLE_OFFSET = 28;
    private static final int TOGGLE_CIRCLE_RADIUS = 14;

    private Rectangle backgroundRectangle;
    private Circle toggleCircle;
    private TranslateTransition translateAnimation;

    private final BooleanProperty isToggled = new SimpleBooleanProperty(false);

    private Pane drawingPane;

    public FilterSwitch() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBinding();
    }

    private void initializeSelf() {
        String fonts = Objects.requireNonNull(getClass().getResource("/assets/fonts/fonts.css")).toExternalForm();
        getStylesheets().add(fonts);

        String stylesheet = Objects.requireNonNull(getClass().getResource("/me/t3sl4/hydraulic/styling/filterswitch.css")).toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("day-night-toggle-button");
    }

    private void initializeParts() {
        backgroundRectangle = new Rectangle(centerRectangleX(CENTER, 126), centerRectangleY(CENTER, 67), 126, 67);
        backgroundRectangle.setArcHeight(BACKGROUND_RECT_ARC_HEIGHT);
        backgroundRectangle.setArcWidth(BACKGROUND_RECT_ARC_WIDTH);
        backgroundRectangle.getStyleClass().add("background-rectangle");

        toggleCircle = new Circle(TOGGLE_CIRCLE_RADIUS);
        toggleCircle.setCenterX(CENTER - TOGGLE_CIRCLE_OFFSET);
        toggleCircle.setCenterY(CENTER);
        toggleCircle.getStyleClass().add("toggle-circle");

        translateAnimation = new TranslateTransition(Duration.seconds(0.35));
        translateAnimation.setNode(toggleCircle);
    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(backgroundRectangle, toggleCircle);
        getChildren().add(drawingPane);
    }

    private void setupEventHandlers() {
        toggleCircle.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    setIsToggled(!isIsToggled());
                }
            }
        });

        backgroundRectangle.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    setIsToggled(!isIsToggled());
                }
            }
        });
    }

    private void setupValueChangeListeners() {
        isToggled.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                translateAnimation.setToX(56);
                backgroundRectangle.getStyleClass().add("background-rectangle-active");
                toggleCircle.getStyleClass().add("toggle-circle-active");
            } else {
                translateAnimation.setToX(0);
                backgroundRectangle.getStyleClass().remove("background-rectangle-active");
                toggleCircle.getStyleClass().remove("toggle-circle-active");
            }
            translateAnimation.play();
        });
    }

    private void setupBinding() {
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    private void resize() {
        Insets padding         = getPadding();
        double availableWidth  = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

        double scalingFactor = width / ARTBOARD_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    private double centerRectangleX(double center, double rectangleWidth){
        return center - (rectangleWidth / 2);
    }

    private double centerRectangleY(double center, double rectangleHeight){
        return center - (rectangleHeight / 2);
    }

    @Override
    protected double computeMinWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return MINIMUM_WIDTH + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return MINIMUM_HEIGHT + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return ARTBOARD_WIDTH + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return ARTBOARD_HEIGHT + verticalPadding;
    }

    public boolean isIsToggled() {
        return isToggled.get();
    }

    public BooleanProperty isToggledProperty() {
        return isToggled;
    }

    public void setIsToggled(boolean isToggled) {
        this.isToggled.set(isToggled);
    }
}
