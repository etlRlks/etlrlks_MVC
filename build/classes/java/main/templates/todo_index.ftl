<html>
<head>
    <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
    <meta charset="utf-8">
    <!-- title 是浏览器显示的页面标题 -->
    <title>Todo</title>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

</head>


<body>

<div class="panel panel-primary">
    <div class="panel-heading">
        TODO
    </div>
    <div class="panel-body">
        <form action="/todo/add" method="post">
            <div class="row">
                <div class="col-lg-6">
                    <div class="input-group">
                        <input type="text" name="content"  class="form-control" placeholder="Todo...">
                        <span class="input-group-btn">
        <button class="btn btn-default" type="submit">add</button>
      </span>
                    </div><!-- /input-group -->
                </div><!-- /.col-lg-6 -->
            </div>
            <#--    <input type="text" name="content" placeholder="请输入todo">-->
<#--            <button type="submit">添加Todo</button>-->

        </form>
        <div>
            <#list todoList as t>
                <#if t.completed>
                    <h4 style='text-decoration:line-through'>
                        <div>${t.id}: ${t.content} <a href="/todo/edit?id=${t.id}">编辑</a>
                            <a href="/todo/delete?id=${t.id}">删除</a>
                            <a href="/todo/complete?id=${t.id}">完成</a>
                        </div>
                    </h4>
                <#else>
                    <h4>
                        <div>${t.id}: ${t.content} <a href="/todo/edit?id=${t.id}">编辑</a>
                            <a href="/todo/delete?id=${t.id}">删除</a>
                            <a href="/todo/complete?id=${t.id}">完成</a>
                        </div>
                    </h4>
                </#if>
            </#list>
        </div>
    </div>
</div>



<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</body>


</html>