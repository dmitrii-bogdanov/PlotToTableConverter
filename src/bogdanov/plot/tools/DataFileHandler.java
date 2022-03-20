package bogdanov.plot.tools;

import bogdanov.plot.data.DataFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DataFileHandler {

    public void save(DataFile file) {}

    public DataFile load(File file) {
        try {
            if (ImageIO.read(file) == null) {

            } else {

            }
        } catch (IOException e) {

        }
        return new DataFile();
    }

}

