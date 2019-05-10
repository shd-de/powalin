// noinspection JSUnusedGlobalSymbols

/**
 * Erzeugt ein neues Objekt auf Basis der übergebenen Konstruktor-Funktion. Diese API ist nur dazu gedacht, von Kotlin-basierten Framework APIs aus
 * aufgerufen zu werden. Daher soll sie nicht direkt aus JavaScript heraus aufgerufen werden.
 * <br />
 * <br />
 * Eine eigene API für die dynamische Erzeugung von Objekten ist notwendig, da das JavaScript-Modul von Kotlin derzeit keine entsprechende
 * Reflection-API zur Verfügung stellt.
 *
 * @param constructorFn {Function} Eine Konstruktor-Funktion, auf deren Basis ein neues Objekt erzeugt werden soll.
 * @returns {Object} Das erzeugte Objekt.
 * @author Florian Steitz (fst)
 */
const createInstance = constructorFn => new constructorFn();