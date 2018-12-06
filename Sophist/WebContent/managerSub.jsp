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
	String tab = request.getParameter("tab");
	String[] check = null;
	
	int i;
	
	
	
	if(tab.equals("0")){
		check = request.getParameterValues("check");
		PostDAO postDao = new PostDAO();
		for(i = 0;i<check.length;i++){
			postDao.deletePost(Integer.parseInt(check[i]));
		}
		%>
		<script>
			alert("글이 삭제되었습니다.");
			location.href = "managerPage.jsp?tab=0";
		</script>
		<%
	}
	else{
		check = request.getParameterValues("check_2");
		MemberDAO memDao = new MemberDAO();
		LikeDAO likeDao = new LikeDAO();
		ReportDAO reportDao = new ReportDAO();
		PostDAO postDao = new PostDAO();
	   	HashDAO hashDao = new HashDAO();
	   	CommentDAO commentDAO = new CommentDAO();
	    FollowDAO followDao = new FollowDAO();
	    
	    System.out.println(check.length);
	    
	    for(i = 0; i<check.length; i++){
			int userId = Integer.parseInt(check[i]);
		    System.out.println(userId);
			
		    List<PostDTO> postList = null;
		    
		    postList = postDao.getList(userId);
		    
		    for(int j=0; j<postList.size(); j++)
		    {
		    	int postId=postList.get(j).getId();
		    	hashDao.deleteHashRel(postId);
		    	postDao.deletePost(postId);
		    	likeDao.unlike(userId, postId);
		    	reportDao.unreport(userId, postId);
		    }
		    
		   	memDao.deleteMember(userId);
		    followDao.deleteAllFollowingInfo(userId);
		    commentDAO.deleteCommentUsedUserId(userId);
		}		
		%>
		<script>
			alert("회원이 삭제되었습니다.");
			location.href = "managerPage.jsp?tab=1";
		</script>
		<%
	}
	
%>


