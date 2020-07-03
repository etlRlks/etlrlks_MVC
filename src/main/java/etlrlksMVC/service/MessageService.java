package etlrlksMVC.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import etlrlksMVC.Utility;
import etlrlksMVC.models.Message;
import etlrlksMVC.models.ModelFactory;
import etlrlksMVC.models.TodoModel;
import etlrlksMVC.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageService {

    public static Message add(String author, String message) {
        Message m = new Message();
        m.setAuthor(author);
        m.setMessage(message);


        MysqlDataSource ds = Utility.getDataSource();
        String sql = "insert into `java`.`message` (author, message) values (?, ?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, author);
            statement.setString(2, message);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.first();
            Integer id = rs.getInt("GENERATED_KEY");
            m.setId(id);

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return m;
    }

    public static Message checkWork(Message m) {
        ArrayList<String> hexieWord = new ArrayList<>();
        hexieWord.add("tmd");
        hexieWord.add("fuck");

        for (String word : hexieWord) {
            if (m.message.contains(word)) {
                m.message = m.message.replace(word, "*");
            }
        }

        return m;
    }

    public static ArrayList<Message> allBySQL()
    {
        ArrayList<Message> ms = new ArrayList<>();

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "select * from message";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Message m = new Message();
                m.setId(rs.getInt("id"));
                m.setAuthor(rs.getString("author"));
                m.setMessage(rs.getString("message"));
                ms.add(m);
            }

            connection.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ms;
    }

    public static ArrayList<Message> load() {
        String className = Message.class.getSimpleName();
        ArrayList<Message> rs = ModelFactory.load(className, 2, modelData -> {
            String author = modelData.get(0);
            String message = modelData.get(1);

            Message m = new Message();
            m.author = author;
            m.message = message;

            return m;
        });
        return rs;
    }

    public static void save(ArrayList<Message> list) {
        String className = Message.class.getSimpleName();
        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.author);
            lines.add(model.message);
            return lines;
        });
    }
}
