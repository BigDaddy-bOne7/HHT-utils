<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <title>login</title>
    <link rel="stylesheet" href="css/baoguanxitong.css">
</head>
<body>
<div id="login-waiceng">
    <form id="loginForm" onsubmit="return false;" autocomplete="on" target="get">
        <div class="login-denglukuang">
            <p class="login-p"><span class="span">用户名</span><input type="text" class="shurukuang-w" required="required"
                                                                   name="userName" id="user-nane"></p>
            <p class="login-p"><span class="span ">密  码</span><input type="password" class="shurukuang-w"
                                                                     required="required" name="userPassword"
                                                                     id="password"></p>
            <p class="login-p denglu-but"><input type="submit" class="login-anniu-denglu" value="登 录"></p>
        </div>
    </form>


</div>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/pageLogin.js"></script>
</body>
</html>