import java.io.*;

public class Main {

    public static String sp = File.separator;

    public static void main(String[] args) {

        String path = "C:" + sp + "Games" + sp + "savegames";
        GameProgress save1 = new GameProgress(100, 100, 1, 2.0);
        GameProgress save2 = new GameProgress(200, 80, 5, 3.0);
        GameProgress save3 = new GameProgress(400, 150, 10, 5.0);

        Functions.saveGames("save1", path, save1);
        Functions.saveGames("save2", path, save2);
        Functions.saveGames("save3", path, save3);

        Functions.zipFiles(path);
        Functions.openZip(path);
        Functions.openProgress(path);
    }
}