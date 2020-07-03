package etlrlksMVC.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import etlrlksMVC.Utility;
import etlrlksMVC.models.Session;
import etlrlksMVC.models.ModelFactory;
import etlrlksMVC.models.User;

import java.sql.*;
import java.util.ArrayList;

public class SessionService {

    public static Session add(Integer userId, String sessionId) {
        Session session = new Session();
        session.setUserId(userId);
        session.setSessionId(sessionId);


        MysqlDataSource ds = Utility.getDataSource();
        String sql = "insert into `java`.`session` (sessionId, userId) values (?, ?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, sessionId);
            statement.setInt(2, userId);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.first();
            Integer id = rs.getInt("GENERATED_KEY");
            session.setId(id);

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return session;
    }


    public static ArrayList<Session> load() {
        String className = Session.class.getSimpleName();
        ArrayList<Session> rs = ModelFactory.load(className, 2, modelData -> {
            String sessionId = modelData.get(0);
            Integer userId = Integer.valueOf(modelData.get(1));

            Session m = new Session();
            m.sessionId = sessionId;
            m.userId = userId;

            return m;
        });
        return rs;
    }

    public static void save(ArrayList<Session> list) {
        String className = Session.class.getSimpleName();
        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.sessionId);
            lines.add(model.userId.toString());
            return lines;
        });
    }

    public static Session findBySessioId(String sessionId) {
        Session session = new Session();
        session.setSessionId(sessionId);

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "select * from Session where sessionId= ?";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, sessionId);

            ResultSet rs = statement.executeQuery();
            rs.first();
            session.setId(rs.getInt("id"));
            session.setUserId(rs.getInt("userId"));


            connection.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return session;
    }
}
