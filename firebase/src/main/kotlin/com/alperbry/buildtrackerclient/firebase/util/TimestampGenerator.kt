package com.alperbry.buildtrackerclient.firebase.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

internal interface TimestampGenerator {

    fun now(): Instant
}

internal class TimestampGeneratorImpl : TimestampGenerator {

    override fun now(): Instant = Clock.System.now()
}
