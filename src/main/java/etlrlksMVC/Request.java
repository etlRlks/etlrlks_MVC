package etlrlksMVC;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Request {
    public String rawData;
    public String path;
    public String method;
    public String body;

    public HashMap<String, String> query;
    public HashMap<String, String> form;

    public HashMap<String, String> headers;
    public HashMap<String, String> cookies;


    private static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public Request(String rawRequest) {
        this.rawData = rawRequest;
        String[] parts = rawRequest.split("\r\n\r\n", 2);
        this.body = parts[1];

        String headers = parts[0];
        String[] lines = headers.split("\r\n");
        String requestLine = lines[0];
        String[] requestLineData = requestLine.split(" ");
        this.method = requestLineData[0];

        this.parsePath(requestLineData[1]);
        this.parseForm(this.body);

        this.addHeaders(headers);
        log("query: %s", this.query);

    }

    private void parsePath(String path) {
        Integer index = path.indexOf("?");

        if (index.equals(-1)) {
            this.path = path;
            this.query = null;
        } else {
            this.path = path.substring(0, index);
            String queryString = path.substring(index + 1);

            String[] args = queryString.split("&");


            this.query = new HashMap<>();
            for (String e:args) {
                String[] kv = e.split("=", 2);
                String k = kv[0];
                String v = kv[1];
                v = URLDecoder.decode(v, StandardCharsets.UTF_8);
                log("k: <%s>, v: <%s>", k, v);
                this.query.put(k, v);
            }
        }
    }


    private void parseForm(String body) {
        if (body.strip().length() > 0) {
            String[] args = body.split("&");
            this.form = new HashMap<>();
            for (String e:args) {
                String[] kv = e.split("=", 2);
                String k = kv[0];
                String v = kv[1];
                v = URLDecoder.decode(v, StandardCharsets.UTF_8);
                log("k: <%s>, v: <%s>", k, v);
                this.form.put(k, v);
            }
        } else {
            this.form = null;
        }
    }

    private void addHeaders(String headerString) {
        this.headers = new HashMap<>();

        String[] lines = headerString.split("\r\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] kv = line.split(":", 2);
            this.headers.put(kv[0].strip(), kv[1].strip());
        }

        if (headers.containsKey("Cookie")) {
            this.cookies = new HashMap<>();
            String cookies = this.headers.get("Cookie");
            String args[] = cookies.split(";");
            for (String kvString: args) {
                String[] kv = kvString.split("=", 2);
                if (kv.length >= 2) {
                    this.cookies.put(kv[0].strip(), kv[1].strip());
                } else {
                    this.cookies.put(kv[0].strip(), kv[0].strip());
                }
            }
        } else {
            this.cookies = new HashMap<>();
        }

    }
}