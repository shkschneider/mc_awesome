package io.github.shkschneider.awesome.custom

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class Event(val reason: String)
