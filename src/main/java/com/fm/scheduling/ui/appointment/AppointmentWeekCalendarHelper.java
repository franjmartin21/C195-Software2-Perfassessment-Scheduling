package com.fm.scheduling.ui.appointment;

import com.fm.scheduling.domain.Appointment;
import com.fm.scheduling.domain.Customer;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AppointmentWeekCalendarHelper extends AppointmentCalendarHelper{

    public AppointmentWeekCalendarHelper(){
        super();
    }

    public void nextPeriod(){
        dateTime = dateTime.plusWeeks(1);
    }

    public void previousPeriod(){
        dateTime = dateTime.minusWeeks(1);
    }

    public GridPane getCalendarPanel(){
        fillAppointmentList();
        GridPane calendarView = new GridPane();

        LocalDate startOfWeek = dateTime.minusDays(dateTime.getDayOfWeek().getValue() - 1) ;
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        for(LocalDate day = startOfWeek; !day.isAfter(endOfWeek); day = day.plusDays(1)){
            calendarView.add(new HeaderSlot(day.getDayOfWeek().name() + " " + day.getDayOfMonth()), day.getDayOfWeek().getValue() - 1, 0);
        }

        for(LocalDate day = startOfWeek; !day.isAfter(endOfWeek); day = day.plusDays(1)){
            calendarView.add(new DayWeekSlot(day,appointmentList),day.getDayOfWeek().getValue() - 1, 1);
        }

        calendarView.getStyleClass().add("monthCalendarGrid");
        calendarView.setGridLinesVisible(true);

        return calendarView;
    }

    class HeaderSlot extends Label{

        private final String CSS_CLASS = "weekHeadCell";

        HeaderSlot(String text){
            super(text);
            this.getStyleClass().add(CSS_CLASS);
        }
    }

    class DayWeekSlot extends VBox {
        private LocalDate localDate;
        private final String CSS_CLASS = "weekCell";

        DayWeekSlot(LocalDate localDate, List<Appointment> appointments) {
            super();
            this.localDate = localDate;
            this.getStyleClass().add(CSS_CLASS);
            setAppointmentsDateRange(appointments);
        }

        private void setAppointmentsDateRange(List<Appointment> appointments) {
            LocalDateTime localDateTime;
            if (appointments != null) {
                for (Appointment appointment : appointments) {
                    if (appointment.getStart().toLocalDate().equals(localDate))
                        this.getChildren().add(new AppointmentSlot(appointment));
                }
            }
        }
    }
    class AppointmentSlot extends VBox{

        private final String CSS_CLASS = "appointmentWeekSlot";

        private Appointment appointment;

        AppointmentSlot(Appointment appointment){
            this.appointment = appointment;
            this.getStyleClass().add(CSS_CLASS);
            Label label = new Label();
            label.setText(appointment.getTitle() + "\n" +
                    appointment.getStart().getHour() + ":" + appointment.getStart().getMinute() + " - " + appointment.getEnd().getHour() + ":" + appointment.getEnd().getMinute() + "\n" +
                    appointment.getCustomer().getCustomerName());
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
