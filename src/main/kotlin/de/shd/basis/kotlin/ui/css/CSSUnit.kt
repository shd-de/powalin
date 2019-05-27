package de.shd.basis.kotlin.ui.css

/**
 * Enthält die wichtigsten Maßeinheiten, die zusammen mit einer Zahl als Wert von bestimmten Eigenschaften von HTML-Elementen gesetzt werden können.
 * Dazu gehört u.a. die Breite sowie die Höhe und der Innen- sowie der Außenabstand eines HTML-Elements.
 *
 * @author Florian Steitz (fst)
 */
enum class CSSUnit(internal val value: String) {

    /**
     * Repräsentiert die Maßeinheit "Pixel" (`px`).
     */
    PIXELS("px"),

    /**
     * Repräsentiert die Maßeinheit "Punkt" (`pt`).
     */
    POINTS("pt"),

    /**
     * Repräsentiert die Maßeinheit `EM`, die relativ zur Schriftgröße des Elements ist, desen Eigenschaft ein Wert mit dieser Einheit zugewiesen wird.
     * Es gilt zu beachten, dass Elemente die Schriftgröße des Elternelements erben.
     */
    EM("em"),

    /**
     * Repräsentiert die Maßeinheit `REM`, die relativ zur Standard-Schriftgröße des Dokuments ist. Sie ist **nicht** relativ zur Schriftgröße des
     * Elements, desen Eigenschaft ein Wert mit dieser Einheit zugewiesen wird.
     */
    REM("rem"),

    /**
     * Repräsentiert die Maßeinheit "Millimeter" (`mm`).
     */
    MM("mm"),

    /**
     * Repräsentiert die Maßeinheit "Centimeter" (`cm`).
     */
    CM("cm"),

    /**
     * Repräsentiert die Maßeinheit "Prozent" (`%`). Prozentwerte sind dabei immer relativ zum Elternelement.
     */
    PERCENTAGE("%");
}