<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="dbControl.MemberDAO" %>
<%@ page import="dbControl.MemberDTO" %>
<%@ page import="java.util.List" %>

<%  //로그인 체크
    if(session.getAttribute("memId")==null){
%>
	<jsp:forward page="/login/loginPage.jsp" /> 
<%
}else{
    request.setCharacterEncoding("UTF-8");
    String searchUser = request.getParameter("searchUser");
    int memId = (Integer)session.getAttribute("memId");
%>
<html>
<head>
    <title><%=searchUser%>에 대한 유저 검색</title>
    <link href="assets/css/css_main.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <meta charset="utf-8">
</head>
<body>
<jsp:include page="/mainMenu.jsp" flush="false"></jsp:include>
<%
	MemberDAO memDao = new MemberDAO();
	List<MemberDTO> userList = memDao.getUserList(searchUser);
%>
<div class="content-area row">

<%
	System.out.println(userList.size());
	for(int i=0; i<userList.size(); i++){
		MemberDTO userInfo = userList.get(i);
		int userId = userInfo.getId();
		String userNick = userInfo.getNick();
		String userEmail = userInfo.getEmail();
		String userProfileImg = userInfo.getProfile_img();
		String userProfileComment = userInfo.getProfile_comment();
%>
       
            
    <div class="col-sm-4">              	
		<div class="card" style="width:200px; margin:auto">
			<img class="card-img-top" src="${pageContext.request.contextPath}<%=userProfileImg%>" alt="Card image" style="width:100%">
			<div class="card-body">
				<h4 class="card-title"><%= userNick %></h4>
				<p class="card-text"><strong>Email : <%= userEmail %></strong></p>
				<p class="card-text"><%= userProfileComment %></p>
				<a href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%=userId %>#">Go Profile</a>
			</div>
		</div>
	</div>
	<br>

        	

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