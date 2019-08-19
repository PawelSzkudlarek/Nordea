package generator;

import com.thoughtworks.xstream.XStream;
import model.Sentence;
import parser.TextParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class XmlGenerator {

    private static final String MANIFEST_LINE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n";
    private static final String TEXT_MARK = "<text> \n";

    private TextParser textParser;

    public XmlGenerator(TextParser parser) {
        textParser = parser;
    }

    private static final Logger log = Logger.getLogger(XmlGenerator.class.getName());

    /**
     * Generate  XML type file.
     *
     * @param BufferedReader object
     * @param BufferedWriter object
     * @param Path           String
     **/

    public void generateFile(BufferedReader br, BufferedWriter bw) throws IOException {
        String line;
        bw.write(MANIFEST_LINE);
        bw.write(TEXT_MARK);

        while ((line = br.readLine()) != null) {
            for (Sentence sentence : textParser.parseLine(line)) {
                bw.write(prepareLineToWrite(sentence));
            }
        }
        bw.write(TEXT_MARK);
        bw.flush();
        log.info("file created with success");
    }

    private String prepareLineToWrite(Sentence sentence) {
        StringBuilder sb = new StringBuilder();
        XStream xstream = new XStream();
        xstream.alias("sentence", Sentence.class);
        xstream.alias("word", String.class);
        xstream.addImplicitCollection(Sentence.class, "words");

        String toXML = xstream.toXML(sentence);
        sb.append(toXML);
        sb.append("\n");
        return sb.toString();
    }
}
