package com.sistema.sicaf.model;

public enum PaymentStatus {
    PENDING("Pendente"),
    PAID("Pago/Recebido");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
