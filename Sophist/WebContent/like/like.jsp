<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "dbControl.LikeDAO" %>
<%@ page import = "dbControl.PostDAO" %>
<%

	request.setCharacterEncoding("UTF-8");
	int userId = Integer.parseInt(request.getParameter("user_id"));
	int postId = Integer.parseInt(request.getParameter("post_id"));
	
	LikeDAO likeDao = new LikeDAO();
	int check = likeDao.like(userId, postId);
	
	if (check == -1){
%>
<script>
    alert("오류가 발생하였습니다.");
    history.go(-1);
</script>
<%
    }else{
    	PostDAO postDao = new PostDAO();
    	int cnt_like = postDao.getLikeCount(postId);
    	postDao.updateLike(cnt_like+1, postId);
%>
<script>
    location.href='${pageContext.request.contextPath}/index.jsp';
</script>
<%
    }
%>