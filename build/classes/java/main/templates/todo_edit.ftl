<html>
<head>
    <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
    <meta charset="utf-8">
    <!-- title 是浏览器显示的页面标题 -->
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