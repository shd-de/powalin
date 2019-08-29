/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

// NOTE: THIS FILE IS AUTO-GENERATED, DO NOT EDIT!
// See libraries/tools/idl2k for details
//
// TODO !!! In der Basis paketiert, bis die Standard-Library von Kotlin diese API standardmäßig zur Verfügung stellt. !!!

@file:Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")

package org.w3c.appmanifest

import org.w3c.dom.CLASSIC
import org.w3c.dom.WorkerType
import org.w3c.dom.events.Event
import kotlin.js.Promise

/**
 * Exposes the JavaScript [BeforeInstallPromptEvent](https://developer.mozilla.org/en/docs/Web/API/BeforeInstallPromptEvent) to Kotlin
 */
public external open class BeforeInstallPromptEvent : Event {
    fun prompt(): Promise<PromptResponseObject>
}

public external interface PromptResponseObject {
    var userChoice: AppBannerPromptOutcome?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun PromptResponseObject(userChoice: AppBannerPromptOutcome? = undefined): PromptResponseObject {
    val o = js("({})")

    o["userChoice"] = userChoice

    return o
}

public external interface WebAppManifest {
    var dir: TextDirectionType? /* = TextDirectionType.AUTO */
        get() = definedExternally
        set(value) = definedExternally
    var lang: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var short_name: String?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var icons: Array<ImageResource>?
        get() = definedExternally
        set(value) = definedExternally
    var screenshots: Array<ImageResource>?
        get() = definedExternally
        set(value) = definedExternally
    var categories: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var iarc_rating_id: String?
        get() = definedExternally
        set(value) = definedExternally
    var start_url: String?
        get() = definedExternally
        set(value) = definedExternally
    var display: DisplayModeType? /* = DisplayModeType.BROWSER */
        get() = definedExternally
        set(value) = definedExternally
    var orientation: dynamic
        get() = definedExternally
        set(value) = definedExternally
    var theme_color: String?
        get() = definedExternally
        set(value) = definedExternally
    var background_color: String?
        get() = definedExternally
        set(value) = definedExternally
    var scope: String?
        get() = definedExternally
        set(value) = definedExternally
    var serviceworker: ServiceWorkerRegistrationObject?
        get() = definedExternally
        set(value) = definedExternally
    var related_applications: Array<ExternalApplicationResource>?
        get() = definedExternally
        set(value) = definedExternally
    var prefer_related_applications: Boolean? /* = "false" */
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun WebAppManifest(dir: TextDirectionType? = TextDirectionType.AUTO, lang: String? = undefined, name: String? = undefined, short_name: String? = undefined, description: String? = undefined, icons: Array<ImageResource>? = undefined, screenshots: Array<ImageResource>? = undefined, categories: Array<String>? = undefined, iarc_rating_id: String? = undefined, start_url: String? = undefined, display: DisplayModeType? = DisplayModeType.BROWSER, orientation: dynamic = undefined, theme_color: String? = undefined, background_color: String? = undefined, scope: String? = undefined, serviceworker: ServiceWorkerRegistrationObject? = undefined, related_applications: Array<ExternalApplicationResource>? = undefined, prefer_related_applications: Boolean? = false): WebAppManifest {
    val o = js("({})")

    o["dir"] = dir
    o["lang"] = lang
    o["name"] = name
    o["short_name"] = short_name
    o["description"] = description
    o["icons"] = icons
    o["screenshots"] = screenshots
    o["categories"] = categories
    o["iarc_rating_id"] = iarc_rating_id
    o["start_url"] = start_url
    o["display"] = display
    o["orientation"] = orientation
    o["theme_color"] = theme_color
    o["background_color"] = background_color
    o["scope"] = scope
    o["serviceworker"] = serviceworker
    o["related_applications"] = related_applications
    o["prefer_related_applications"] = prefer_related_applications

    return o
}

public external interface ImageResource {
    var src: String?
        get() = definedExternally
        set(value) = definedExternally
    var sizes: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var purpose: String?
        get() = definedExternally
        set(value) = definedExternally
    var platform: String?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun ImageResource(src: String?, sizes: String? = undefined, type: String? = undefined, purpose: String? = undefined, platform: String? = undefined): ImageResource {
    val o = js("({})")

    o["src"] = src
    o["sizes"] = sizes
    o["type"] = type
    o["purpose"] = purpose
    o["platform"] = platform

    return o
}

public external interface ServiceWorkerRegistrationObject {
    var src: String?
        get() = definedExternally
        set(value) = definedExternally
    var scope: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: WorkerType? /* = WorkerType.CLASSIC */
        get() = definedExternally
        set(value) = definedExternally
    var update_via_cache: dynamic /* = "imports" */
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun ServiceWorkerRegistrationObject(src: String?, scope: String? = undefined, type: WorkerType? = WorkerType.CLASSIC, update_via_cache: dynamic = "imports"): ServiceWorkerRegistrationObject {
    val o = js("({})")

    o["src"] = src
    o["scope"] = scope
    o["type"] = type
    o["update_via_cache"] = update_via_cache

    return o
}

public external interface ExternalApplicationResource {
    var platform: String?
        get() = definedExternally
        set(value) = definedExternally
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var min_version: String?
        get() = definedExternally
        set(value) = definedExternally
    var fingerprints: Array<Fingerprint>?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun ExternalApplicationResource(platform: String?, url: String? = undefined, id: String? = undefined, min_version: String? = undefined, fingerprints: Array<Fingerprint>? = undefined): ExternalApplicationResource {
    val o = js("({})")

    o["platform"] = platform
    o["url"] = url
    o["id"] = id
    o["min_version"] = min_version
    o["fingerprints"] = fingerprints

    return o
}

public external interface Fingerprint {
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun Fingerprint(type: String? = undefined, value: String? = undefined): Fingerprint {
    val o = js("({})")

    o["type"] = type
    o["value"] = value

    return o
}

/* please, don't implement this interface! */
public external interface AppBannerPromptOutcome {
    companion object
}

public inline val AppBannerPromptOutcome.Companion.ACCEPTED: AppBannerPromptOutcome get() = "accepted".asDynamic().unsafeCast<AppBannerPromptOutcome>()
public inline val AppBannerPromptOutcome.Companion.DISMISSED: AppBannerPromptOutcome get() = "dismissed".asDynamic().unsafeCast<AppBannerPromptOutcome>()

/* please, don't implement this interface! */
public external interface TextDirectionType {
    companion object
}

public inline val TextDirectionType.Companion.LTR: TextDirectionType get() = "ltr".asDynamic().unsafeCast<TextDirectionType>()
public inline val TextDirectionType.Companion.RTL: TextDirectionType get() = "rtl".asDynamic().unsafeCast<TextDirectionType>()
public inline val TextDirectionType.Companion.AUTO: TextDirectionType get() = "auto".asDynamic().unsafeCast<TextDirectionType>()

/* please, don't implement this interface! */
public external interface DisplayModeType {
    companion object
}

public inline val DisplayModeType.Companion.FULLSCREEN: DisplayModeType get() = "fullscreen".asDynamic().unsafeCast<DisplayModeType>()
public inline val DisplayModeType.Companion.STANDALONE: DisplayModeType get() = "standalone".asDynamic().unsafeCast<DisplayModeType>()
public inline val DisplayModeType.Companion.MINIMAL_UI: DisplayModeType get() = "minimal-ui".asDynamic().unsafeCast<DisplayModeType>()
public inline val DisplayModeType.Companion.BROWSER: DisplayModeType get() = "browser".asDynamic().unsafeCast<DisplayModeType>()

