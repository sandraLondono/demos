package tasks.commons;

import ability.ManageTextFile;
import io.vavr.control.Try;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

public class FileTask {

  public static Performable readFile(String filePath) {
    return Task.where("{0} read the text file, process the content", actor -> {
          Try.run(() -> {
            String content = ManageTextFile.as(actor).readFile(filePath);
            actor.remember("content", content);
          }).getOrElseThrow(() -> new RuntimeException("File not found"));
        }
    );
  }

  public static Performable writeFile(String filePath, String content) {
    return Task.where("{0} write the text file", actor -> {
      Try.run(() -> ManageTextFile.as(actor).writeFile(filePath, content)
      ).getOrElseThrow(() -> new RuntimeException("Can't write file"));
    });
  }

  public static Performable createFile(String filePath) {
    return Task.where("{0} create the text file", actor -> {
      Try.run(() -> {
            Path projectRoot = Paths.get(".").toAbsolutePath().normalize();
            Path destinationPath = projectRoot.resolve(filePath);
            actor.remember("finalPath", destinationPath.toString());
            ManageTextFile.as(actor).createFile(destinationPath);
          }).onFailure(Throwable::printStackTrace)
          .getOrElseThrow(() -> new RuntimeException("Can't create the file"));
    });
  }

  public static Performable deleteFile(String filePath) {
    return Task.where("{0} delete the text file", actor -> {
      Try.run(() -> {
            String path = actor.recall("finalPath");
            ManageTextFile.as(actor).deleteFile(path);
          }).onFailure(Throwable::printStackTrace)
          .getOrElseThrow(() -> new RuntimeException("Can't delete the file"));
    });
  }

}
