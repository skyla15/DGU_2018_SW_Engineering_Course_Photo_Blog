

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="dbControl.PostDAO" %>
<%@ page import="dbControl.PostDTO" %>
<%@ page import="dbControl.MemberDAO" %>
<%@ page import="dbControl.CommentDAO" %>
<%@ page import="dbControl.CommentDTO" %>
<%@ page import="dbControl.LikeDAO" %>
<%@ page import="java.util.List" %>

<%  //세션여부 확인하여 로그인여부 체크
  if(session.getAttribute("memId") ==null){
%>
	<jsp:forward page="/login/loginPage.jsp" />
<%
  }
%>

<html>
<head>
    <title>Photoblog</title>
    <link href="assets/css/css_main.css" rel="stylesheet" type="text/css"/>
    <meta charset="utf-8">
</head>
<body>
<!-- 메인메뉴를 인클루드하여 표시 -->
<jsp:include page="/mainMenu.jsp" flush="false"></jsp:include>
<%
    //세션에 등록된 로그인 아이디를 가져옴
    int memId = (Integer)session.getAttribute("memId");

    // postDTO인스턴스들의 리스트를 받아올 postlist를 선언
    List<PostDTO> postList = null;

    // PostDAO객체를 postDao라는 이름으로생성
    PostDAO postDao = new PostDAO();

    // postDao에게 팔로워기준 포스팅들을 모두 불러와서 postList에 저장
    postList = postDao.getFollwersPost(memId);

    // 회원의 닉네임이나 프로필사진을 불러오기위해 MemberDao객체를 memDao라는 이름으로 생성해둠
    MemberDAO memDao = new MemberDAO();
%>
<div class="content-area">

<%
    // postList안에 값이 있는동안 반복
    for(int i=0; i<postList.size(); i++){
        //단일 PostDTO객체를 post라는 이름으로 불러
        PostDTO post = postList.get(i);

        //post와 연관되는 댓글들을 불러오기 위해 CommentDTO의 인스턴스들을 담아올 commentList를 선언
        List<CommentDTO> commentList = null;

        //댓글 테이블에 접근하여 값을 불러오는 CommentDAO를 commentDAO라는 이름을 가진 객체로 생성
        CommentDAO commentDAO = new CommentDAO();

        //CommentDAO를 이용해 포스팅에 달린 댓글들을 객체 리스트로 담아서 가져옴
        commentList = commentDAO.getList(post.getId());

        // 포스팅을 불러오는 데 필요한 정보들을 DTO객체인 post에서 꺼내옴
        int userId = post.getUser_id();
        String postImg = post.getImage();
        int cntLike = post.getCnt_like();
        String content = post.getContent();
        int postId = post.getId();
        
        LikeDAO likeDao = new LikeDAO();
        boolean islike = likeDao.isLike(memId, postId);
%>
    <div class="post-box">
        <p class="post-top">
            <!--memDao에서 글쓴이의 프로필 사진을 가져오도록 시킴, 유저의 id를 인자로 넘겨줘서 실행한다. -->
            <img src="${pageContext.request.contextPath}<%=memDao.getProfileImg(userId) %>" class="post-profile-img">
            <span class="post-top-name">
                  <!--유저의 id를 가지고 이름을 불러오고 하이퍼링크도 생성 -->
                  <a href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%= userId%>">
                      <%=memDao.getUsername(userId) %>
                  </a>
              </span>
        </p>
        <p>
            <!--앞서 가져온 정보로 포스팅에 삽입되어있던 이미지의 주소를 불러와 이미지를 띄워줌 -->
            <img src="${pageContext.request.contextPath}<%= postImg%>" class="post-img">
        </p>
        <div class="post-content">
            <p class="post-like">

			  <%if(islike == true){ %>
                <button class="unfollow-button" onclick="location.href='${pageContext.request.contextPath}/like/unlike.jsp?user_id=<%=memId%>&post_id=<%=postId%>'">
                	좋아요 
                </button>
              <%} else { %>  
                <button class="follow-button" onclick="location.href='${pageContext.request.contextPath}/like/like.jsp?user_id=<%=memId%>&post_id=<%=postId%>'">
                	좋아요 
                </button>
              <%} 		%>
              <%=cntLike %>개	           

            </p>
            <p class="post-story">
                <!--글쓴이의 이름을 불러와 하이퍼링크를 생성해주고, 글쓴이가 작성한 글을 불러온다. 줄바꿈 구현하기위해 replace함수를 사용 -->
                <b><a href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%= userId%>">
                    <%=memDao.getUsername(userId) %></a></b> <%=content.replace("\r\n","<br>") %>
                </a>
            </p>
            <p class="post-comment">
<%
    // 상단에서 불러온 댓글 리스트를 모두 순회하며 조회
    for(int j=0; j<commentList.size(); j++){
        // 개별 댓글 인스턴스를 comment라는 이름으로 불러옴
        CommentDTO comment = commentList.get(j);
%>
                <!--불러온 댓글 인스턴스로부터 정보를 얻어 하이퍼링크를 생성해주고 댓글내용을 불러와 표시해줌 -->
                <b><a href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%=comment.getUser_id()%>">
                    <%=memDao.getUsername(comment.getUser_id()) %></b></a> <%=comment.getContent() %>&nbsp;&nbsp;&nbsp;
<%
		if(memId == comment.getUser_id()){
%>
		<a href="${pageContext.request.contextPath}/commentDeletePro.jsp?comment_id=<%=comment.getId()%>">[삭제]</a>
		<br>
<%			
		}
    }
%>
            </p>
            <hr>
            <!--댓글 작성하는 공간 -->
            <div class="post-input">
                
                <img src="${pageContext.request.contextPath}/assets/icons/like_colored.png">
                <!--댓글을 입력하면 commentPro.jsp로 보내 댓글을 저장하도록 함 -->
                <form action="commentPro.jsp" method="post">
                    <input type="text" placeholder="댓글 달기..." class="post-input-comment-box comment-placeholder" name="content">
                    <!--DB에 저장할 때 필요한 값(포스트id와 유저id)를 히든 값으로 넘겨줌 -->
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
