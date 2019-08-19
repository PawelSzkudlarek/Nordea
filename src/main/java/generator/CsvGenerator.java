package generator;

import model.Sentence;
import parser.TextParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class CsvGenerator {

    private TextParser textParser;

    private static final String FIRST_LINE =
            "                                      " +
                    "                                      " +
                    "                                      " +
                    "                                      " +
                    "                                      " +
                    "                                      " +
                    "                                      " +
                    "  \n";

    private int wordCounter = 0;
    private int sentenceCounter = 1;
    private static final Logger log = Logger.getLogger(CsvGenerator.class.getName());

    public CsvGenerator(TextParser parser) {
        this.textParser = parser;
    }

    /**
     * Generate  CSV type file.
     *
     * @param BufferedReader object
     * @param BufferedWriter object
     * @param Path           String
     **/

    public void generateFile(BufferedReader br, BufferedWriter bw, String path) throws IOException {
        String line;
        // reserve space for overwrite
        bw.write(FIRST_LINE);
        while ((line = br.readLine()) != null) {
            for (Sentence sentence : textParser.parseLine(line)) {
                List<String> words = sentence.getWords();
                updateCounter(words);
                bw.write(prepareLineToWrite(words));
                sentenceCounter++;
            }
        }
        // overwrite first line with knowing number of words
        String header = buildHeader(wordCounter);
        overwriteHeader(path, header, 0);
        bw.flush();
        log.info("file created with success");
    }

    private String prepareLineToWrite(List<String> words) {
        List<String> linkedList = new LinkedList<>(words);
        linkedList.add(0, "Sentence " + sentenceCounter);
        linkedList.add("\n");

        int last = linkedList.size() - 1;
        String line = String.join("",
                String.join(", ", linkedList.subList(0, last)),
                linkedList.get(last));
        return line;
    }

    private void updateCounter(List<String> words) {
        int currentSize = words.size();
        if (currentSize > wordCounter) {
            wordCounter = currentSize;
        }
    }

    private String buildHeader(int wordCounter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordCounter + 1; i++) {
            sb.append(", Word " + i);
        }
        return sb.toString();
    }

    private void overwriteHeader(String filePath, String data, int position) {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            file.seek(position);
            file.write(data.getBytes());
        } catch (IOException e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
