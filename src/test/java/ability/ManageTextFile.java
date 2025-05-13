package ability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

public class ManageTextFile implements Ability {

  public static ManageTextFile as(Actor actor) {
    return actor.abilityTo(ManageTextFile.class);
  }

  public static ManageTextFile with() {
    return new ManageTextFile();
  }

  public String readFile(String filePath) throws IOException {
    Path path = Paths.get(filePath);
    return new String(Files.readAllBytes(path));
  }

  public void writeFile(String filePath, String content) throws IOException {
    Path path = Paths.get(filePath);
    Files.write(path, content.getBytes());
  }

  public void deleteFile(String filePath) throws IOException {
    Path path = Paths.get(filePath);
    Files.deleteIfExists(path);
  }

  public void createFile(Path destinationPath) throws IOException {
    Files.createFile(destinationPath);
  }

  public boolean fileExists(String filePath) {
    Path path = Paths.get(filePath);
    return Files.exists(path);
  }


}
