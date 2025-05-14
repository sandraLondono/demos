package utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ZAPReportGenerator {
    private static final String REPORT_DIR = "target/zap-reports/";

    static {
        try {
            Files.createDirectories(Paths.get(REPORT_DIR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}