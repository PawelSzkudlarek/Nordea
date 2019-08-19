package parser;

import model.Sentence;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextParser {

    /**
     * Parse line into Java objects.
     *
     * @param line from file as String
     * @return collection of Sentence object
     **/

    public List<Sentence> parseLine(String line) {
        List<String> sentences = parseLineIntoSentence(line);
        return sentences.stream().map(this::parseSentenceStringIntoObject).collect(Collectors.toList());
    }

    // Split line on sentences and put into list
    private List<String> parseLineIntoSentence(String line) {
        List<String> sentences = new ArrayList<>();
        BreakIterator bi = BreakIterator.getSentenceInstance();
        bi.setText(line);
        int index = 0;
        while (bi.next() != BreakIterator.DONE) {
            String sentence = line.substring(index, bi.current());
            sentences.add(sentence);
            index = bi.current();
        }
        return sentences;
    }

    // Split sentence on words and filter unwanted marks
    private Sentence parseSentenceStringIntoObject(String sentence) {
        Sentence output = new Sentence();
        BreakIterator bi = BreakIterator.getWordInstance();
        bi.setText(sentence);
        int index = 0;
        while (bi.next() != BreakIterator.DONE) {
            String word = sentence.substring(index, bi.current());
            output.getWords().add(word);
            index = bi.current();
        }
        output.setWords(output.getWords().stream()
                .filter(item -> !item.trim().isEmpty())
                .filter(x -> !x.contains(".") && !x.contains(",") && !x.contains(";"))
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList()));
        return output;
    }
}
