package com.fm.scheduling.ui.calendarTest;

import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class MyWeekView extends Application {

    private final LocalTime firstSlotStart = LocalTime.of(0, 0);
    private final Duration slotLength = Duration.ofMinutes(15);
    private final LocalTime lastSlotStart = LocalTime.of(23, 59);

    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    @Override
    public void start(Stage primaryStage) {
        GridPane calendarView = new GridPane();

        LocalDate today = LocalDate.now();

        //LocalDate today = LocalDate.of(2017,12,30);

        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);


        for(LocalDate day = startOfWeek; !day.isAfter(endOfWeek); day = day.plusDays(1)){
            calendarView.add(new Label(day.getDayOfWeek().name() + " " + day.getDayOfMonth()), day.getDayOfWeek().getValue() - 1, 0);
        }

        ScrollPane scroller = new ScrollPane(calendarView);

        Scene scene = new Scene(scroller);
        scene.getStylesheets().add(getClass().getResource("calendar-view.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    class DayMonthSlot extends VBox {
        DayMonthSlot(String dayOfMonth){
            super();
            this.getChildren().add(new Label(dayOfMonth));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}