<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="stylesheet" href="/static?file=index.css">
</head>
<body>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">


            <ul class="nav pull-right">
                <li><a href="/">首页</a></li>


                <li><a href="/register">注册</a></li>
                <li><a href="/login">登录</a></li>


                <li><a href="/message">Message</a></li>
                <li><a href="/todo">TODO</a></li>

            </ul>


        </div>
    </div>
</div>

<h1>注册</h1>
<form action="/register" method="post">
    <input type="text" name="username" placeholder="请输入用户名">
    <br>
    <input type="text" name="password" placeholder="请输入密码">
    <br>
    <button type="submit">注册</button>
</form>
<h3>${registerResult}</h3></body>
</body>
</html>