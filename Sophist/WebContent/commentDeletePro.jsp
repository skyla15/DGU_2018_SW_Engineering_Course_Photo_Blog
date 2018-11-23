<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dbControl.CommentDAO" %>

<%
    request.setCharacterEncoding("UTF-8");
    int commentId = Integer.parseInt(request.getParameter("comment_id"));
    
    CommentDAO commentDAO = new CommentDAO();

    int check = commentDAO.deleteComment(commentId);

    if(check==1){
%>

<script>
    alert("댓글이 정상적으로 삭제되었습니다.");
    location.href = document.referrer;
</script>
<%
    }else if(check==0){
%>
<script>
    alert("댓글이 삭제과정에서 오류가 발생했습니다.")
    location.href="${pageContext.request.contextPath}/index.jsp";
</script>
<%
    }
%>