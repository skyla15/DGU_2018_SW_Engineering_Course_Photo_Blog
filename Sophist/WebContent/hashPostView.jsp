<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="dbControl.PostDAO" %>
<%@ page import="dbControl.PostDTO" %>
<%@ page import="dbControl.MemberDAO" %>
<%@ page import="dbControl.CommentDAO" %>
<%@ page import="dbControl.CommentDTO" %>
<%@ page import="java.util.List" %>

<%  //로그인 체크
    if(session.getAttribute("memId")==null){
%>
	<jsp:forward page="/login/loginPage.jsp" /> 
<%
}else{
    request.setCharacterEncoding("UTF-8");
    String hash = request.getParameter("hash");
    int memId = (Integer)session.getAttribute("memId");
%>
<html>
<head>
    <title><%=hash%>에 대한 포스팅</title>
    <link href="assets/css/css_main.css" rel="stylesheet" type="text/css">
    <meta charset="utf-8">
</head>
<body>
<jsp:include page="/mainMenu.jsp" flush="false"></jsp:include>
<%

    List<PostDTO> postList = null;
    PostDAO postDao = new PostDAO();
    postList = postDao.getHahtagPost(hash);

    MemberDAO memDao = new MemberDAO();
%>
<div class="content-area">

    <%
        for(int i=0; i<postList.size(); i++){
            PostDTO post = postList.get(i);

            List<CommentDTO> commentList = null;
            CommentDAO commentDAO = new CommentDAO();
            commentList = commentDAO.getList(post.getId());

            int userId = post.getUser_id();
            String postImg = post.getImage();
            int cntLike = post.getCnt_like();
            String content = post.getContent();
            int postId = post.getId();
    %>
    <div class="post-box">
        <p class="post-top">
            <img src="${pageContext.request.contextPath}<%=memDao.getProfileImg(userId) %>" class="post-profile-img">
            <span class="post-top-name">
                  <a href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%= userId%>">
                      <%=memDao.getUsername(userId) %>
                  </a>
              </span>
        </p>
        <p>
            <img src="${pageContext.request.contextPath}<%= postImg%>" class="post-img">
        </p>
        <div class="post-content">
            <p class="post-like">
<!-- 좋아요 버튼 추가, 이미지 및 로직 추가 -->
                <button>
                	<img>
                </button>
<!-- 좋아요 버튼 추가, 이미지 및 로직 추가 -->
                좋아요 <%=cntLike %>개
            </p>
            <p class="post-story">
                <b><a href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%= userId%>">
                    <%=memDao.getUsername(userId) %></a></b> <%=content.replace("\r\n","<br>") %>
                </a>
            </p>
            <p class="post-comment">
                <%
                    for(int j=0; j<commentList.size(); j++){
                        CommentDTO comment = commentList.get(j);
                %>
                <b><a href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%=comment.getUser_id()%>">
                    <%=memDao.getUsername(comment.getUser_id()) %></b> <%=comment.getContent() %>
                </a><br>
                <%
                    }
                %>
            </p>
            <hr>
            <div class="post-input">
                <img src="${pageContext.request.contextPath}/assets/icons/like_colored.png">
                <form action="commentPro.jsp" method="post">
                    <input type="text" placeholder="댓글 달기..." class="post-input-comment-box comment-placeholder" name="content">
                    <input type="hidden" name="post_id" value="<%=postId %>">
                    <input type="hidden" name="user_id" value="<%=session.getAttribute("memId")%>">
                </form>
            </div>
        </div>
    </div>
    <%
        }
    %>

</div>
</body>
</html>

</body>
</html>
<%
    }
%>