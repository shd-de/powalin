// noinspection JSUnusedGlobalSymbols
/**
 * Ein <a href="https://developer.mozilla.org/en-US/docs/Web/API/Service_Worker_API">ServiceWorker</a>, der von einer offlinefähigen Anwendung im
 * Webbrowser "installiert" werden kann (siehe Event "<code>install</code>"). Nachdem er aktiviert wurde (siehe Event "<code>activate</code>"), agiert
 * er als Interceptor von HTTP-Anfragen (siehe Event "<code>fetch</code>") und prüft bei jeder Anfrage, ob die zu ladende Ressource (bspw. ein Skript,
 * eine HTML-Datei, eine CSS-Datei oder ein Bild) bereits im sog. <a href="https://developer.mozilla.org/en-US/docs/Web/API/CacheStorage">CacheStorage</a>
 * vorhanden ist. Falls dem so ist, wird die zu ladende Datei aus dem CacheStorage ausgelesen, statt eine HTTP-Anfrage zu senden. D.h. dieser
 * ServiceWorker prüft hinterher nicht mehr, ob auf dem Server mittlerweile eine neuere Version der Datei liegt. Er greift ausschließlich auf den
 * CacheStorage zu, wenn er dort die passende Datei findet.
 *
 * Damit also ein Update einer offlinefähigen Anwendung ausgeliefert werden kann, muss dafür eine neue Version dieses ServiceWorkers installiert
 * werden. Daher ist es notwendig, dass eine Anwendung die "Version" der ServiceWorker-Instanz über den Konstruktor angibt. Denn sobald sich die
 * Version ändert, wird eine neue Version des ServiceWorkers installiert, die einen eigenen Cache aufbaut und am Ende die alte Version des
 * ServiceWorkers (inklusive Cache) verwirft.
 *
 * Um die Versionierung von Instanzen dieses ServiceWorkers zu vereinfachen, ist es empfohlen, eine eindeutige Version für eine Instanz bei jedem
 * Build der Anwendung zu generieren. Dadurch gäbe es einen separaten ServiceWorker pro Build. Und da einzelne Dateien generell nicht außerhalb eines
 * Releases oder eines Snapshot-Builds ausgeliefert bzw. aktualisiert werden sollen, würden so auch keine Inkompatibilitäten entstehen.
 *
 * Es gilt allerdings zu beachten, dass wenn eine neue ServiceWorker-Instanz ausgeliefert wird, sie nicht zwingend umgehend aktiviert wird. Denn falls
 * bereits eine ältere Instanz aktiv ist, wird sie erst durch eine neue Instanz ausgetauscht, sobald die ältere Instanz keine Skripte mehr
 * "kontrolliert". Sprich, es müssen alle bisher geöffneten Tabs geschlossen werden, in der die Anwendung läuft. Ein reines Neuladen der Anwendung im
 * gleichen Tab ist nicht ausreichend.
 *
 * Auch gilt zu beachten, dass der CacheStorage unabhängig vom Standard-Cache eines Webbrowsers ist, den Webbrowser selbstständig befüllen und
 * verwalten. D.h. Anwendungen müssen den CacheStorage explizit befüllen und leeren. Allerdings ist in diesem ServiceWorker eine Liste von Ressourcen
 * gepflegt, die er standardmäßig im Rahmen der Installation im CacheStorage hinterlegt. D.h. solange ein Anwendung keine zusätzlichen Ressourcen
 * importiert, muss sie auch keine zu cachenden Ressourcen angeben bzw. pflegen.
 *
 * Schließlich ist zu beachten, dass im CacheStorage enthaltene Einträge bzw. Dateien kein automatisches Ablaufdatum haben. D.h. solange eine
 * Anwendung den Cache nicht leert bzw. verwirft, bleibt der Cache erhalten. Allerdings nur solange, solange dem Webbrowser genügend Speicherplatz zur
 * Verfügung steht, um auch anderen Webseiten bzw. Webanwendungen Speicherplatz einzuräumen.
 *
 * @param cacheVersion {String} Die Version des zu registrierenden ServiceWorkers, die gleichzeitig auch die Version des internen Caches ist. Sie
 * sollte automatisch generiert werden (bspw. durch ein Build-Tool).
 * @param additionalFilesToCache {Array<String>} Weitere Dateien die gecached werden sollen und der Basis nicht bekannt sind. Diese Dateien können
 * projektspezifisch sein.
 * @constructor
 */
