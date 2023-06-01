package net.catzie.weather.utils

import com.google.common.truth.Truth.assertThat
import net.catzie.weather.Utils
import org.junit.Test

class UtilsFormatTimeTest {

    @Test
    fun givenFormatTimeFunc_WhenGivenMsTime_ThenReturnValueInCorrectFormat() {

        val timeInMillis = 1621468800000 // May 20, 2021 12:00:00 AM GMT
        val timeInExpectedFormat = "8:00 AM"

        val result = Utils.Format.formatTime(timeInMillis)

        assertThat(result).isEqualTo(timeInExpectedFormat)
    }
}