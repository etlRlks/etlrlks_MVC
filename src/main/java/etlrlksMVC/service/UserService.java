package etlrlksMVC.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import etlrlksMVC.Utility;
import etlrlksMVC.models.ModelFactory;
import etlrlksMVC.models.TodoModel;
import etlrlksMVC.models.User;
import etlrlksMVC.models.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserService {

    public static User addBySQL(String username, String password) {
        User m = new User();
        password = saltedPassword(password);
        m.setUsername(username);
        m.setPassword(saltedPassword(password));


        MysqlDataSource ds = Utility.getDataSource();
        String sql = "insert into `java`.`user` (username, password) values (?, ?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, password);

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

    public static ArrayList<User> load() {
        String className = User.class.getSimpleName();
        ArrayList<User> rs = ModelFactory.load(className, 4, modelData -> {
            Integer id = Integer.valueOf(modelData.get(0));
            String username = modelData.get(1);
            String password = modelData.get(2);
            UserRole role = UserRole.valueOf(modelData.get(3));

            User m = new User();
            m.id = id;
            m.username = username;
            m.password = password;
            m.role = role;

            return m;
        });
        return rs;
    }

    public static void save(ArrayList<User> list) {
        String className = User.class.getSimpleName();
        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            Utility.log("id: %s", model.id);
            lines.add(model.id.toString());
            lines.add(model.username);
            lines.add(model.password);
            lines.add(model.role.toString());
            return lines;
        });
    }

    public static boolean validLogin(String username, String password, User u) {
            if (u.username.equals(username) && u.password.equals(saltedPassword(password))) {
                return true;
            }
        return false;
    }


    public static User findByNameSQL(String username) {
        User m = new User();
        m.setUsername(username);
        MysqlDataSource ds = Utility.getDataSource();
        String sql = "select * from User where username = ?";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();
            rs.first();
            m.setId(rs.getInt("id"));
            m.setPassword(rs.getString("password"));


            connection.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return m;
    }



    public static User findByIdSQL(Integer id) {
        User m = new User();
        m.setId(id);

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "select * from User where id = ?";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            rs.first();
            m.setUsername(rs.getString("username"));


            connection.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return m;
    }


    public static User guest() {
        User g = new User();
        g.id = -1;
        g.username = "游客";
        g.password = "";
        g.role = UserRole.guest;
        return g;
    }

    public static void updatePassword(Integer userId, String pass) {
        ArrayList<User> all = load();

        for (int i = 0; i < all.size(); i++) {
            User e = all.get(i);
            if (e.id.equals(userId)) {
                e.password = saltedPassword(pass);
            }
        }

        save(all);
    }

    public static String saltedPassword(String password) {
        String text = "$#@fev#dsfes";
        String hex = Utility.md5(password);
        String salted = hex + text;
        return salted;
    }
}
