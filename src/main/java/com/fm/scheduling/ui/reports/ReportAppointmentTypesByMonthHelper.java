package com.fm.scheduling.ui.reports;

import com.fm.scheduling.exception.SchedulingException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportAppointmentTypesByMonthHelper extends ReportHelper {

    private static final DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MM-yyyy");

    private Map<String, LocalDate> stringLocalDateMap;

    @Override
    public List<String> getComboValues() {
        stringLocalDateMap = new HashMap<>();
        List<String> monthComboValues = new ArrayList<>();
        LocalDate nowLocalDate = LocalDate.now();
        LocalDate previousYearLocalDate = nowLocalDate.minusYears(1);
        LocalDate nextYearLocalDate = nowLocalDate.plusYears(1);
        for(LocalDate localDate = previousYearLocalDate; nextYearLocalDate.isAfter(localDate); localDate = localDate.plusMonths(1)){
            stringLocalDateMap.put(formatter.format(localDate), localDate);
            monthComboValues.add(formatter.format(localDate));
        }
        return monthComboValues;
    }

    @Override
    public String getDefaultElement(){
        LocalDate now = LocalDate.now();
        return formatter.format(now);
    }

    @Override
    public TableView getReport(String filter) throws SchedulingException {
        if(filter == null) return null;

        Map<String, String> mapAppointmentsByType = schedulingService.getAppointmentsGroupedByDescriptionByMonth(stringLocalDateMap.get(filter));
        TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Appointment Type");
        column1.setPrefWidth(500);
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String,String>,String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                // this callback returns property for just one cell, you can't use a loop here
                // for first column we use key
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });

        TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Number Appointments");
        column2.setPrefWidth(400);
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                // for second column we use value
                return new SimpleStringProperty(p.getValue().getValue());
            }
        });

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(mapAppointmentsByType.entrySet());
        final TableView<Map.Entry<String,String>> table = new TableView<>(items);

        table.getColumns().setAll(column1, column2);

        return table;
    }
}
