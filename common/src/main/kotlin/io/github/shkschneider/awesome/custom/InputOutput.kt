package io.github.shkschneider.awesome.custom

data class InputOutput(
    val inputs: Int = 0,
    val outputs: Int = 0,
) {

    val size: Int = inputs + outputs

    fun isInput(slot: Int): Boolean =
        slot in (0 until inputs)

    fun canInsert(slot: Int, face: Faces = Faces.Top): Boolean =
        face == Faces.Top && ((inputs == 0 && outputs > 0) || isInput(slot))

    fun isOutput(slot: Int): Boolean =
        slot in (inputs until size)

    fun canExtract(slot: Int, face: Faces = Faces.Bottom): Boolean =
        face == Faces.Bottom && ((outputs == 0 && inputs > 0) || isOutput(slot))

    override fun toString(): String =
        "I/O: $inputs+$outputs=$size"

}
