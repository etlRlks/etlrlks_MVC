<html>
<head>
    <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
    <meta charset="utf-8">
    <!-- title 是浏览器显示的页面标题 -->
    <title>login</title>
    <link rel="stylesheet" href="/static?file=index.css">
</head>
<body>
<#--<a href='/login'>LOGIN</a>-->
<#--<a href='/'>HOME</a>-->
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
<img src="/static?file=wulian.jpg">
<h1>登录</h1>
<h3>欢迎 ${reveal}</h3>
<form action="/login" method="post">
    <input type="text" name="username" placeholder="请输入用户名">
    <br>
    <input type="text" name="password" placeholder="请输入密码">
    <br>
    <button type="submit">登录</button>
</form>
<h3>${loginResult}</h3></body>
</body>
</html>