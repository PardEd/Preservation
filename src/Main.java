import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static String sp = File.separator;

    public static void main(String[] args) {

        String path = "C:" + sp + "Games" + sp + "savegames";
        GameProgress save1 = new GameProgress(100, 100, 1, 2.0);
        GameProgress save2 = new GameProgress(200, 80, 5, 3.0);
        GameProgress save3 = new GameProgress(400, 150, 10, 5.0);

        saveGames("save1", path, save1);
        saveGames("save2", path, save2);
        saveGames("save3", path, save3);

        zipFiles(path);
    }

    public static void saveGames(String name, String path, GameProgress save) {
        File file = new File(path);
        if (file.isDirectory()) {
            try (FileOutputStream fos = new FileOutputStream(file + sp + name + ".dat");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(save);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void zipFiles(String path) {
        File file = new File(path);
        File[] listFile = file.listFiles();

        if (listFile != null) {
            if (file.isDirectory()) {
                try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path + sp + "save.zip"))) {
                    for (File value : listFile) {
                        FileInputStream fis = new FileInputStream(value);
                        ZipEntry entry = new ZipEntry(value.getName());
                        zout.putNextEntry(entry);
                        byte[] buffer = new byte[fis.available()];
                        fis.read(buffer);
                        zout.write(buffer);
                        zout.closeEntry();
                        fis.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Arrays.stream(listFile)
                    .filter(File::delete)
                    .map(f -> "Файл " + f.getName() + " удален")
                    .forEachOrdered(System.out::println);
        }

    }
}
