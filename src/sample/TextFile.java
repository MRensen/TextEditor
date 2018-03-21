package sample;

import java.nio.file.Path;
import java.util.List;

public class TextFile {

    private final List<String> content;

    private final Path path;


    public TextFile(Path path, List<String> content){
        this.content = content;
        this.path = path;
    }

    public List<String> getContent() {
        return content;
    }

    public Path getPath() {
        return path;
    }
}
