import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FilesOperator {
    private List<String> extensions = new ArrayList<>();
    private String destinationPath;

    public void walk(String sourcePath, String destPath) throws IOException {
        this.destinationPath = destPath;
        Files.walk(Paths.get(sourcePath))
                .filter(path -> path.toFile().isFile())
                .forEach(this::visit);
    }
     
    public void visit(Path path) {
        Path target;
        String filename = path.getFileName().toString();
        
        String extension = Optional.of(filename)
                                   .filter(f -> f.contains("."))
                                   .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                                   .orElse("");

        
        if(extension == "") {
            target = Paths.get(this.destinationPath + "/" + filename);
        }
        else {
            target = Paths.get(this.destinationPath + "/" + extension + "/" + filename);
        }

        if(extension != "" && !extensions.contains(extension)) {
            extensions.add(extension);

            try {
                Files.createDirectory(Paths.get(this.destinationPath + "/" + extension));
            } 
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        try {
            Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}