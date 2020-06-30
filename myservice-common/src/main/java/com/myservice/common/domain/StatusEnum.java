package com.myservice.common.domain;

import lombok.Getter;

@Getter
public enum StatusEnum {

    INACTIVE("lbl_inactive"),
    ACTIVE("lbl_active");

    private String label;

    StatusEnum(String label) {
        this.label = label;
    }

    public static StatusEnum get(String filter) {
        if (ACTIVE.label.contains(filter)) {
            return ACTIVE;
        } else if (INACTIVE.label.contains(filter)) {
            return INACTIVE;
        }
        return null;
    }

    public static StatusEnum contains(String filter) {
        if (ACTIVE.label.toUpperCase().contains(filter.toUpperCase())) {
            return ACTIVE;
        } else if (INACTIVE.label.toUpperCase().contains(filter.toUpperCase())) {
            return INACTIVE;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getLabel();
    }
}
