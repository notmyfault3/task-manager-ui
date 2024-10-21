package com.example.task_manager_ui.enums;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    public String toDisplayName() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}