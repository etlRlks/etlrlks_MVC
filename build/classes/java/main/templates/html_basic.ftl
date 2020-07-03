<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>例子111</title>
    </head>
    <body>
        很         好普通版
        <h1>很好 h1 版</h1>
        <h2>很好 h2 版</h2>
        <h3>很好 h3 版</h3>
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
