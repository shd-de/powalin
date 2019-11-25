package de.shd.basis.kotlin.ui.time

private const val MILLI_SCALE = 1
private const val SECOND_SCALE = 1000 * MILLI_SCALE
private const val MINUTE_SCALE = 60 * SECOND_SCALE
private const val HOUR_SCALE = 60 * MINUTE_SCALE
private const val DAY_SCALE = 24 * HOUR_SCALE

/**
 * Eine [TimeUnit] repräsentiert eine Zeiteinheit, die zuverlässig und reproduzierbar (bspw. auf Millisekunden) umgerechnet werden kann, ohne
 * Lokalisierungen in Betracht zu ziehen. D.h. eine [TimeUnit] wird verwendet, um eine Datums- und Uhrzeit-unabhängige Zeitspanne anzugeben.
 *
 * @author Florian Steitz (fst)
 */
enum class TimeUnit(private val scale: Int) {

    /**
     * Repräsentiert ein tausendstel einer Sekunde.
     */
    MILLISECONDS(MILLI_SCALE),

    /**
     * Repräsentiert eine Sekunde.
     */
    SECONDS(SECOND_SCALE),

    /**
     * Repräsentiert 60 Sekunden.
     */
    MINUTES(MINUTE_SCALE),

    /**
     * Repräsentiert 60 Minuten.
     */
    HOURS(HOUR_SCALE),

    /**
     * Repräsentiert 24 Stunden.
     */
    DAYS(DAY_SCALE);

    /**
     * Konvertiert die spezifizierte Anzahl von dieser Zeiteinheit in Millisekunden.
     *
     * Diese Methode arbeitet dabei ausschließlich mit positiven Werten. Dementsprechend gibt diese Methode `0` zurück, wenn eine negative Zahl
     * übergeben wird.
     */
    fun toMillis(duration: Int): Int {
        if (duration < 0) {
            return 0
        }

        return duration * scale
    }
}