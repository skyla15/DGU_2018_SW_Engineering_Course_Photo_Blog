<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  //로그인 체크
    if(session.getAttribute("memId")==null){
%>
<!-- <jsp:forward page="/login/loginPage.jsp" /> -->
<%
    }
%>
<html>
<head>
    <title>이미지 선택</title>
    <link href="/Sophist/assets/css/css_main.css" rel="stylesheet" type="text/css">
    <meta charset="utf-8">
</head>
<body>

<jsp:include page="/mainMenu.jsp" flush="false"></jsp:include>

<div class="content-area">
    <div class="upload-box">
        <p>
            업로드할 이미지를 선택해주세요
        </p>
        <hr>
        <form name="fileform" enctype="multipart/form-data" action="imgUpload.jsp" method="post">
            <p>
                <input type="file" value="이미지 선택" name="filename"><br>
            </p>
            <input type="submit" value="확인" class="image-select-button">
            <input type="button" value="뒤로가기" class="image-select-button">
        </form>
    </div>
</div>
</body>
</html>