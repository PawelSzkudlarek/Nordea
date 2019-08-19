package parser;

import model.Sentence;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TextParserTest {

    @Test
    void shouldParseLineCorrectly() {

        TextParser parser = new TextParser();
        Sentence pattern = new Sentence();

        pattern.setWords(Arrays.asList(new String[]{"aaa", "bbb", "ccc"}));
        String correctExample = ", ; ccc, bbb aaa. Third_word another_word";
        String incorrectExample = ", ; ccc, bbb aa. Third_word another_word";
        List<Sentence> correctResult = parser.parseLine(correctExample);
        List<Sentence> incorrectResult = parser.parseLine(incorrectExample);
        Sentence correctSentence = correctResult.get(0);
        Sentence incorrectSentence = incorrectResult.get(0);

        assertEquals(pattern, correctSentence);
        assertNotEquals(incorrectSentence, pattern);
    }
}