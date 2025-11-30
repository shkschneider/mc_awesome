package io.github.shkschneider.awesome.custom

sealed class Faces {

    object Top : Faces()
    object Bottom : Faces()
    object Front : Faces()
    object Back : Faces()

    data class Sides(val right: Boolean = true, val left: Boolean = true) : Faces() {

        override fun toString(): String =
            (this as Faces).toString() + if (right) "(right)" else "" + if (left) "(left)" else ""

    }

    override fun toString(): String =
        this::class.java.simpleName

}
