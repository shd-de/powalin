package de.shd.basis.kotlin.ui.appinstaller

import de.shd.basis.kotlin.ui.util.exception.SHDRuntimeException
import org.w3c.appmanifest.ACCEPTED
import org.w3c.appmanifest.AppBannerPromptOutcome
import org.w3c.appmanifest.BeforeInstallPromptEvent
import org.w3c.appmanifest.DISMISSED
import kotlin.browser.window

/**
 * Stellt einer PWA die Möglichkeit zur Verfügung, sich auf dem Homescreen zu "installieren".
 *
 * Eine PWA ist dann "installierbar", wenn bestimmte Kriterien erfüllt werden. Siehe
 * [https://developers.google.com/web/fundamentals/app-install-banners/]
 *
 * @author Marcel Ziganow (zim)
 */
object AppInstaller {

  private val onInstallableListeners = mutableListOf<() -> Unit>()
  private val onAcceptListeners = mutableListOf<() -> Unit>()
  private val onRejectListeners = mutableListOf<() -> Unit>()

  private var promptEvent: BeforeInstallPromptEvent? = null

  /**
   * Startet die Aufforderung des Browsers, die PWA zu installieren.
   */
  fun requestInstallation() {
    requestInstallation(promptEvent
        ?: throw SHDRuntimeException("RequestInstallation() kann nur aufgerufen werden, wenn die App installierbar ist. PrompEvent ist null!"))
  }

  /**
   * Fügt einen Listener hinzu, welcher ausgeführt wird, sobald die PWA "installierbar ist"
   */
  fun addInstallableListener(onInstall: () -> Unit): AppInstaller {
    onInstallableListeners.add(onInstall)

    if (promptEvent != null) {
      onInstallableListeners.remove(onInstall)
      onInstall.invoke()
    }

    return this
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

  internal fun checkIsAppInstallable() {
    window.addEventListener("beforeinstallprompt", { event ->
      event.preventDefault()  // Prevent Chrome 76 and later from showing the mini-infobar
      val installPromptEvent = event as BeforeInstallPromptEvent
      promptEvent = installPromptEvent
      onInstallableListeners.forEach { it.invoke() }
    })
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