<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <!-- title 是浏览器显示的页面标题 -->
    <title>我的主页</title>
    <link rel="stylesheet" href="/static?file=index.css">
</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">

            <a class="brand" href="/">
                <h5>欢迎 {username}</h5>

            </a>

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

<div id="main">
    <div id="sidebar">

        <div class="panel">

            <div class="header">
                <span class="col_fade">个人信息</span>
            </div>
            <div class="inner">
                <div class="user_card">
                    <div>
                        <a class="user_avatar" href="/static?file=wulian.jpg">
                            <img src="/static?file=wulian.jpg" title="{username}">
                        </a>
                        <span class="user_name"><a class="dark" href="/profile">{username}</a></span>

                        <div class="board clearfix">
                            <div class="floor">
                                <span class="big">积分: 0 </span>
                            </div>
                        </div>
                        <div class="space clearfix"></div>
                        <span class="signature">
        “

            这家伙很懒，什么个性签名都没有留下。

        ”
    </span>
                    </div>
                </div>

            </div>
        </div>

    </div>
</div>

</body>
</html>
