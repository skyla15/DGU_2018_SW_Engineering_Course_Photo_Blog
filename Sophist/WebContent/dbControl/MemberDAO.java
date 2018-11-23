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

    //�쉶�썝媛��엯 湲곕뒫 援ы쁽
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

    // 濡쒓렇�씤湲곕뒫 援ы쁽
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
                    x = 1; //�씤利앹꽦怨�
                else
                    x = 0; //�씤利앹떎�뙣
            }
            else
                x = -1; //�씪移섑븯�뒗 �씠硫붿씪 �뾾�쓬
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return x;
    }

    //�봽濡쒗븘 �젙蹂� �뼸�뼱�삤湲�
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

    // �봽�궗 �뼸�뼱�삤湲�
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

    //User Name �뼸�뼱�삤湲�
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

    //�쑀�� �냼媛쒓� 媛��졇�삤湲�
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

    //email濡� user_id媛��졇�삤
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

    //ID 以묐났 泥댄겕
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
                checkNum = 1;   //以묐났�븘�씠�뵒 議댁옱
            }else{
                checkNum = 0;   //以묐났�븘�씠�뵒 �뾾�쓬
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    //�봽濡쒗븘 蹂�寃쎌뿉�꽌 ID以묐났 泥댄겕
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
                    checkNum = 0;   //�쑀���꽕�엫�씠 以묐났�씠吏�留� �옄�떊�쓽 寃껋씠誘�濡� �삤瑜� �뾾�씠 �넻怨�
                }else{
                    checkNum = checkID(recievedNick);   //�쑀���꽕�엫�씠 蹂�寃쎈맂 寃쎌슦 checkID method瑜� �궗�슜
                }
            }else {
                checkNum = 1;      //�빐�떦 �븘�씠�뵒�� �쑀���꽕�엫�씠 議댁옱�븯吏� �븡�쑝誘�濡� �삤瑜�
            }
        }catch (Exception e){

        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    //EMAIL 以묐났 泥댄겕
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
                checkNum = 1;   //以묐났�씠硫붿씪 議댁옱
            }else{
                checkNum = 0;   //以묐났�씠硫붿씪 �뾾�쓬
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    // �쉶�썝�젙蹂� �닔�젙
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

    //�봽濡쒗븘 �궗吏� �닔�젙
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

}

