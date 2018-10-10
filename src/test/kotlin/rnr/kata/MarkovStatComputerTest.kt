package rnr.kata

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MarkovStatComputerTest {

    @Test
    fun `empty sequence return empty map`() {
        assertThat("".computeMarkovStat()).isEmpty();
    }

    @Test
    fun `chat should return map with key chat but with empty value`() {
        assertThat("chat".computeMarkovStat()["chat"]).isEmpty();
    }

    @Test
    fun `chien should return map with key chien but with empty value`() {
        assertThat("chien".computeMarkovStat()["chien"]).isEmpty();
    }

    @Test
    fun `le chat return map  that has key le with chat , 1 for value and key chat with empty value`() {
        val computeMarkovStat = "le chat".computeMarkovStat()
        assertThat(computeMarkovStat["le"]).containsExactly(WordStat("chat", 1))
        assertThat(computeMarkovStat["chat"]).isEmpty();
    }

    @Test
    fun `le chien return map  that has key le with chien , 1 for value and key chien with empty value`() {
        val computeMarkovStat = "le chien".computeMarkovStat()
        assertThat(computeMarkovStat["le"]).containsExactly(WordStat("chien", 1))
        assertThat(computeMarkovStat["chien"]).isEmpty();
    }
}

private fun String.computeMarkovStat(): MutableMap<String, List<WordStat>> {
    val markovStats = mutableMapOf<String, List<WordStat>>()

    if (this.isEmpty()) {
        return markovStats
    }

    val words = this.split(" ")

    if(words.size == 1){
        markovStats[words[0]] = emptyList()
        return markovStats
    }

    markovStats[words[0]] = listOf(WordStat(words[1], 1))
    markovStats[words[1]] = emptyList()
    return markovStats
}

data class WordStat(val word: String, val stat: Int)
