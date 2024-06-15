package ru.java.experimental.Fx._2d;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SimplePhysics extends Application {
    public static final double G = 1;

    private double planetVelocityX = 0.1;
    private double planetVelocityY = 2.7;
    private double planetAccelerationX = 0;
    private double planetAccelerationY = 0;
    private final double planetMass = 40;
    private final double sunMass = 1000;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Circle sun = new Circle(50);
        sun.setCenterX(700);
        sun.setCenterY(400);
        sun.setFill(Color.YELLOW);
        Circle planet = new Circle(25);
        planet.setFill(Color.DEEPSKYBLUE);
        planet.setCenterX(700+200);
        planet.setCenterY(400);
        Group group = new Group(sun, planet);
        Scene scene = new Scene(group, 1400, 800);
        scene.setFill(Color.BLACK);

        System.out.println(getGravityForce(1, 1, 1));

        primaryStage.setTitle("2d physics");
        primaryStage.setScene(scene);
        primaryStage.show();

        Animation(sun, planet, group);
    }

    private void Animation(Circle sun, Circle planet, Group group) {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double force = getGravityForce(planetMass, sunMass, getDistance(sun, planet));
                double dx = sun.getCenterX() - planet.getCenterX();
                double dy = sun.getCenterY() - planet.getCenterY();
                double dr = getDistance(sun, planet);
                planetAccelerationX = force * (dx / dr) / planetMass;
                planetAccelerationY = force * (dy / dr) / planetMass;

                planetVelocityX += planetAccelerationX;
                planetVelocityY += planetAccelerationY;

                planet.setCenterX(planet.getCenterX() + planetVelocityX);
                planet.setCenterY(planet.getCenterY() + planetVelocityY);

                Circle circle = new Circle(1);
                circle.setFill(Color.RED);
                circle.setCenterX(planet.getCenterX());
                circle.setCenterY(planet.getCenterY());
                group.getChildren().add(circle);
            }
        };

        animationTimer.start();
    }

    private double getDistance(Circle sun, Circle planet) {
        double x0 = planet.getCenterX();
        double y0 = planet.getCenterY();
        double x1 = sun.getCenterX();
        double y1 = sun.getCenterY();
        return Math.sqrt((x1-x0)*(x1-x0) + (y1-y0)*(y1-y0));
    }

    public static double getGravityForce(double m, double M, double distance) {
        return G * m * M / (distance * distance);
    }
}
