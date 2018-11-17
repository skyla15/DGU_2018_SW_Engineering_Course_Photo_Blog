<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%  //세션여부 확인하여 로그인여부 체크
  if(session.getAttribute("memId") ==null){
%>
      <jsp:forward page="/login/loginPage.jsp" />
<%
  }
%>
<html>
<head>
    <title>Photoblog</title>
    <link href="assets/css/css_main.css" rel="stylesheet" type="text/css"/>
    <meta charset="utf-8">
</head>
<body>
<!-- 메인메뉴를 인클루드하여 표시 -->
<jsp:include page="mainMenu.jsp"></jsp:include>

<body>
이것은 인덱스여.

</body>
</html>