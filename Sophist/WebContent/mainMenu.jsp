<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="main-menu-bar">
          <span>
              <a href="${pageContext.request.contextPath}/index.jsp">
                  <img src="${pageContext.request.contextPath}/assets/icons/sophist.png" class="logo-image">
              </a>
          </span>
    	  <span>
              <form action="${pageContext.request.contextPath}/SearchRedirect.jsp" method="get" onsubmit="addSharp()" name="search">
                  <input type="text" placeholder="검색" class="main-menu-search-box search-placeholder" name="searchStr" id="searchStr">
              </form>            

          </span>
    <span>
    <%if(session.getAttribute("AuthLevel").equals("manager")){%>
        <a href="${pageContext.request.contextPath}/managerPage.jsp">
            <img src="${pageContext.request.contextPath}/assets/icons/manager.png" width="30px">
        </a>
    <%} %>
        <a href="${pageContext.request.contextPath}/totalPostView.jsp">
            <img src="${pageContext.request.contextPath}/assets/icons/explore.png" width="30px">
        </a>
        <a href="/Sophist/profile/profilePage.jsp?user_id=<%=session.getAttribute("memId")%>">
            <img src="${pageContext.request.contextPath}/assets/icons/profile.png" width="30px">
        </a>
        <a href="${pageContext.request.contextPath}/PostEdit/imageSelectPage.jsp">
            <img src="${pageContext.request.contextPath}/assets/icons/upload.png" width="30px">
        </a>        
        
    </span>
</div>

<script>
    function addSharp(){
        var hashContent = document.getElementById("searchStr").value;
        if(hashContent[0] != '@'){//@를 붙이면 유저검색
        	document.search.searchStr.value= "#" + hashContent;
	    }
        document.getElementByName("search").submit();
    }
</script>
