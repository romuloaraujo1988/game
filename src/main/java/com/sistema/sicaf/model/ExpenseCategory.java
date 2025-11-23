package com.sistema.sicaf.model;

public enum ExpenseCategory {
    FEED("Alimentação/Ração"),
    MEDICINE("Medicamentos/Vacinas"),
    LABOR("Mão de Obra"),
    MAINTENANCE("Manutenção"),
    OTHER("Outros");

    private final String displayName;

    ExpenseCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
