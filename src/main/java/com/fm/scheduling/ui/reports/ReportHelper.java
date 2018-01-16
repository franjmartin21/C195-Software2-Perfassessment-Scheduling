package com.fm.scheduling.ui.reports;

import com.fm.scheduling.exception.SchedulingException;
import com.fm.scheduling.service.SchedulingService;
import javafx.scene.control.TableView;

import java.util.List;

public abstract class ReportHelper {

    protected SchedulingService schedulingService;

    public ReportHelper(){
        schedulingService = SchedulingService.getInstance();
    }

    public abstract List<String> getComboValues() throws SchedulingException;

    public abstract String getDefaultElement();

    public abstract TableView getReport(String filter) throws SchedulingException;
}
