<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dbControl.CommentDAO" %>
<%@ page import="dbControl.FollowDAO"%>
<%@ page import="dbControl.HashDAO"%>
<%@ page import="dbControl.LikeDAO"%>
<%@ page import="dbControl.MemberDAO"%>
<%@ page import="dbControl.PostDAO"%>
<%@ page import="dbControl.PostDTO"%>
<%@ page import="dbControl.ReportDAO"%>
<%@ page import="java.util.List" %>

<%
    request.setCharacterEncoding("UTF-8");
    
	int userId = Integer.parseInt(request.getParameter("userId"));
    int check = 0;
	LikeDAO likeDao = new LikeDAO();
	ReportDAO reportDao = new ReportDAO();
	PostDAO postDao = new PostDAO();
   	HashDAO hashDao = new HashDAO();
    List<PostDTO> postList = null;
    
    postList = postDao.getList(userId);
    
    for(int i=0; i<postList.size(); i++)
    {
    	int postId=postList.get(i).getId();
    	check += hashDao.deleteHashRel(postId);
    	check += postDao.deletePost(postId);
    	check += likeDao.unlike(userId, postId);
    	check += reportDao.unreport(userId, postId);
    }
    
    CommentDAO commentDAO = new CommentDAO();

    check+=commentDAO.deleteCommentUsedUserId(userId);
    
    MemberDAO memberDao = new MemberDAO();
    check+=memberDao.deleteMember(userId);
    FollowDAO followDao = new FollowDAO();
    check+=followDao.deleteAllFollowingInfo(userId);
    
    if(check == 4*postList.size() + 3){
    	
    	if(session.getAttribute("AuthLevel").equals("manager")){
	%>
			<script>
			    alert("정상적으로 회원 삭제시켰습니다..");
			    location.href="${pageContext.request.contextPath}/managerPage.jsp";
			</script>
		<%}else{ %>
			<script>
			    alert("정상적으로 탈퇴되었습니다.");
			    location.href="${pageContext.request.contextPath}/logout.jsp";
			</script>
	<%
	   	 }
	}else if(check==0){
	
		if(session.getAttribute("AuthLevel").equals("manager")){
	%>
			<script>
			    alert("삭제 과정에서 모유가 발생했습니다.");
			    location.href="${pageContext.request.contextPath}/managerPage.jsp";
			</script>
	<%	}else{ %>
			<script>
			    alert("탈퇴 과정에서 오류가 발생했습니다.")
			    location.href="${pageContext.request.contextPath}/logout.jsp";
			</script>
	<%
		}
	}
	%>