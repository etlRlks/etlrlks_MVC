package etlrlksMVC;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Utility {

    public static MysqlDataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setServerName("127.0.0.1");
        dataSource.setDatabaseName("java");

        // 用来设置时区和数据库连接的编码
        try {
            dataSource.setCharacterEncoding("UTF-8");
            dataSource.setServerTimezone("UTC");

            Utility.log("url: %s", dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }


    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static InputStream fileStream(String path) throws FileNotFoundException {
        String resource = String.format("%s.class", Utility.class.getSimpleName());
        var res = Utility.class.getResource(resource);
        if (res != null && res.toString().startsWith("jar:")) {
            path = String.format("/%s", path);
            InputStream is = Utility.class.getResourceAsStream(path);
            if (is == null) {
                throw new FileNotFoundException(String.format("在 jar 里面找不到 %s", path));
            } else {
                return is;
            }
        } else {
            path = String.format("build/resources/main/%s", path);
            return new FileInputStream(path);
        }
    }


    public static void save(String path, String data) {
        try (FileOutputStream os = new FileOutputStream(path)) {
            os.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            String s = String.format(
                    "Save file <%s> error <%s>",
                    path,
                    e
            );
            throw new RuntimeException(s);
        }
    }

    public static String load(String path) {
        byte[] body = new byte[1];
        try (FileInputStream is = new FileInputStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            String s = String.format(
                    "Load file <%s> error <%s>",
                    path,
                    e
                    );
            throw new RuntimeException(s);
        }
        String r = new String(body, StandardCharsets.UTF_8);
        return r;
    }

    public static String html(String fileName) {
        String dir = "templates";
        String path = String.format("%s/%s", dir, fileName);
        byte[] body = new byte[1];
        try (InputStream is = Utility.fileStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String r = new String(body, StandardCharsets.UTF_8);
        return r;
    }

    public static String hexFromBytes(byte[] array) {
        String hex = new BigInteger(1, array).toString(16);
        int zeroLength = array.length * 2 - hex.length();
        for (int i = 0; i < zeroLength; i++) {
            hex = "0" + hex;
        }
        return hex;
    }

    public static String md5(String orgin) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("error: %s"), e);
        }
        md.update(orgin.getBytes());

        byte[] result = md.digest();
        String hex = hexFromBytes(result);

        return hex;
    }


    public static void main(String[] args) {
        String fileName = "1.txt";
        String data = load(fileName);
        log("<%s>",data);
    }
}
