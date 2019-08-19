package service;

import enums.FileType;
import generator.CsvGenerator;
import generator.XmlGenerator;
import parser.TextParser;

import java.io.*;
import java.util.logging.Logger;

public class ParserService {

    public ParserService(TextParser textParser) {
        this.textParser = textParser;
    }

    TextParser textParser;

    private static final Logger log = Logger.getLogger(ParserService.class.getName());

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
            log.info("Start generate " + fileType.toString().toLowerCase() + " file");
            if (fileType.equals(FileType.XML)) {
                XmlGenerator xmlGenerator = new XmlGenerator(textParser);
                xmlGenerator.generateFile(br, bw);
            } else {
                CsvGenerator csvGenerator = new CsvGenerator(textParser);
                csvGenerator.generateFile(br, bw, output.getPath());
            }
        } catch (IOException e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
