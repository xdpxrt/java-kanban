package project.manager.util;

import project.manager.HistoryManager;
import project.task.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class CSVTaskUtil {
    private final static String BACKUP_PATH = "src\\project\\resources\\";
    private final static String FILE_NAME = "ManagerBackup.csv";

    public static String getBackupPath() {
        return BACKUP_PATH;
    }

    public static String getFileName() {
        return FILE_NAME;
    }

    public static String historyTostring(HistoryManager manager) {
        StringBuilder history = new StringBuilder();
        if (!manager.getHistory().isEmpty()) {
            for (Task task : manager.getHistory()) {
                history.append(task.getId() + ",");
            }
            return history.delete(history.length() - 1, history.length()).toString();
        }
        return history.toString();
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> historyAsArray = new ArrayList<>();
        try {
            for (String field : value.split(",")) {
                historyAsArray.add((parseInt(field)));
            }
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }
        return historyAsArray;
    }

    public static List<String> taskFieldsFromFile(File file) {
        List<String> tasksFieldsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                tasksFieldsList.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasksFieldsList;
    }
}
