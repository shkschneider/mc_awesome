package io.github.shkschneider.awesome.custom

sealed class Permissions(val level: Int) {

    object Anyone : Permissions(0)
    object Moderator : Permissions(1)
    object GameMaster : Permissions(2)
    object Admin : Permissions(3)
    object Owner : Permissions(4)

}
