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
    fun `le chat return map that has key le with chat , 1 for value and key chat with empty value`() {
        val computeMarkovStat = "le chat".computeMarkovStat()
        assertThat(computeMarkovStat["le"]).containsExactly(WordStat("chat", 1))
        assertThat(computeMarkovStat["chat"]).isEmpty();
    }

    @Test
    fun `le chien return map that has key le with chien , 1 for value and key chien with empty value`() {
        val computeMarkovStat = "le chien".computeMarkovStat()
        assertThat(computeMarkovStat["le"]).containsExactly(WordStat("chien", 1))
        assertThat(computeMarkovStat["chien"]).isEmpty();
    }

    @Test
    fun `le chien est cool return expected stats `() {
        val computeMarkovStat = "le chien est cool".computeMarkovStat()
        assertThat(computeMarkovStat["le"]).containsExactly(WordStat("chien", 1))
        assertThat(computeMarkovStat["chien"]).containsExactly(WordStat("est", 1))
        assertThat(computeMarkovStat["est"]).containsExactly(WordStat("cool", 1))
        assertThat(computeMarkovStat["cool"]).isEmpty();
    }

    @Test
    fun `le chat est nul return expected stats `() {
        val computeMarkovStat = "le chat est nul".computeMarkovStat()
        assertThat(computeMarkovStat["le"]).containsExactly(WordStat("chat", 1))
        assertThat(computeMarkovStat["chat"]).containsExactly(WordStat("est", 1))
        assertThat(computeMarkovStat["est"]).containsExactly(WordStat("nul", 1))
        assertThat(computeMarkovStat["nul"]).isEmpty();
    }
}

private fun String.computeMarkovStat(): MutableMap<String, List<WordStat>> {
    val markovStats = mutableMapOf<String, List<WordStat>>()

    val words = this.split(" ")

    if (this.isEmpty()) {
        return markovStats
    }

    if(words.size == 1){
        markovStats[words[0]] = emptyList()
        return markovStats
    }

    markovStats[words[0]] = listOf(WordStat(words[1], 1))
    markovStats[words[1]] = emptyList()

    val newSentence = this.replace(words[0]+" ", "");
    markovStats.putAll(newSentence.computeMarkovStat());

    return markovStats;
}

data class WordStat(val word: String, val stat: Int)
