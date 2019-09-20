import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Output {
    Output() {}

    void write(String text, String outputName) throws IOException {
        File file = new File("output\\", outputName);
        BufferedWriter writer = Files.newBufferedWriter(file.toPath());
        writer.write(text);
        writer.close();
    }

    void write(List<String> text, String outputName) throws IOException {
        File file = new File("output\\", outputName);
        BufferedWriter writer = Files.newBufferedWriter(file.toPath());
        for (String line : text) {
            writer.write(line);
            if (text.indexOf(line) != (text.size() - 1)) {
                writer.newLine();
            }
        }
        writer.close();
    }

    void clear(String name) {
        File file = new File("\\output",name);
        file.delete();
    }
}
