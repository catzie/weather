package net.catzie.weather.utils

import com.google.common.truth.Truth.assertThat
import net.catzie.weather.Utils
import org.junit.Test

class UtilsRandomIdTest {

    @Test
    fun givenGetRandomIdFunc_WhenCalled_ThenReturnNumberBetween0to9999inclusive() {

        // Get random value from SUT
        val randomId = Utils.Randoms.getRandomId()

        // Assert
        assertThat(randomId in 0..9999).isTrue()
    }

    @Test
    fun givenGetRandomIdFunc_WhenCalledTwice_ThenReturnTwoDifferentValues() {

        // Get 2 random values to compare from SUT
        val randomId1 = Utils.Randoms.getRandomId()
        val randomId2 = Utils.Randoms.getRandomId()

        // Assert
        assertThat(randomId1).isNotEqualTo(randomId2)
    }
}