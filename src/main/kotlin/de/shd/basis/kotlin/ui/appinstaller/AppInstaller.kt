package de.shd.basis.kotlin.ui.appinstaller

import de.shd.basis.kotlin.ui.appinstaller.AppInstaller.isInstallable
import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import de.shd.basis.kotlin.ui.util.promise.DefaultRepeatablePromise
import de.shd.basis.kotlin.ui.util.promise.ResolvablePromise
import org.w3c.appmanifest.ACCEPTED
import org.w3c.appmanifest.AppBannerPromptOutcome
import org.w3c.appmanifest.BeforeInstallPromptEvent
import org.w3c.appmanifest.DISMISSED
import kotlin.browser.window

/**
 * Stellt einer PWA die Möglichkeit zur Verfügung, sich auf dem Homescreen zu "installieren".
 * Hierbei wird durch das Resolve des zurückgegebenen Promise der Methode [isInstallable] darauf hingewiesen,
 * dass die PWA "installierbar" ist.
 * Diese Information kann dazu verwendet werden, ein Popup, Button, etc. einzublenden.
 *
 * Eine PWA ist dann "installierbar", wenn bestimmte Kriterien erfüllt werden. Siehe hierfür
 * [https://developers.google.com/web/fundamentals/app-install-banners/]
 *
 * @author Marcel Ziganow (zim)
 */
object AppInstaller {

  private val onAcceptListeners = mutableListOf<() -> Unit>()
  private val onRejectListeners = mutableListOf<() -> Unit>()
  private var promptEvent: BeforeInstallPromptEvent? = null

  /**
   * Gibt ein Promise zurück, welches das Event `beforeinstallprompt` zum `window` hinzufügt.
   * Sobald das Event vom Browser ausgelöst wird, wird die Methode [DefaultRepeatablePromise.then] ausgeführt.
   * In dieser kann der Anwender darauf hingewiesen werden, dass die PWA installiert werden kann.
   */
  fun isInstallable(): ResolvablePromise<Nothing?> {
    return DefaultRepeatablePromise { invokeThen, _ ->
      window.addEventListener("beforeinstallprompt", { event ->
        event.preventDefault()  //Prevent Chrome 76 and later from showing the mini-infobar
        val installPromptEvent = event as BeforeInstallPromptEvent
        promptEvent = installPromptEvent
        invokeThen(null)
      })
    }
  }

  /**
   * Startet die Aufforderung des Browsers, die PWA zu installieren.
   */
  fun requestInstallation() {
    requestInstallation(promptEvent ?: throw SHDRuntimeException("Keine"))
  }

  /**
   * Fügt einen Listener hinzu, welcher ausgeführt wird, sobald der Benutzer die Installation bestätigt.
   */
  fun addAcceptListener(onInstall: () -> Unit): AppInstaller {
    onAcceptListeners.add(onInstall)
    return this
  }

  /**
   * Fügt einen Listener hinzu, welcher ausgeführt wird, sobald der Benutzer die Installation ablehnt.
   */
  fun addRejectListener(onInstall: () -> Unit): AppInstaller {
    onRejectListeners.add(onInstall)
    return this
  }

  private fun requestInstallation(installEvent: BeforeInstallPromptEvent) {
    installEvent.prompt().then { choiceResult ->
      val userChoice = choiceResult.userChoice
      if (AppBannerPromptOutcome.ACCEPTED == userChoice) {
        onAcceptListeners.forEach { it.invoke() }
      } else if (AppBannerPromptOutcome.DISMISSED == userChoice) {
        onRejectListeners.forEach { it.invoke() }
      }
    }
  }
}