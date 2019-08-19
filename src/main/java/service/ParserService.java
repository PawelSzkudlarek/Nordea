package service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import enums.FileType;
import generator.CsvGenerator;
import generator.XmlGenerator;
import model.Sentence;
import parser.TextParser;

import java.io.*;
import java.util.logging.Logger;

public class ParserService {

    private static final String FILE_CREATED = "file created with success.";

    TextParser textParser;

    private static final Logger log = Logger.getLogger(ParserService.class.getName());

    public ParserService(TextParser textParser) {
        this.textParser = textParser;
    }

    /**
     * Parse input text file on objects and generate output file in choose type.
     *
     * @param input    file
     * @param output   file
     * @param FileType as enum
     **/

    public void generate(File input, File output, FileType fileType) {
        try (BufferedReader br = new BufferedReader(new FileReader(input));
             BufferedWriter bw = new BufferedWriter(new FileWriter(output))) {
            log.info("Start generate " + fileType.toString().toLowerCase() + " file.");
            if (fileType.equals(FileType.XML)) {
                XStream xstream = new XStream(new DomDriver());
                xstream.alias("sentence", Sentence.class);
                xstream.alias("word", String.class);
                xstream.addImplicitCollection(Sentence.class, "words");
                XmlGenerator xmlGenerator = new XmlGenerator(textParser, xstream);
                xmlGenerator.generateFile(br, bw);
                log.info(FILE_CREATED);
            } else {
                CsvGenerator csvGenerator = new CsvGenerator(textParser);
                csvGenerator.generateFile(br, bw, output.getPath());
                log.info(FILE_CREATED);
            }
        } catch (IOException e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
