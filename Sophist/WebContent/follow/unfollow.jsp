<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="dbControl.FollowDAO" %>
<%
    request.setCharacterEncoding("UTF-8");
    int userId = Integer.parseInt(request.getParameter("user_id"));
    int followingId = Integer.parseInt(request.getParameter("following_id"));

    FollowDAO followDao = new FollowDAO();
    int check = followDao.unfollow(userId, followingId);

    if (check == -1){
%>
<script>
    alert("오류가 발생하였습니다.");
    history.go(-1)
</script>
<%
}else{
%>
<script>
    location.href='${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%=followingId %>'
</script>
<%
    }
%>
%>
