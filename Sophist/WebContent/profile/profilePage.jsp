<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="dbControl.PostDAO" %>
<%@ page import="dbControl.PostDTO" %>
<%@ page import="dbControl.MemberDAO" %>
<%@ page import="dbControl.FollowDAO" %>
<%@ page import="java.util.List" %>
<%
    request.setCharacterEncoding("UTF-8");
    //로그인 체크
    if(session.getAttribute("memId")==null){
%>
<jsp:forward page="${pageContext.request.contextPath}/login/loginPage.jsp" />
<%
    }else if(request.getParameter("user_id")==null || request.getParameter("user_id")==""){
%>
<script>
    alert("잘못된 방식의 접근입니다.");
    history.go(-1);
</script>
<%
    }else{
        int memId = (Integer)session.getAttribute("memId");
        int requestedUserId = Integer.parseInt(request.getParameter("user_id"));
        boolean isMyself;

        //본인의 프로필인지 여부 확인

        if(memId == requestedUserId){
            isMyself = true;
        }else {
            isMyself = false;
        }

        MemberDAO memDao = new MemberDAO();
        FollowDAO followDao = new FollowDAO();

        //팔로우중인지 확인

        boolean isFollowing = followDao.isFollwing(memId, requestedUserId);
%>

<html>
<head>
    <title>Profile</title>
    <link href="/Sophist/assets/css/css_main.css" rel="stylesheet" type="text/css">
    <script src="/Sophist/assets/assets/js/main_js.js" type=text/javascript></script>
    <meta charset="utf-8">
</head>
<body>
<jsp:include page="/mainMenu.jsp" flush="false"></jsp:include>
<div class="content-area">


    <div class="profile-contents">
        <div class="profile-header">
            <div class="profile-header-image">
                <img class="profile-image" src="${pageContext.request.contextPath}<%=memDao.getProfileImg(requestedUserId) %>">
            </div>
            <div class="profile-header-contents">
                <div class="profile-header-nick">
                    <h1><%= memDao.getUsername(requestedUserId)%></h1>
                    <span class="follow-button-span">
<%
        if(isMyself == true){   //본인일 경우에만 해당 버튼이 표시됨
%>
                        <button class="profile-button"
                                onclick="location.href='${pageContext.request.contextPath}/profile/profileEditPage.jsp?user_id=<%=memId %>'">프로필 편집</button>&nbsp;
                        <div class="dropdown">
                            <button onclick="dropDown()" class="dropbtn profile-button">&nabla;</button>
                            <div id="myDropdown" class="dropdown-content">
                                <a href="/logout.jsp">로그아웃</a>
                            </div>
                        </div>
<%
        }else if (isFollowing == true){ //팔로우중인 사람일때 해당 버튼이 표시됨
%>
                        <button class="unfollow-button" onclick="location.href='${pageContext.request.contextPath}/follow/unfollow.jsp?user_id=<%=memId%>&following_id=<%=requestedUserId%>'">
                            팔로잉
                        </button>
<%
                        }else {         //자신의 계정도 아니고 팔로우중인 사람도 아닐 경우 표시됨
                        %>
                        <button class="follow-button" onclick="location.href='${pageContext.request.contextPath}/follow/follow.jsp?user_id=<%=memId%>&following_id=<%=requestedUserId%>'">
                            팔로우
                        </button>
<%
        }
%>
                    </span>
                </div>
                <div class="profile-stat">
                
                <!--  구현 필요  -->
                    <span>게시물 x개</span>
                    <span>팔로워 x명</span>
                    <span>팔로잉 x명</span>
                <!--  구현 필요  -->
                </div>
                <div class="profile-comment">
                    <%= memDao.getProfileComment(requestedUserId) %>
                </div>
            </div>
        </div>

<%
        List<PostDTO> postList = null;
        PostDAO postDao = new PostDAO();
        postList = postDao.getList(requestedUserId);

%>

        <div class="profile-posts">
<%
        for(int i=0; i<postList.size(); i+=3){
%>
            <div class="profile-posts-row">
<%
            for(int j = i; j < i+3; j++){
                if(j<postList.size()){
                    PostDTO post = postList.get(j);

                    int userId = post.getUser_id();
                    String postImg = post.getImage();
                    int postId = post.getId();
%>
                <div class="profile-post">
                    <a href="${pageContext.request.contextPath}/singlePostView.jsp?post_id=<%=postId %>">
                        <img src="${pageContext.request.contextPath}<%=postImg%>">
                    </a>
                </div>
<%
                }
            }
%>
            </div>
<%
        }
%>
        </div>
    </div>


</div>
</body>
</html>
<%
    }
%>