function SHDCacheWorker(cacheVersion, additionalFilesToCache)
{
   // Informationen des internen Caches.
   const CACHE_NAME = `app-cache-v${cacheVersion}`; // Fungiert als eindeutiger Key des internen Caches.
   const CURRENT_CACHES = [CACHE_NAME]; // Enthält derzeit zwar nur einen Cache, ermöglicht es aber, zukünftig bei Bedarf mehr Caches hinzuzufügen.

   // Der Name dieser ServiceWorker-Instanz, der nur für Log-Meldungen benötigt bzw. verwendet wird.
   const WORKER_NAME = `SHDCacheWorker(${CACHE_NAME})`;

   // Enthält relative Links auf Ressourcen, die standardmäßig in der Phase "install" geladen und im internen Cache hinterlegt werden sollen.
   const DEFAULT_FILES_TO_CACHE = [
      // HTML-Dateien.
      "/", // Zeigt zwar auf die index.html, auf diese Weise ist aber sichergestellt, dass beide Varianten im internen Cache verfügbar sind.
      "/index.html",

      // Skripte von Kotlin
      "/basis/js/lib/kotlin.js",
      "/basis/js/lib/kotlinx-html-js.js",
      "/basis/js/lib/kotlinx-serialization-kotlinx-serialization-runtime.js",

      // Skripte des Basis-Frameworks
      "/basis/js/shd-basis-cache-worker.js",
      "/basis/js/shd-basis-util.js",
      "/basis/js/shd-basis-ui.js",

      // Anwendungsspezifische Skripte
      "/shd-app.js",

      // Anwendungsspezifische Styles
      "/styles.css",

      // Anwendungsspezifische Icons
      "/favicon.ico",

     // Manifest
     "/manifest.json"
   ];

   // Enthält weitere, anwendungsspezifische relative Links auf Ressourcen, die standardmäßig in der Phase "install" geladen und im internen Cache hinterlegt werden sollen.
   const ADDITIONAL_FILED_TO_CAHCE = [].concat(additionalFilesToCache.filter(t => typeof t !== "undefined" && t !== null));

   /**
    * Lädt und cacht alle Ressourcen, die standardmäßig in der Phase "<code>install</code>" geladen und im internen Cache hinterlegt werden sollen.
    *
    * @returns {Promise<void>} Das von {@link Cache.addAll} zurückgegebene {@link Promise}.
    */
   const precacheDefaultFiles = () => caches.open(CACHE_NAME).then(cache => cache.addAll(DEFAULT_FILES_TO_CACHE.concat(ADDITIONAL_FILED_TO_CAHCE)));

   /**
    * Iteriert über alle Caches, die zu diesem Origin (Hostname und Port) gehören und verwirft jeden Cache, der nicht in {@link CURRENT_CACHES}
    * enthalten ist. Auf diese Weise werden u.a. alle alten bzw. veralteten Caches weggeräumt.
    *
    * Dieser Ansatz kann zu Problemen bzw. Konflikten führen, wenn mehrere Anwendungen den gleichen Origin verwenden. Da aber Anwendungen, die auf
    * diesem Framework basieren, generell als Microservices verteilt werden sollen, sollte dies für die meisten Anwendungen kein Problem darstellen.
    *
    * Diese Funktion darf niemals in der Phase "<code>install</code>" aufgerufen werden, weil sie sonst Caches wegräumen könnte, die noch von einer
    * älteren Version dieses ServiceWorkers verwendet werden. Erst in der Phase "<code>activate</code>" ist sichergestellt, dass es keine ältere
    * Version mehr gibt, da nur stets eine Version eines ServiceWorkers aktiv sein kann.
    *
    * @returns {Promise<void>} Das von {@link CacheStorage.keys} zurückgegebene {@link Promise}.
    */
   const discardOldCaches = () => {
      return caches.keys()
         .then(cacheNames => Promise.all(cacheNames.map(discardIfNecessary))
            .then(() => console.debug(`Caches wurden erfolgreich durch ${WORKER_NAME} bereinigt`))
         );
   };

   /**
    * Prüft, ob der spezifizierte Cache in {@link CURRENT_CACHES} enthalten ist. Falls nicht, dann wird er via {@link CacheStorage.delete} verworfen.
    *
    * @param cacheName {String} Der Name (bzw. Key) des zu prüfenden Caches.
    * @returns {Promise<boolean>} Das von {@link CacheStorage.delete} zurückgegebene {@link Promise} oder nichts.
    */
   const discardIfNecessary = cacheName => {
      if( CURRENT_CACHES.indexOf(cacheName) === -1 )
      {
         return caches.delete(cacheName);
      }
   };

   // noinspection JSUnusedGlobalSymbols
   /**
    * Registriert Listener für die Events "<code>install</code>", "<code>activate</code>" und "<code>fetch</code>" beim übergebenen {@link ServiceWorkerGlobalScope},
    * die sich um die Verwaltung des internen Caches kümmern. Falls dieser ServiceWorker konfiguriert werden soll bzw. muss, dann müssen diese
    * Konfigurationen durchgeführt werden, bevor diese Funktion aufgerufen wird. Sie darf auch nur ein einziges Mal aufgerufen werden.
    *
    * @param workerScope {ServiceWorkerGlobalScope } Der Scope des zu installierenden ServiceWorker-Skripts.
    */
   this.listeningTo = function (workerScope) {
      // Schritt 1:
      // Lädt und cacht alle Ressourcen im Rahmen der Installation, die standardmäßig geladen und im internen Cache hinterlegt werden sollen.
      workerScope.addEventListener("install", event => {
         console.info(`${WORKER_NAME} wird installiert...`);
         event.waitUntil(precacheDefaultFiles())
      });

      // Schritt 2:
      // Prüft im Rahmen der Aktivierung alle vorhandenen Caches und verwirft alle Caches, die zu älteren Versionen dieses ServiceWorkers gehören.
      workerScope.addEventListener("activate", event => {
         console.info(`${WORKER_NAME} wird aktiviert...`);
         event.waitUntil(discardOldCaches())
      });

      // Schritt 3:
      // Interceptet alle HTTP-Requests mit der HTTP-Methode "GET" und prüft, ob die zu ladende Ressource bereits im internen Cache vorhanden ist.
      // Falls ja, dann wird sie aus dem Cache ausgelesen und zurückgegeben. Andernfalls wird ein HTTP-Request gesendet.
      workerScope.addEventListener("fetch", event => {
         // Standardmäßig werden nur statische Ressourcen gecacht, die mit der HTTP-Methode "GET" vom Webbrowser geladen werden. Fachliche Dateien
         // bzw. Responses sollen standardmäßig nicht gecacht werden. Daher sind alle anderen HTTP-Methoden uninteressant.
         if( event.request.method === "GET" )
         {
            event.respondWith(caches.match(event.request).then(request => request || fetch(event.request)))
         }

         // Falls die obige Bedingung nicht zutrifft, wird einfach nichts getan. Andere ServiceWorker können anschließend eigene Prüfungen und
         // Aktionen durchführen.
      });
   }
}