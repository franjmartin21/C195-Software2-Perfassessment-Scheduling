package com.fm.scheduling.ui.appointment;

import com.fm.scheduling.domain.Appointment;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class AppointmentMonthCalendarHelper extends AppointmentCalendarHelper{

    public AppointmentMonthCalendarHelper(){
        super();
    }

    public void nextPeriod(){
        dateTime = dateTime.plusMonths(1);
    }

    public void previousPeriod(){
        dateTime = dateTime.minusMonths(1);
    }

    public GridPane getCalendarPanel(){
        fillAppointmentList();
        GridPane calendarView = new GridPane();

        LocalDate startOfWeek = dateTime.minusDays(dateTime.getDayOfWeek().getValue() - 1) ;
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        LocalDate startOfMonth = dateTime.withDayOfMonth(1);

        for(DayOfWeek dayOfWeek: DayOfWeek.values()) {
            calendarView.add(new HeaderSlot(dayOfWeek.name()), dayOfWeek.getValue() -1, 0);
        }

        int row = 1;
        for(LocalDate day = startOfMonth; !day.isAfter(dateTime.withDayOfMonth(dateTime.lengthOfMonth())); day = day.plusDays(1)){
            if((day.getDayOfWeek().getValue() -1) % 7 == 0) row++;

            calendarView.add(new DayMonthSlot(day, appointmentList), day.getDayOfWeek().getValue() - 1, row);
        }
        calendarView.getStyleClass().add("monthCalendarGrid");
        calendarView.setGridLinesVisible(true);

        return calendarView;
    }

    class HeaderSlot extends Label{
        HeaderSlot(String text){
            super(text);
            this.getStyleClass().add("monthHeadCell");
        }
    }

    class DayMonthSlot extends VBox {
        private LocalDate localDate;
        private final String CSS_CLASS = "monthCell";
        DayMonthSlot(LocalDate localDate, List<Appointment> appointments) {
            super();
            this.localDate = localDate;
            this.getChildren().add(new Label(String.valueOf(localDate.getDayOfMonth())));
            this.getStyleClass().add(CSS_CLASS);
            setAppointmentsDateRange(appointments);
        }

        private void setAppointmentsDateRange(List<Appointment> appointments) {
            LocalDateTime localDateTime;
            if (appointments != null) {
                for (Appointment appointment : appointments) {
                    if (appointment.getStart().toLocalDate().equals(localDate)) {
                        this.getChildren().add(new AppointmentSlot(appointment));
                    }
                }
            }
        }

    }

    class AppointmentSlot extends VBox{

        private final String CSS_CLASS = "appointmentMonthSlot";

        private Appointment appointment;

        AppointmentSlot(Appointment appointment){
            this.appointment = appointment;
            this.getStyleClass().add(CSS_CLASS);
            Label label = new Label(appointment.getTitle());
            label.setTooltip(addToolTipText(appointment));
            this.getChildren().add(label);
            this.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isSecondaryButtonDown()) {
                        getContextMenu(appointment, label).show(AppointmentSlot.this, event.getScreenX(), event.getScreenY());
                    }
                }
            });
        }
        private Tooltip addToolTipText(Appointment appointment){
            final Tooltip tooltip = new Tooltip();
            tooltip.setText(appointment.getTitle() + "\n" +
                            appointment.getStart().getHour() + ":" + appointment.getStart().getMinute() + " - " + appointment.getEnd().getHour() + ":" + appointment.getEnd().getMinute() + "\n" +
                            appointment.getCustomer().getCustomerName()
            );
            return tooltip;
        }
    }

}
