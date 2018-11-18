<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%
    // 가지고 있는 세션값을 없애서 로그인을 해제함
    session.invalidate();
%>
<jsp:forward page="/login/loginPage.jsp" />
