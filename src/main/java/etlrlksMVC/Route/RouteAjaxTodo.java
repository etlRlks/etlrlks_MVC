package etlrlksMVC.Route;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import etlrlksMVC.Request;
import etlrlksMVC.Utility;
import etlrlksMVC.models.TodoModel;
import etlrlksMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RouteAjaxTodo {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/ajax/todo", RouteAjaxTodo::index);
        map.put("/ajax/todo/add", RouteAjaxTodo::add);
        map.put("/ajax/todo/all", RouteAjaxTodo::all);


        return map;
    }
    public static String responseWithHeader(int code, HashMap<String, String> headerMap, String body) {
        String header = String.format("HTTP/1.1 %s\r\r", code);

        for (String key: headerMap.keySet()) {
            String value = headerMap.get(key);
            String item = String.format("%s: %s \r\n", key, value);
            header = header + item;
        }
        String response =  String.format("%s\r\n\r\n%s", header, body);
        return response;
    }

    public static byte[] index(Request request) {
        String body = Utility.html( "ajax_todo.ftl");

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

    public static byte[] add(Request request) {
        Utility.log("ajax todo add: %s", request.body);
        String jsonString = request.body;
        JSONObject jsonForm = JSON.parseObject(jsonString);
        String content = jsonForm.getString("content");
        Utility.log("content: %s", content);

        HashMap<String, String> form = new HashMap<>();
        form.put("content", content);
        TodoModel todo = TodoService.addBySQL(content);
        String todoString = JSON.toJSONString(todo);
        Utility.log("todoString: %s", todoString);


        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/json");
        String body = todoString;
        String response = responseWithHeader(200, header, body);
        return response.getBytes();
    }


    public static byte[] all(Request request) {
        ArrayList<TodoModel> todos = TodoService.allBySQL();
        String todoString = JSON.toJSONString(todos);

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/json");
        String body = todoString;
        String response = responseWithHeader(200, header, body);
        return response.getBytes();
    }
}
