package io.github.shkschneider.awesome.core

data class AwesomeClock(
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val ticks: Int,
) {

    companion object {

        fun elapsed(ticks: Long): AwesomeClock {
            val d = (ticks / AwesomeTime.ticksPerInGameDay).toInt()
            val h = ((ticks - (d * AwesomeTime.ticksPerInGameDay)) / AwesomeTime.ticksPerInGameHour).toInt()
            val m = ((ticks - (h * AwesomeTime.ticksPerInGameHour)) / AwesomeTime.ticksPerInGameMinute).toInt()
            val t = (ticks - (h * AwesomeTime.ticksPerInGameHour) - (m * AwesomeTime.ticksPerInGameMinute)).toInt()
            return AwesomeClock(d, h, m, t)
        }

    }

    val isZenith: Boolean get() =
        hours == 12 && minutes == 0 && ticks == 0

    val isNadir: Boolean get() =
        hours == 0 && minutes == 0 && ticks == 0

    fun toTicks(): Long =
        (days * AwesomeTime.ticksPerInGameDay + hours * AwesomeTime.ticksPerInGameHour + minutes * AwesomeTime.ticksPerInGameMinute + ticks).toLong()

    override fun toString(): String =
        "${days}d${hours}h${minutes}.${ticks}"

}
