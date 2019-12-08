package idwall.desafio.string;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by marcos.salomao.
 */
public class UnitTestResourceUtils {

    public static String readResource(String fileName) throws URISyntaxException, IOException {
        URL url = UnitTestResourceUtils.class.getResource("/" + fileName);
        Path path = Paths.get(url.toURI());
        return new String(Files.readAllBytes(path), "UTF-8");
    }

}
