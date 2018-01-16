package com.fm.scheduling.ui.calendarTest;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MyMonthView extends Application {

    private final LocalTime firstSlotStart = LocalTime.of(0, 0);
    private final Duration slotLength = Duration.ofMinutes(15);
    private final LocalTime lastSlotStart = LocalTime.of(23, 59);

    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    @Override
    public void start(Stage primaryStage) {
        GridPane calendarView = new GridPane();

        //LocalDate today = LocalDate.now();

        LocalDate today = LocalDate.of(2017,12,30);

        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1) ;
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        LocalDate startOfMonth = today.withDayOfMonth(1);

        for(DayOfWeek dayOfWeek: DayOfWeek.values()) {
            calendarView.add(new Label(dayOfWeek.name()), dayOfWeek.getValue() -1, 0);
        }

        int row = 1;
        for(LocalDate day = startOfMonth; !day.isAfter(today.withDayOfMonth(today.lengthOfMonth())); day = day.plusDays(1)){
            if((day.getDayOfWeek().getValue() -1) % 7 == 0) row++;

            calendarView.add(new DayMonthSlot(String.valueOf(day.getDayOfMonth())), day.getDayOfWeek().getValue() - 1, row);
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