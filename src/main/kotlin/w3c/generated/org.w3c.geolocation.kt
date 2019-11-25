/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

// NOTE: THIS FILE IS AUTO-GENERATED, DO NOT EDIT!
// See libraries/tools/idl2k for details
//
// TODO !!! In der Basis paketiert, bis die Standard-Library von Kotlin diese API standardmäßig zur Verfügung stellt. !!!

@file:Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")

package org.w3c.geolocation

/**
 * Exposes the JavaScript [Geolocation](https://developer.mozilla.org/en/docs/Web/API/Geolocation) to Kotlin
 */
public external abstract class Geolocation {
    fun getCurrentPosition(successCallback: (GeolocationPosition) -> Unit, errorCallback: (GeolocationPositionError) -> Unit = definedExternally, options: PositionOptions = definedExternally): Unit
    fun watchPosition(successCallback: (GeolocationPosition) -> Unit, errorCallback: (GeolocationPositionError) -> Unit = definedExternally, options: PositionOptions = definedExternally): Int
    fun clearWatch(watchId: Int): Unit
}

/**
 * Exposes the JavaScript [PositionOptions](https://developer.mozilla.org/en/docs/Web/API/PositionOptions) to Kotlin
 */
public external interface PositionOptions {
    var enableHighAccuracy: Boolean? /* = false */
        get() = definedExternally
        set(value) = definedExternally
    var timeout: Int? /* = definedExternally */
        get() = definedExternally
        set(value) = definedExternally
    var maximumAge: Int? /* = 0 */
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun PositionOptions(enableHighAccuracy: Boolean? = false, timeout: Int? = 0, maximumAge: Int? = 0): PositionOptions {
    val o = js("({})")

    o["enableHighAccuracy"] = enableHighAccuracy
    o["timeout"] = timeout
    o["maximumAge"] = maximumAge

    return o
}

public external abstract class GeolocationPosition {
    open val coords: GeolocationCoordinates
    open val timestamp: Number
}

public external abstract class GeolocationCoordinates {
    open val latitude: Double
    open val longitude: Double
    open val altitude: Double?
    open val accuracy: Double
    open val altitudeAccuracy: Double?
    open val heading: Double?
    open val speed: Double?
}

public external abstract class GeolocationPositionError {
    open val code: Short
    open val message: String

    companion object {
        val PERMISSION_DENIED: Short
        val POSITION_UNAVAILABLE: Short
        val TIMEOUT: Short
    }
}

