<html>
<head>
    <meta charset="utf-8">
    <title>Todo 编辑</title>
</head>
<body>
<h1>Todo 编辑 </h1>
<form action="/todo/update" method="post">
    <input type="text" name="id" value="${todo_id}" hidden>
    <input type="text" name="content" value="${todo_content}">
    <br>
    <button type="submit">提交修改</button>
</form>

</body>
</body>
</html>