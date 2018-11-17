<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");
    //로그인 체크
    if(session.getAttribute("memId")==null){
%>
	<jsp:forward page="/login/loginPage.jsp" /> 
<%
    // 접근방식 체크 본인여부 확인
    }else if(request.getParameter("user_id")==null ||
            request.getParameter("user_id")=="" ||
            Integer.parseInt(request.getParameter("user_id")) != (Integer)session.getAttribute("memId")){
%>
<script>
    alert("잘못된 방식의 접근입니다.");
    history.go(-1);
</script>
<%
    }else{
        int userId = Integer.parseInt(request.getParameter("user_id"));
%>
<html>
<head>
    <title>프로필 이미지 변경</title>
    <link href="/Sophist/assets/css/css_main.css" rel="stylesheet" type="text/css">
    <meta charset="UTF-8">
</head>
<body>
<jsp:include page="/mainMenu.jsp" flush="false"></jsp:include>
<div class="content-area">
    <div class="upload-box">
        <p>
            원하는 프로필 이미지를 선택해주세요.
        </p>
        <p class="align-right-header-font">150px*150px에 최적화되어 있습니다.</p>
        <hr>
        <form name="fileform" enctype="multipart/form-data"
              action="${pageContext.request.contextPath}/profile/profileImageUploadPro.jsp?user_id=<%=userId%>"
              method="post">
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
<%
    }
%>