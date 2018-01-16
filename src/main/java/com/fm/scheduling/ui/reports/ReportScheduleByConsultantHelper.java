package com.fm.scheduling.ui.reports;

import com.fm.scheduling.domain.Appointment;
import com.fm.scheduling.exception.SchedulingException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ReportScheduleByConsultantHelper extends ReportHelper{

    @Override
    public List<String> getComboValues() throws SchedulingException{
        schedulingService.getUserList();

        return schedulingService.getUserList().stream().map(e -> e.getUserName()).collect(Collectors.toList());
    }

    @Override
    public String getDefaultElement() {
        return null;
    }

    @Override
    public TableView getReport(String filter) throws SchedulingException {
        TableView<Appointment> table = new TableView();
        table.setEditable(false);

        TableColumn customerNameCol = new TableColumn("Customer Name");
        customerNameCol.setMinWidth(200);
        customerNameCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customerName"));

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setMinWidth(250);
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));

        TableColumn startCol = new TableColumn("Start date");
        startCol.setMinWidth(200);
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("start"));

        TableColumn endCol = new TableColumn("End date");
        endCol.setMinWidth(200);
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("end"));

        TableColumn locationCol = new TableColumn("Location");
        locationCol.setMinWidth(200);
        locationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        List<Appointment> appointmentList = schedulingService.getAppointmentByConsultant(filter);
        if(appointmentList != null){
            table.getItems().addAll(appointmentList);
        }
        table.getColumns().addAll(customerNameCol, titleCol, startCol, endCol, locationCol);
        return table;
    }
}
