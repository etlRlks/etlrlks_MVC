package etlrlksMVC.Route;

import etlrlksMVC.RenderTemplate;
import etlrlksMVC.Request;
import etlrlksMVC.Utility;
import etlrlksMVC.models.TodoModel;
import etlrlksMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RouteTodo {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/todo", RouteTodo::index);
        map.put("/todo/add", RouteTodo::add);
        map.put("/todo/delete", RouteTodo::delete);
        map.put("/todo/edit", RouteTodo::edit);
        map.put("/todo/update", RouteTodo::update);
        map.put("/todo/complete", RouteTodo::complete);

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
        ArrayList<TodoModel> todos = TodoService.allBySQL();
        Utility.log("todos %s", todos);
        HashMap<String, Object> h = new HashMap<>();
        h.put("todoList", todos);
        String body = RenderTemplate.render(h, "todo_index.ftl");

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
        HashMap<String, String> form = request.form;
        TodoService.addBySQL(form.get("content"));
        return redirect("/todo");
    }

    public static byte[] delete(Request request) {
        HashMap<String, String> data = request.query;
        Integer todoId = Integer.valueOf(data.get("id"));
        TodoService.deleteByIdSQL(todoId);
        Utility.log("todo delete %s", data);
        return redirect("/todo");
    }

    public static byte[] edit(Request request) {
        HashMap<String, String> data = request.query;
        Integer todoId = Integer.valueOf(data.get("id"));
        TodoModel todo = TodoService.findByIdSQL(todoId);

        HashMap<String, Object> h = new HashMap<>();
        h.put("todo_id", todo.id);
        h.put("todo_content", todo.content);
        String body = RenderTemplate.render(h, "todo_edit.ftl");


        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        String response = responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] update(Request request) {
        HashMap<String, String> data = request.form;
        Integer todoId = Integer.valueOf(data.get("id"));
        String content = data.get("content");
        TodoService.updateBySQL(todoId,content);
        return redirect("/todo");
    }

    public static byte[] complete(Request request) {
        HashMap<String, String> data = request.query;
        Integer todoId = Integer.valueOf(data.get("id"));
        TodoService.complete(todoId);
        return redirect("/todo");
    }
}
