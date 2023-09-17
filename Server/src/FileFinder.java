import java.io.File;
import java.io.FilenameFilter;

public class FileFinder implements FilenameFilter {
    private final String init;
    private final String ext;
    public FileFinder(String init, String ext) {
        this.init = init;
        this.ext = ext;

    }

    @Override
    public boolean accept(File dir, String name) {
        return name.startsWith(init) && name.endsWith(ext);
    }
}
