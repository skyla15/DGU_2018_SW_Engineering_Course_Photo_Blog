package dbControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemberDAO {
    DBConnect dbconnect = null;
    String sql = "";

    public MemberDAO() {
        dbconnect = new DBConnect();
    }

    //회원가입을 할 경우 호출하는 메소드
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
    public List<MemberDTO> getAllMember(){
    	Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MemberDTO>list = null;

        try{
        	sql = "SELECT * from insta.members";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<MemberDTO>();

                do{
                	MemberDTO user = new MemberDTO();
                	user.setId(rs.getInt("id"));
                	user.setNick(rs.getString("username"));
                	user.setEmail(rs.getString("email"));
                	user.setProfile_img(rs.getString("profile_img"));
                	user.setProfile_comment(rs.getString("profile_comment"));
                	
                    list.add(user);
                }while(rs.next());
            }else {
                list = Collections.EMPTY_LIST;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return list;
    }

    //로그인을 할 경우 호출하는 메소드
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
                    x = 1; //패스워드가 맞을시
                else
                    x = 0; //패스워드가 틀릴시
            }
            else
                x = -1; 
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return x;
    }

    //유저 아이디로부터 유저 정보를 반환오는 메소드
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

    // 유저 아이디로부터 프로필 이미지 주소를 반환하는 메소드
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

    //유저 아이디로부터 유저 이름을 반환하는 메소드
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
    //유저 이메일로 조회하여 유저들을 반환하는 메소드
    public List<MemberDTO> getUserList(String userID_EMAIL) throws Exception{
    	Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MemberDTO>list = null;

        try{
        	sql = "SELECT * from insta.members WHERE (username LIKE ?) OR (email LIKE ?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, "%"+userID_EMAIL+"%");
            pstmt.setString(2, "%"+userID_EMAIL+"%");

            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<MemberDTO>();

                do{
                	MemberDTO user = new MemberDTO();
                	user.setId(rs.getInt("id"));
                	user.setNick(rs.getString("username"));
                	user.setEmail(rs.getString("email"));
                	user.setProfile_img(rs.getString("profile_img"));
                	user.setProfile_comment(rs.getString("profile_comment"));
                	
                    list.add(user);
                }while(rs.next());
            }else {
                list = Collections.EMPTY_LIST;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return list;
    }

    //유저 아이디로 조회하여 프로플 코멘트를 반환하는 메소드
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
                profileComment = "�삤瑜섎컻�깮";
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return profileComment;
    }

    //email로 조회하여 유저 아이디를 반환하는 메소드
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

    //유저의 권한을 반환하는 메소드 (일반 유저 : normal, 관리자 : manager )
    public String getAuth(String user_email)throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userAuth = null;

        try{
            sql = "SELECT auth from insta.members WHERE email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user_email);
            rs = pstmt.executeQuery();

            if(rs.next()){
            	userAuth = rs.getString("auth");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return userAuth;
    }
    
    //유저 아이디로 존재유무를 체크하는 메소드
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
                checkNum = 1;   //존재하지 않으면
            }else{
                checkNum = 0;   //존재하면
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    //유저의 닉네임을 변경할 시 호출하는 메소드
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
                    checkNum = 0;   
                }else{
                    checkNum = checkID(recievedNick);   
                }
            }else {
                checkNum = 1;      
            }
        }catch (Exception e){

        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    //이메일르 멤버의 아이디를 반환하는 메소드
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
                checkNum = 1;   
            }else{
                checkNum = 0;   
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    // 회원의 정보를 갱신하는 메소드
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

    //회원의 프로필 사진을 갱신하는 메소드
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
    
    //유저가 팔로우 하고있는 회원들을 반환하는 메소드
    public List<MemberDTO> getFollowingList(int user_id) throws Exception{
    	//System.out.println(user_id);
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MemberDTO>list = null;

        try{
            sql = "SELECT * FROM insta.members " +
                    "WHERE id IN (SELECT following_id FROM insta.follow WHERE user_id = ?)" +
                    "ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, user_id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<MemberDTO>();

                do{
                	MemberDTO user = new MemberDTO();
                	user.setId(rs.getInt("id"));
                	user.setNick(rs.getString("username"));
                	user.setEmail(rs.getString("email"));
                	user.setProfile_img(rs.getString("profile_img"));
                	user.setProfile_comment(rs.getString("profile_comment"));
                	
                    list.add(user);
                }while(rs.next());
            }else {
                list = Collections.EMPTY_LIST;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return list;
    }
    //유저가 팔로우하고 있는 회원의 수를 반환하는 메소드
    public int getFollowingCount(int user_id) throws Exception{
    	//System.out.println(user_id);
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int FollowingNumber = 0;

        try{
            sql = "SELECT count(*)count FROM insta.members " +
            		"WHERE id IN (SELECT following_id FROM insta.follow WHERE user_id = ?)";
            
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, user_id);

            rs = pstmt.executeQuery();
            
            if(rs.next())
            	FollowingNumber = rs.getInt("count");


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return FollowingNumber;
    }
    
    //유저 아이디로 팔로우 하고있는 회원들을 반환하는 메소드
    public List<MemberDTO> getFollowerList(int user_id) throws Exception{
    	//System.out.println(user_id);
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MemberDTO>list = null;

        try{
            sql = "SELECT * FROM insta.members " +
                    "WHERE id IN (SELECT user_id FROM insta.follow WHERE following_id = ?)" +
                    "ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, user_id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<MemberDTO>();

                do{
                	MemberDTO user = new MemberDTO();
                	user.setId(rs.getInt("id"));
                	user.setNick(rs.getString("username"));
                	user.setEmail(rs.getString("email"));
                	user.setProfile_img(rs.getString("profile_img"));
                	user.setProfile_comment(rs.getString("profile_comment"));
                	
                    list.add(user);
                }while(rs.next());
            }else {
                list = Collections.EMPTY_LIST;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return list;
    }
    //
    public int getFollowerCount(int user_id) throws Exception{
    	//System.out.println(user_id);
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int FollowerNumber = 0;

        try{
            sql = "SELECT count(*)count FROM insta.members " +
                    "WHERE id IN (SELECT user_id FROM insta.follow WHERE following_id = ?)";
 
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, user_id);

            rs = pstmt.executeQuery();
            
            if(rs.next())
            	FollowerNumber = rs.getInt("count");


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return FollowerNumber;
    }
    //유저 아이디로 조회하여 회원을 삭제하는 메소드
    public int deleteMember(int userId) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int check = 0;

        try{
            sql="DELETE FROM insta.members WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);

            pstmt.executeUpdate();

            check = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }
    //유저 아이디로 멤버를 조회하여 삭제하는 메소드
    public int deleteRelateMember(int userId) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int check = 0;

        try{
            sql="DELETE FROM insta.members WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);

            pstmt.executeUpdate();

            check = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }
    // 컬럼 이름과 조건으로 멤버들을 반환하는 메소드
    public List<MemberDTO> getList(String where, String is) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<MemberDTO>list = null;

        try{
            sql = "SELECT * FROM insta.members WHERE "+where+" LIKE ? ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%"+is+"%");

            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<MemberDTO>();

                do{
                	MemberDTO user = new MemberDTO();
                	user.setId(rs.getInt("id"));
                	user.setNick(rs.getString("username"));
                	user.setEmail(rs.getString("email"));
                	user.setProfile_img(rs.getString("profile_img"));
                	user.setProfile_comment(rs.getString("profile_comment"));
                	
                    list.add(user);
                }while(rs.next());
            }else {
                list = Collections.EMPTY_LIST;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return list;
    }
}

