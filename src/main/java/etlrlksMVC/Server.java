package etlrlksMVC;

import etlrlksMVC.Route.Route;
import etlrlksMVC.Route.RouteAjaxTodo;
import etlrlksMVC.Route.RouteTodo;
import etlrlksMVC.Route.RouteUser;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;


class EtlrlksServlet implements Runnable {
    Request request;
    Socket connection;


    private static byte[] responseForPath(Request request) {
        String path = request.path;
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.putAll(Route.routeMap());
        map.putAll(RouteUser.routeMap());
        map.putAll(RouteTodo.routeMap());
        map.putAll(RouteAjaxTodo.routeMap());
        Function<Request, byte[]> function = map.getOrDefault(path, Route::route404);
        byte[] response = function.apply(request);
        return response;
    }

    public EtlrlksServlet(Socket connection, Request request) {
        this.connection = connection;
        this.request = request;

    }

    @Override
    public void run() {
        byte[] response = responseForPath(this.request);
        try {
            SocketOperator.socketSendAll(this.connection, response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


public class Server {
    static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        run();
    }

    public static void run() {

        int port = 9000;
        Utility.log("服务器启动, 访问 http://localhost:%s", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Utility.log("\nclient 连接成功");
                    String request = SocketOperator.socketReadAll(socket);
                    byte[] response;

                    // 处理请求
                    if (request.length() > 0) {
                        Utility.log("请求:\n%s", request);
                        Request r = new Request(request);

                        EtlrlksServlet servlet = new EtlrlksServlet(socket, r);
                        Thread t = new Thread(servlet);
                        pool.execute(t);


                    } else {
                        response = new byte[1];
                        Utility.log("接受到了一个空请求");
                        SocketOperator.socketSendAll(socket, response);
                    }
                } finally {

                }
            }
        } catch (IOException ex) {
            System.out.println("exception: " + ex.getMessage());
        }
    }


}
