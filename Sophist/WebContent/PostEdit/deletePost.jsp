<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="dbControl.PostDAO" %>

<%
    request.setCharacterEncoding("UTF-8");
    int postId = Integer.parseInt(request.getParameter("post_id"));

    PostDAO postDao = new PostDAO();

    int checkNum = postDao.deletePost(postId);

    if (checkNum == 0){
%>
    <script>
        alert("삭제과정에 오류가 발생하였습니다.");
        history.go(-1);
    </script>
<%
    }else if(checkNum == 1){
%>
    <script>
        alert("포스팅이 삭제되었습니다.");
        location.href="${pageContext.request.contextPath}/index.jsp"
    </script>
<%
    }

%>