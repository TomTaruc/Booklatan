/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Model;

/**
 *
 * @author Joseph Rey
 */
public enum FineStatus {
    ALL("All"), // for filtering
    PAID("Paid"),
    PENDING("Pending");

    private final String label;

    FineStatus(String label) {
        this.label = label;
    }

    public static FineStatus fromString(String value) {
        for (FineStatus type : FineStatus.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return label;
    }
}
