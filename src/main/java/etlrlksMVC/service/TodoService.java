package etlrlksMVC.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import etlrlksMVC.Utility;
import etlrlksMVC.models.ModelFactory;
import etlrlksMVC.models.TodoModel;

import java.sql.*;
import java.util.ArrayList;

public class TodoService {


    public static TodoModel addBySQL(String content) {
        TodoModel m = new TodoModel();
        m.setContent(content);
        m.setCompleted(false);

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "insert into `java`.`todo` (content, completed) values (?, ?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, content);
            statement.setBoolean(2, false);

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



    public static ArrayList<TodoModel> allBySQL() {
        ArrayList<TodoModel> ms = new ArrayList<>();

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "select * from todo";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                TodoModel m = new TodoModel();
                m.setId(rs.getInt("id"));
                m.setContent(rs.getString("content"));
                m.setCompleted(rs.getBoolean("completed"));
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




    public static void deleteByIdSQL(Integer id) {

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "delete  from `java`.`todo` where id = (?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();


            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public static TodoModel findByIdSQL(Integer id) {
        TodoModel m = new TodoModel();
        m.setId(id);

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "select * from Todo where id = ?";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            rs.first();
            m.setContent(rs.getString("content"));


            connection.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return m;
    }

    public static TodoModel updateBySQL(Integer id, String content) {
        TodoModel m = new TodoModel();
        m.setContent(content);
        m.setId(id);


        MysqlDataSource ds = Utility.getDataSource();
        String sql = "update  `java`.`todo` set content = (?) where id = (?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, content);
            statement.setInt(2, id);

            statement.executeUpdate();


            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return m;
    }

    public static void complete(Integer todoId) {

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "update  `java`.`todo` set completed = (?) where id = (?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setBoolean(1, true);
            statement.setInt(2, todoId);

            statement.executeUpdate();


            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
