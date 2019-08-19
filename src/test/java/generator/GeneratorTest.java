package generator;

import enums.FileType;
import org.junit.jupiter.api.Test;
import parser.TextParser;
import service.ParserService;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class GeneratorTest {

    @Test
    public void shouldCreateXMLFileAndFullFill() {

        File largeInput = new File("src/test/java/files/input/large.in");
        File largeOutputXml = new File("src/test/java/files/output/largeOutput.xml");

        ParserService service = new ParserService(new TextParser());
        service.generate(largeInput, largeOutputXml, FileType.XML);

        assertTrue(largeOutputXml.exists());
        assertTrue(largeOutputXml.length() > 0);
    }

    @Test
    public void shouldCreateCSVFileAndFullFill() throws IOException {

        String correctOutput = "Sentence 1, adipiscing, amet, consectetur, dolor, elit, ipsum, Lorem, sit";

        File largeInput = new File("src/test/java/files/input/large.in");
        File largeOutputCsv = new File("src/test/java/files/output/largeOutput.csv");

        ParserService service = new ParserService(new TextParser());
        service.generate(largeInput, largeOutputCsv, FileType.CSV);

        assertTrue(largeOutputCsv.exists());
        assertTrue(largeOutputCsv.length() > 0);

        RandomAccessFile fileToChange = new RandomAccessFile(largeOutputCsv, "rw");
        fileToChange.seek(1);
        fileToChange.skipBytes(268); //skip whole first line by numbered bytes
        String secondLine = fileToChange.readLine();
        assertTrue(correctOutput.equals(secondLine));
    }

}
