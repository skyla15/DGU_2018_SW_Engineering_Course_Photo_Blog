package dbControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {
    DBConnect dbconnect = null;
    String sql = "";

    public MemberDAO() {
        dbconnect = new DBConnect();
    }

    //회원가입 기능 구현
    public void insertMember(MemberDTO dto) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        try {
            sql = "INSERT INTO insta.members(username, EMAIL, pass) VALUES(?,?,?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, dto.getNick());
            pstmt.setString(2, dto.getEmail());
            pstmt.setString(3, dto.getPassword());

            pstmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
    }

    // 로그인기능 구현
    public int userCheck(String email, String password) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String dbpassword = "";
        int x = -1;

        try {
            sql = "SELECT pass from members WHERE email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if(rs.next()){
                dbpassword = rs.getString("pass");
                if(dbpassword.equals(password))
                    x = 1; //인증성공
                else
                    x = 0; //인증실패
            }
            else
                x = -1; //일치하는 이메일 없음
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return x;
    }

    //프로필 정보 얻어오기
    public MemberDTO getProfile(int user_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        MemberDTO profile = null;

        try{
            sql="SELECT * FROM insta.members WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                profile = new MemberDTO();
                profile.setId(rs.getInt("id"));
                profile.setNick(rs.getString("username"));
                profile.setPassword(rs.getString("pass"));
                profile.setProfile_img(rs.getString("profile_img"));
                profile.setProfile_comment(rs.getString("profile_comment"));
            }else{
                profile = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return profile;
    }

    // 프사 얻어오기
    public String getProfileImg(int user_id)throws Exception{
        Connection  con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String profileImg = null;

        try{
            sql = "SELECT profile_img from insta.members WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                profileImg = rs.getString("profile_img");
            }else{
                profileImg = "/images/no_profile_img.jpg";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return profileImg;
    }

    //User Name 얻어오기
    public String getUsername(int user_id)throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String nickName = null;

        try{
            sql = "SELECT username from insta.members WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                nickName = rs.getString("username");
            }else{
                nickName = "username_error";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return nickName;
    }

    //유저 소개글 가져오기
    public String getProfileComment(int user_id){
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String profileComment = "";

        try{
            sql = "SELECT profile_comment FROM insta.members where id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                profileComment = rs.getString("profile_comment");
            }else{
                profileComment = "오류발생";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return profileComment;
    }

    //email로 user_id가져오
    public int getUser_id(String user_email)throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int user_id = 0;

        try{
            sql = "SELECT id from insta.members WHERE email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user_email);
            rs = pstmt.executeQuery();

            if(rs.next()){
                user_id = rs.getInt("id");
            }else{
                user_id = 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return user_id;
    }

    //ID 중복 체크
    public int checkID(String id) {
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String recievedId = id;
        int checkNum = -1;

        try {
            sql = "SELECT * FROM insta.members where username = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, recievedId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                checkNum = 1;   //중복아이디 존재
            }else{
                checkNum = 0;   //중복아이디 없음
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    //프로필 변경에서 ID중복 체크
    public int changeNickCheck(int user_id, String nick) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int checkNum = -1;
        String recievedNick = nick;

        try{
            sql="SELECT username FROM insta.members WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                String dbUserName = rs.getString("username");
                if (dbUserName.equals(recievedNick)){
                    checkNum = 0;   //유저네임이 중복이지만 자신의 것이므로 오류 없이 통과
                }else{
                    checkNum = checkID(recievedNick);   //유저네임이 변경된 경우 checkID method를 사용
                }
            }else {
                checkNum = 1;      //해당 아이디와 유저네임이 존재하지 않으므로 오류
            }
        }catch (Exception e){

        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    //EMAIL 중복 체크
    public int checkEmail(String email) {
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String recievedEmail = email;
        int checkNum = -1;

        try {
            sql = "SELECT * FROM insta.members where email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, recievedEmail);
            rs = pstmt.executeQuery();

            if(rs.next()){
                checkNum = 1;   //중복이메일 존재
            }else{
                checkNum = 0;   //중복이메일 없음
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    // 회원정보 수정
    public int updateProfile(MemberDTO member) {
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql="UPDATE insta.members SET username=?, pass=?, profile_comment=? WHERE id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getNick());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getProfile_comment());
            pstmt.setInt(4, member.getId());
            pstmt.executeUpdate();

            check = 1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }

    //프로필 사진 수정
    public int updateProfileImage(int user_id, String image) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;
        int userId = user_id;
        String imagePath = "/images/" + image;

        try {
            sql = "UPDATE insta.members SET profile_img=? WHERE id = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, imagePath);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();

            check = 1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }

}

