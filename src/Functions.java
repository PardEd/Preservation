import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Functions {

    private static final String sp = File.separator;
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

    public static void openZip(String path) {
        File file = new File(path);
        File[] filePath = file.listFiles();
        assert filePath != null;
        for (File f : filePath) {
            if (f.isFile() && f.getName().endsWith(".zip")) {
                try (ZipInputStream zis = new ZipInputStream(new FileInputStream(f))) {
                    ZipEntry entry;
                    String name;
                    while ((entry = zis.getNextEntry()) != null) {
                        name = entry.getName();
                        FileOutputStream fos = new FileOutputStream(file + sp + name);
                        for (int i = zis.read(); i != -1; i = zis.read()) {
                            fos.write(i);
                        }
                        fos.flush();
                        zis.closeEntry();
                        fos.close();
                        System.out.println("File " + name + " successfully unpacked\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (f.delete()) {
                System.out.println("Файл: " + f.getName() + " удален");
            }
        }
    }

    public static void openProgress(String path) {
        File file = new File(path);
        File[] fileName = file.listFiles();
        for (int i = 0; i < Objects.requireNonNull(fileName).length; i++) {
            if (fileName[i].isFile() && fileName[i].getName().endsWith(".dat")) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName[i]))) {
                    GameProgress result = (GameProgress) ois.readObject();
                    System.out.println(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
