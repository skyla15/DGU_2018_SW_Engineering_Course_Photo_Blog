<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="dbControl.PostDAO" %>
<%@ page import="dbControl.PostDTO" %>
<%@ page import="dbControl.MemberDAO" %>
<%@ page import="dbControl.MemberDTO" %>
<%@ page import="dbControl.ReportDAO" %>
<%@ page import= "java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<html lang="ko-KR">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">

    <!-- Meta -->
    <meta name="description" content="Premium Quality and Responsive UI for Dashboard.">
    <meta name="author" content="ThemePixels">


    <title>관리자 페이지</title>

    <link href="assets2/lib/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <link href="assets2/lib/Ionicons/css/ionicons.css" rel="stylesheet" type="text/css"/>
    <link href="assets2/lib/perfect-scrollbar/css/perfect-scrollbar.css" rel="stylesheet" type="text/css"/>
    <link href="assets2/lib/jquery-toggles/toggles-full.css" rel="stylesheet" type="text/css"/>
    <link href="assets2/lib/highlightjs/github.css" rel="stylesheet" type="text/css"/>
    <link href="assets2/lib/datatables/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
    <link href="assets2/lib/select2/css/select2.min.css" rel="stylesheet" type="text/css"/>

    <!--Amanda CSS -->
    <link href="assets/amanda.css" rel="stylesheet" type="text/css"/>
    
        <script>

     
      $(document).ready(function() {
         

         var url_string = window.location.href;
         var tab = url_string.split("=")[1];
         
         if (typeof tab == 'undefined')
            tab = 0;
         
            $("#checkall").click(function(){
                   //클릭되었으면
                   if($("#checkall").prop("checked")){
                       //input태그의 name이 chk인 태그들을 찾아서 checked옵션을 true로 정의
                       $("input[name=check]").prop("checked",true);
                       //클릭이 안되있으면
                   }else{
                       //input태그의 name이 chk인 태그들을 찾아서 checked옵션을 false로 정의
                       $("input[name=check]").prop("checked",false);
                   }
               })
          
          $("#checkall_2").click(function(){
              //클릭되었으면
              if($("#checkall_2").prop("checked")){
                  //input태그의 name이 chk인 태그들을 찾아서 checked옵션을 true로 정의
                  $("input[name=check_2]").prop("checked",true);
                  //클릭이 안되있으면
              }else{
                  //input태그의 name이 chk인 태그들을 찾아서 checked옵션을 false로 정의
                  $("input[name=check_2]").prop("checked",false);
              }
          })
                    
          
          $(".am-pagebody").hide(); //Hide all content
          $(".am-pagetitle").hide(); //Hide all content
          $(".am-pagebody:eq("+tab+")").show(); //Show first tab content
          $(".am-pagetitle:eq("+tab+")").show(); //Show first tab content

          //On Click Event
          $("nav-link with-sub li").click(function() {

              $("ul.tabs li").removeClass("active"); //Remove any "active" class
              $(this).addClass("active"); //Add "active" class to selected tab
              $(".am-pagebody").hide(); //Hide all tab content
              //$(".am-pagetitle").hide(); //Hide all content

              //var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
           $(".am-pagetitle:eq("+tab+")").show(); //Show first tab content
              $(".am-pagebody:eq("+tab+")").fadeIn(); //Fade in the active ID content
              return false;
          });

      });
    </script>
  </head>

  <body>
  
  <%
  
   String where = request.getParameter("where");
   String where_2 = request.getParameter("where_2");
   String is = request.getParameter("is");
   

    List<PostDTO> postList = new ArrayList<PostDTO>();
    List<MemberDTO> memList = null;
    List<MemberDTO> memSearchList = null;
    
    PostDAO postDao = new PostDAO();
    MemberDAO memDao = new MemberDAO();
    ReportDAO reportDao = new ReportDAO();
    
    
    if(where == null && where_2 == null){
       postList = postDao.getTotalList();
       memList = memDao.getAllMember();
    }
    else if(where == null){
       postList = postDao.getTotalList();
       memList = memDao.getList(where_2, is);
    }
    else if(where_2 == null){

       if(where.equals("content")){
          postList = postDao.getList(where, is);
          memList = memDao.getAllMember();
       }else{
           //MemberDTO memDto = new MemberDTO();
           memSearchList = memDao.getList(where, is);
           //System.out.println(memSearchList.size());
           
           for(int i = 0; i<memSearchList.size(); i++)
           {
        	   if(i == 0)
        	   		postList = postDao.getList(memSearchList.get(i).getId());
        	   else
        	   		postList.addAll(postDao.getList(memSearchList.get(i).getId()));
        	   
           }
           //postList = postDao.getList(memList.getId());
           memList = memDao.getAllMember();
       }
    }
    
   int i;

