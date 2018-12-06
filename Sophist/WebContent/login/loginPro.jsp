<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="dbControl.MemberDAO" %>

<%
    // 한글 처리를 위해 받아온 값의 인코딩을 UTF로 해준다(모든 페이지에서 동일)
    request.setCharacterEncoding("UTF-8");

    String email = request.getParameter("email");
    String password = request.getParameter("password");
    
	   // 회원 테이블에 접근하여 값을 가져오는 MemberDAO의 인스턴스를 memberDao라는 이름으로 생성함
    MemberDAO memberDao = new MemberDAO();
    // memberDao는 인증 여부를 int로 알려준다.(1 - 성공 / 0 - 실패 / -1 -에러)
    int check = memberDao.userCheck(email, password);

    if(check==1){
        //인증성공하면 유저의 id를 memId라는 이름의 세션으로 지정한다. 앞으로 이 세션으로 로그인 인증을 하게 됨
        session.setAttribute("memId", memberDao.getUser_id(email));
        String auth = memberDao.getAuth(email);
        session.setAttribute("AuthLevel", auth);
        if(auth.equals("manager")){
        	%>
        	<script>
        	location.href = "${pageContext.request.contextPath}/managerPage.jsp";
        	</script>
        	<%
        }
%>
	<!-- 로그인 성공, 인덱스 이 -->
        <script>
        	location.href = "${pageContext.request.contextPath}/index.jsp";           
        </script>
        로그인 성공, 이동할 페이지 입력 
<%
    }else if(check==0){
        //비밀번호 불일치
%>
        <script>
            alert("비밀번호가 맞지 않습니다.");
            history.go(-1);
        </script>
<%
    }else {
%>
        <script>
            alert("존재하지 않는 계정입니다.");
            history.go(-1);
        </script>
<%
    }
%>