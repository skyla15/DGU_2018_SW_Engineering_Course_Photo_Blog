<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="dbControl.PostDAO" %>
<%@ page import="dbControl.PostDTO" %>
<%@ page import="dbControl.MemberDAO" %>
<%@ page import="dbControl.MemberDTO" %>
<%@ page import="dbControl.FollowDAO" %>
<%@ page import="java.util.List" %>
<%
    request.setCharacterEncoding("UTF-8");
    //로그인 체크
    if(session.getAttribute("memId")==null){
%>
	<jsp:forward page="/login/loginPage.jsp" /> 
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
        
        PostDAO postDao = new PostDAO();
        int postCount = postDao.getListCount(requestedUserId);
        
        //팔로우중인지 확인
        boolean isFollowing = followDao.isFollwing(memId, requestedUserId);
        
        int FollowerCount = memDao.getFollowerCount(requestedUserId);
        int FollowingCount = memDao.getFollowingCount(requestedUserId);
%>

<html>
<head>
    <title>Profile</title>
    <link href="/Sophist/assets/css/css_main.css" rel="stylesheet" type="text/css">
    <script src="/Sophist/assets/js/main_js.js" type=text/javascript></script>
    <script src="/Sophist/assets/js/jquery-3.1.1.js" type=text/javascript></script>
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
                                onclick="location.href='${pageContext.request.contextPath}/profile/profileEditPage.jsp?user_id=<%=memId %>'">프로필 편집</button>&nbsp;&nbsp;&nbsp;
                           
                   
                        <div class="dropdown">
                            <button onclick="dropDown()" class="dropbtn profile-button">&nabla;</button>
                            <div id="myDropdown" class="dropdown-content">
                                <a href="${pageContext.request.contextPath}/logout.jsp">로그아웃</a>
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
                
                    <span><strong>게시물 <%= postCount %>개</strong></span>
                    
                    <span><strong>팔로잉 <%= FollowingCount %>명</strong></span>
                    
                    <span><strong>팔로워 <%= FollowerCount %>명</strong></span>

                </div>
                <div class="profile-comment">
                    <%= memDao.getProfileComment(requestedUserId) %>
                </div>
            </div>
        </div>

<%
        List<PostDTO> postList = null;
        //PostDAO postDao = new PostDAO();
        postList = postDao.getList(requestedUserId);
        
        List<MemberDTO> followingList= null;
        List<MemberDTO> followerList= null;
        followingList = memDao.getFollowingList(requestedUserId);
        followerList = memDao.getFollowerList(requestedUserId);
        
%>

        <div class="profile-posts">
        	
        	<div class="tab">
				<button class = "tablinks active" onclick = "openTab(event, 'Posting')">Posting</button>
				<button class = "tablinks" onclick = "openTab(event, 'Following')">Following</button>
				<button class = "tablinks" onclick = "openTab(event, 'Follower')">Follower</button>
			</div>
			
			<div id="Posting" class="tabcontent" style="display:block">
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
			
			<div id="Following" class="tabcontent">
				<%
					for(int i=0; i<followingList.size(); i++){
				%>
				    <div class="profile-posts-row row">
				<%
						MemberDTO memberInfo = followingList.get(i);
						int followingId = memberInfo.getId();
						String followingUserNick = memberInfo.getNick();
						String followingUserEmail = memberInfo.getEmail();
						String followingUserProfileImg = memberInfo.getProfile_img();
						String followingUserProfileComment = memberInfo.getProfile_comment();
				%>
				
					    <div class="col-sm-4"> 
					    <div class="row">             	
							<a href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%=followingId %>#">
							<div class="col-sm-4" style="width:150px; margin:auto">
								<img class="profile-image" src="${pageContext.request.contextPath}<%=followingUserProfileImg%>" alt="Card image" style="width:100%">
							</div>
							<div class="col-sm-4">
									<h4 class="card-title"> <%= followingUserNick %></h4>
									<p class="card-text"><strong>Email : <%= followingUserEmail %></strong></p>
									<p class="card-text"><%= followingUserProfileComment %></p>
							</div>
							</a>
						</div>
						</div>
	  
		            </div>
				<%
				    }
				%>
			</div>
	
			<div id="Follower" class="tabcontent">
				<%
					for(int i=0; i<followerList.size(); i++){
				%>
				    <div class="profile-posts-row">
				<%
						MemberDTO memberInfo = followerList.get(i);
						int followerId = memberInfo.getId();
						String followerUserNick = memberInfo.getNick();
						String followerUserEmail = memberInfo.getEmail();
						String followerUserProfileImg = memberInfo.getProfile_img();
						String followerUserProfileComment = memberInfo.getProfile_comment();
				%>

		                
		                <div class="col-sm-4"> 
					    <div class="row">             	
							<a href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%=followerId %>#">
							<div class="col-sm-4" style="width:150px; margin:auto">
								<img class="profile-image" src="${pageContext.request.contextPath}<%=followerUserProfileImg%>" alt="Card image" style="width:100%">
							</div>
							<div class="col-sm-4">
									<h4 class="card-title"> <%= followerUserNick %></h4>
									<p class="card-text"><strong>Email : <%= followerUserEmail %></strong></p>
									<p class="card-text"><%= followerUserProfileComment %></p>
							</div>
							</a>
						</div>
						</div>

		            </div>
				<%
				    }
				%>
			</div>
        

        </div>
    </div>
    
	<script>
	function openTab(evt, TabName){
		var i, tabcontent, tablinks;
		
		tabcontent = document.getElementsByClassName("tabcontent");
		for(i=0; i<tabcontent.length; i++){
			tabcontent[i].style.display = "none";
		}
		
		tablinks = document.getElementsByClassName("tablinks");
		for(i=0; i<tablinks.length; i++){
			tablinks[i].className = tablinks[i].className.replace(" active", "");
		}
		
		document.getElementById(TabName).style.display = "block";
		evt.currentTarget.className += " active";
	}
	</script>
</div>
</body>
</html>
<%
    }
%>