<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="dbControl.ReportDAO" %>
<%@ page import = "dbControl.PostDAO" %>
<%
    request.setCharacterEncoding("UTF-8");
    int userId = Integer.parseInt(request.getParameter("user_id"));
    int postId = Integer.parseInt(request.getParameter("post_id"));

    ReportDAO reportDao= new ReportDAO();
    int check = reportDao.unreport(userId, postId);
    
    if (check == -1){
%>
<script>
    alert("오류가 발생하였습니다.");
    history.go(-1)
</script>
<%
}else{
	
	PostDAO postDao = new PostDAO();
	int cnt_report = postDao.getReportCount(postId);
	postDao.updateReport(cnt_report-1, postId);
%>
<script>
    location.href='${pageContext.request.contextPath}/index.jsp';
</script>
<%
    }
%>

