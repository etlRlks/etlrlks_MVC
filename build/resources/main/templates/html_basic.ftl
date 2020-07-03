<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>留言板</title>
    </head>
    <body>

        <form action="/message" method="get">
            <input name="author"/>
            <br/>
            <textarea name="message" rows="8" cols="40"></textarea>
            <br/>
            <button type="submit">GET 提交</button>
        </form>


        <form action="/message" method="post">
            <input name="author" />
            <!--<input name="name">-->
            <br>
            <textarea name="message" rows="8" cols="40"></textarea>
            <br>
            <button type="submit">POST 提交</button>
        </form>

     <#list messageList as m>
         <div>${m.author}: ${m.message}</div>
    </#list>
    </body>
</html>
