<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>默认导航栏</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">校园资讯</a>
        </div>        
        <div>
            <ul class="nav navbar-nav">
                <li ><a href="#">快讯</a></li>
                <li><a href="#">趣闻</a></li>

            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon-user"></span> 注册</a></li>
                <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> 登录</a></li>
            </ul>
        </div>
    </div>
</nav>
    <div class="list-group">
    <a href="#" class="list-group-item active">
        <h4 class="list-group-item-heading">
            近期热点
        </h4>
    </a>
    <a href="#" class="list-group-item">
        <h4 class="list-group-item-heading">
            热点1
        </h4>
        <p class="list-group-item-text">
            内容1
        </p>
    </a>
    <a href="#" class="list-group-item">
        <h4 class="list-group-item-heading">
            热点2
        </h4>
        <p class="list-group-item-text">
            内容2
        </p>
    </a>
</div>
<div  style="text-align: center;">
    <ul class="pagination">
    <li><a href="#">&laquo;</a></li>
    <li><a href="#">1</a></li>
    <li><a href="#">2</a></li>
    <li><a href="#">3</a></li>
    <li><a href="#">4</a></li>
    <li><a href="#">5</a></li>
    <li><a href="#">&raquo;</a></li>
</div>
</ul>
<div id="time" style="text-align: center;">
    <script type="text/javascript">
        setInterval(
                "$('#time').html(new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay()))",1000);
    </script>
</div>
</body>
</html>