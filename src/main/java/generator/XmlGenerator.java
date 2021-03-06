package generator;

import com.thoughtworks.xstream.XStream;
import model.Sentence;
import parser.TextParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class XmlGenerator {

    private static final String MANIFEST_LINE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n";
    private static final String TEXT_MARK = "<text> \n";

    private TextParser textParser;
    private XStream xstream;

    public XmlGenerator(TextParser parser, XStream xstream) {
        this.textParser = parser;
        this.xstream = xstream;
    }

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
    }

    private String prepareLineToWrite(Sentence sentence) {
        StringBuilder sb = new StringBuilder();
        String toXML = xstream.toXML(sentence);
        sb.append(toXML);
        sb.append("\n");
        return sb.toString();
    }
}