%>

   
    <div class="am-header">
      <span>
              <a href="${pageContext.request.contextPath}/index.jsp">
                  Sophist
              </a>
      </span>
    </div>

    <div class="am-sideleft">      
      <div class="tab-content">
        <div id="mainMenu" class="tab-pane active">
          <ul class="nav am-sideleft-menu">           
            <li class="nav-item">
              <a href="?tab=0" class="nav-link with-sub">
                <i class="icon ion-ios-filing-outline"></i>
                <!--포스트 관리 -->
                <span>Post Management</span>
              </a>
              <a href="?tab=1" class="nav-link with-sub">
                <i class="icon ion-ios-filing-outline"></i>
                <!--회원관리 -->
                <span>User Management</span>
              </a>
  
            </li><!-- nav-item -->
            <li class="nav-item">             
          </ul>
        </div><!-- #emailMenu --> 
      </div><!-- tab-content -->
    </div><!-- am-sideleft -->

    <!--검색 창 -->
    <div class="am-pagetitle">
      <h5 class="am-title">Post Management</h5>
      
          <form action="managerPage.jsp?tab=0" method="post">
               <select name="where">
                  <option value="content">해쉬태그</option>
                  <option value="username">작성자</option>                 
               </select>
               <input type="text" name="is"/>
               <input type="submit" value="submit"/>
            </form>            
    </div>
    
     <div class="am-pagetitle">
      <h5 class="am-title">User Management</h5>
      
              <form action="managerPage.jsp?tab=1" method="post">
               <select name="where_2">
                  <option value="username">닉네임</option>
                    <option value="email">이메일</option>                 
                    <option value="profile_comment">프로필 코멘트</option>
               </select>
               <input type="text" name="is"/>
               <input type="submit" value="submit"/>
            </form>         
    </div>
   
    <div class="am-mainpanel">
    
    
    
    <!-- tab1 -->
      <div class="am-pagebody">
        <div class="card pd-20 pd-sm-40">
          <h6 class="card-body-title">Basic Responsive DataTable</h6>   
          <div class="table-wrapper">
            <table id="datatable1" class="table display responsive nowrap">
              <thead>
                <tr>
                  <th><input type="checkbox" id="checkall"></th>
                  <th class="wd-15p">글번호</th>
                  <th class="wd-15p">해시태그</th>
                  <th class="wd-20p">작성자</th>
                  <th class="wd-15p">좋아요</th>
                  <th class="wd-10p">신고</th>
                  <th class="wd-25p">게시글 이동</th>
                </tr>
              </thead>
              
              <!-- 글 반복 -->
         	<form action='managerSub.jsp'>
            <input type="hidden" name="tab" value="0">
              <tbody>
              <% for(i=0;i<postList.size();i++) {
                  String nick; 
                  if(memDao.getProfile(postList.get(i).getUser_id()) == null)
                     nick = "탈퇴회원";
                  else
                     nick = memDao.getProfile(postList.get(i).getUser_id()).getNick();
               %>
                <tr>
                <td><input type="checkbox" name="check" value=<%=postList.get(i).getId()%>></td>
               <td><%=postList.get(i).getId()%></td>
               <td><%=postList.get(i).getContent()%></td>
               <td><%=nick%></td>
               <td><%=postDao.getLikeCount(postList.get(i).getId())%></td>
               <td><%=postDao.getReportCount(postList.get(i).getId())%></td>
               <td>
                  <a class="shift-button" href="${pageContext.request.contextPath}/singlePostView.jsp?post_id=<%= postList.get(i).getId()%>">
                      게시글 이동 
                  </a>
                 </td>
                </tr>
                   <% }%>
              </tbody>
            </table>
            <br>
            
            <input type="submit" value="글 삭제"/>
            </form>
          </div><!-- table-wrapper -->
        </div><!-- card -->
      </div><!-- am-pagebody -->
  
 
      
      <!-- tab2 -->
      <div class="am-pagebody">
        <div class="card pd-20 pd-sm-40">
          <h6 class="card-body-title">Basic Responsive DataTable</h6>   
          <div class="table-wrapper">
            <table id="datatable1" class="table display responsive nowrap"/>              
              <!-- 글 반복 -->
         <form action='managerSub.jsp?tab=1'>
            <input type="hidden" name="tab" value="1">
              <tbody>
               <tr><td><input type="checkbox" id="checkall_2"></td><td>유저 번호</td><td>닉네임</td><td>이메일</td><td>프로필 코멘트</td><td>이동</td></tr>
               <% for(i=0;i<memList.size();i++) {%>
            <tr>
            <td><input type="checkbox" name="check_2" value=<%=memList.get(i).getId()%>></td>
            <td><%=memList.get(i).getId()%></td>
            <td><%=memList.get(i).getNick()%></td>
            <td><%=memList.get(i).getEmail()%></td>
            <td><%=memList.get(i).getProfile_comment()%></td>
            <td>
              <a class="shift-button" href="${pageContext.request.contextPath}/profile/profilePage.jsp?user_id=<%= memList.get(i).getId()%>">
                프로필 이동 
            </a>
            </td>
            </tr>
                   <% }%>
              </tbody>
            </table>
            <br>
            <input type="submit" value="회원삭제"/>
          </form>
          </div><!-- table-wrapper -->
        </div><!-- card -->
      </div><!-- am-pagebody -->
 


      <div class="am-footer">
        <span>Copyright &copy;. All Rights Reserved. Sophist, the best team. Give them all A+.</span>
        <span>Created by: ThemePixels, Inc.</span>
      </div><!-- am-footer -->

    </div><!-- am-mainpanel -->
       <form action="logout.jsp">
                <input style="float:right; margin-right:12px;" type="submit" value="로그아웃" />
       </form>




  </body>
  </html>