<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="dbControl.PostDAO" %>
<%@ page import="dbControl.PostDTO" %>
<%@ page import="dbControl.MemberDAO" %>
<%@ page import="dbControl.CommentDAO" %>
<%@ page import="dbControl.CommentDTO" %>
<%@ page import="dbControl.LikeDAO" %>
<%@ page import="java.util.List" %>

<%  //로그인 체크
    if(session.getAttribute("memId")==null){
%>
	<jsp:forward page="/login/loginPage.jsp" /> 
<%
}else{
    request.setCharacterEncoding("UTF-8");
    int memId = (Integer)session.getAttribute("memId");
    String searchStr = request.getParameter("searchStr");
    
    if( searchStr.charAt(0) != '@'){
    	String hash = searchStr.substring(1);
%>
	<script>
	    location.href="${pageContext.request.contextPath}/hashPostView.jsp?hash=%23<%=hash%>";
	</script>
<%
    }else{
    	String searchUser = searchStr.substring(1);
%>
	<script>
	    location.href="${pageContext.request.contextPath}/userSearchView.jsp?searchUser=<%=searchUser%>";
	</script>
<%    	
    }
}
%>