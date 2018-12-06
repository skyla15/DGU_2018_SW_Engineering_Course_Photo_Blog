<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dbControl.PostDAO" %>
<%@ page import="dbControl.PostDTO" %>
<%  //로그인 체크
    if(session.getAttribute("memId")==null){
%>
	<jsp:forward page="/login/loginPage.jsp" /> 
<%
    }
%>
<%
    request.setCharacterEncoding("UTF-8");

    int postId = Integer.parseInt(request.getParameter("postId"));
    
    PostDAO postDao = new PostDAO();
    
    PostDTO postInfo = postDao.getSinglePost(postId);
    
    String filepath;
    if(request.getParameter("filepath")!=null)
    	filepath = request.getParameter("filepath");
    else
    	filepath = postInfo.getImage();
    
    String hash = postInfo.getHashString();
%>
<html>
<head>
    <title>이미지 선택</title>
    <link href="/Sophist/assets/css/css_main.css" rel="stylesheet" type="text/css">
    <meta charset="utf-8">
</head>
<body>

<!--  상단 고정 메뉴 추가  -->
<!--  상단 고정 메뉴 추가  -->

<div class="content-area">
    <div class="upload-box">
        <p>
            포스팅 편집
        </p>
        <hr>
        <p>
            <button class="profile-button"
                                onclick="location.href='${pageContext.request.contextPath}/PostEdit/imageModifyPage.jsp?postId=<%=postId %>&hash=<%=hash%>'">이미지 편집</button>
            <img src="${pageContext.request.contextPath}/<%=filepath %>" class="uploading-image">
        </p>
        <form action="postingUpdatePro.jsp?postId=<%=postId %>" method="post">
            <p>
                <textarea placeholder="하고싶은 말을 입력해주세요" class="uploading-story" name="content"><%=hash %></textarea>
                <input type="hidden" value="<%= filepath%>" name="image">
                <input type="hidden" value="<%= session.getAttribute("memId")%>" name="userId">
            </p>
            <p>
                <input type="submit" value="수정" class="upload-button">
                <input type="button" value="취소" class="upload-button" onClick="history.back()">
            </p>
        </form>
    </div>
</div>
</body>
</html>