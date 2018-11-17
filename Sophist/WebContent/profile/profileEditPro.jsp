<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="dbControl.MemberDAO" %>

<%
    request.setCharacterEncoding("UTF-8");
    int memid = Integer.parseInt(request.getParameter("id"));
    String username = request.getParameter("nick");
    MemberDAO memDao = new MemberDAO();
%>

<jsp:useBean id="member" class="dbControl.MemberDTO">
    <jsp:setProperty name="member" property="*"/>
</jsp:useBean>
<%
    if(memDao.changeNickCheck(memid, username) == 1){
%>
<script>
    alert("<%=username %> 은/는 이미 사용중인 닉네임입니다.");
    history.go(-1);
</script>
<%
    }else if(memDao.changeNickCheck(memid, username) == 0){
%>
<%
        int check = memDao.updateProfile(member);
        if(check == -1){
%>
<script>
    alert("회원정보 수정중 오류가 발생했습니다.");
    history.go(-1);
</script>
<%
        }else{
%>
<script>
    location.href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%=memid%>"
</script>
<%
        }
    }
%>
