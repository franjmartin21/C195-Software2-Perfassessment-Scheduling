package com.fm.scheduling.domain;

import java.time.LocalDateTime;

public class BaseRecord<E> {

    private LocalDateTime createDate;

    private String createdBy;

    private LocalDateTime lastUpdate;

    private String lastUpdateBy;

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public static <E> E createInstance(Class clazz) {
        E e = null;
        try {
            e = (E) clazz.newInstance();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }
}
