<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="dbControl.*" %>

<%
    request.setCharacterEncoding("UTF-8");
    String image = request.getParameter("image");
    String content = request.getParameter("content");
    int user_id = Integer.parseInt(request.getParameter("userId"));

    PostDAO postDao = new PostDAO();

    int checkNum = postDao.insertPost(image, content, user_id);

    if (checkNum==0) {
%>
<script>
    alert("포스팅 작성중 오류가 발생했습니다.");
    history.go(-1);
</script>
<%
    }else if(checkNum == 1){
%>
<script>
    alert("포스팅이 정상적으로 작성되었습니다.");
    location.href = "${pageContext.request.contextPath}/index.jsp";
</script>
<%
    }
%>
