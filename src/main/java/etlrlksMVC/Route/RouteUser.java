package etlrlksMVC.Route;

import etlrlksMVC.RenderTemplate;
import etlrlksMVC.Request;
import etlrlksMVC.Utility;
import etlrlksMVC.models.User;
import etlrlksMVC.models.UserRole;
import etlrlksMVC.service.SessionService;
import etlrlksMVC.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

public class RouteUser {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/login", RouteUser::login);    //把函数赋值给变量 ::
        map.put("/register", RouteUser::register);
        map.put("/profile", RouteUser::profile);
        map.put("/admin/users", RouteUser::showAllUser);
        map.put("/admin/user/update", RouteUser::updatePass);
        return map;
    }

    public static String responseWithHeader(int code, HashMap<String, String> headerMap, String body) {
        String header = String.format("HTTP/1.1 %s\r\r", code);

        for (String key : headerMap.keySet()) {
            String value = headerMap.get(key);
            String item = String.format("%s: %s \r\n", key, value);
            header = header + item;
        }
        String response = String.format("%s\r\n\r\n%s", header, body);
        return response;
    }

    public static byte[] login(Request request) {
        HashMap<String, String> header = new HashMap<>();

        HashMap<String, String> data = null;
        if (request.method.equals("POST")) {
            data = request.form;
        } else if (request.method.equals("GET")) {
            data = request.query;
        } else {
            String m = String.format("unknown method (%s)", request.method);
            throw new RuntimeException(m);
        }

        String loginResult = "";
        String reveal = "";
        if (data != null) {
            String username = data.get("username");
            String password = data.get("password");
            User user = UserService.findByNameSQL(username);
            if (UserService.validLogin(username, password, user)) {
                String sessionId = UUID.randomUUID().toString();
                header.put("Set-Cookie", String.format("session_id=%s", sessionId));
                SessionService.add(user.id, sessionId);
                reveal = user.username;
                loginResult = "登录成功";
            } else {
                loginResult = "登录失败";
                reveal = "";
            }
        }

        header.put("Content-Type", "text/html");

        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("loginResult", loginResult);
        hashmap.put("reveal", reveal);
        String body = RenderTemplate.render(hashmap, "login.ftl");

        String response = responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] register(Request request) {
        Utility.log("register Route");
        String registerResult = "";

        if (request.method.equals("POST")) {
            HashMap<String, String> form = request.form;
            Utility.log("register form: %s", form);
            UserService.addBySQL(form.get("username"), form.get("password"));
            registerResult = "注册成功";
        }

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("registerResult", registerResult);

        String body = RenderTemplate.render(hashmap, "register.ftl");
        String response = responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] profile(Request request) {
        User u = Route.currentUser(request);
        String id = "";
        String username = "";
        String role = "";
        if (u != null && u.id != -1) {
            id = String.valueOf(u.id);
            username = u.username;
            role = u.role.toString();
        } else {
            return redirect("/login");
        }
        String body = Utility.html("profile.html");
        body = body.replace("{id}", id);
        body = body.replace("{username}", username);
        body = body.replace("{role}", role);
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        String response = responseWithHeader(200, header, body);

        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] redirect(String url) {
        String header = "HTTP/1.1 302 move\r\n" +
                "Location: " + url + "\r\n" +
                "\r\n\r\n";
        return header.getBytes();
    }

    public static byte[] showAllUser(Request request) {
        User user = Route.currentUser(request);
        if (!user.role.equals(UserRole.admin)) {
            return redirect("/login");
        }

        ArrayList<User> all = UserService.load();
        StringBuilder sb = new StringBuilder();

        for (User u : all) {
            String s = String.format(
                    "<h3>\n" +
                            "id: %s, username: %s, password: %s\n" +
                            "</h3>",
                    u.id,
                    u.username,
                    u.password
            );
            sb.append(s);
        }
        String body = Utility.html("users.html");
        body = body.replace("{users}", sb);
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        String response = responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] updatePass(Request request) {
        HashMap<String, String> data = request.form;
        Integer userId = Integer.valueOf(data.get("id"));
        String password = data.get("password");
        UserService.updatePassword(userId, password);
        return redirect("/admin/users");
    }

}
