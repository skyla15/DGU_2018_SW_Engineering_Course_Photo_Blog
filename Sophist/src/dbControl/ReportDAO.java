package dbControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportDAO {
    DBConnect dbConnect = null;
    String sql = "";

    public ReportDAO(){ dbConnect = new DBConnect();}

    public int report(int user_id,int post_id, String report_reason) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql="INSERT INTO insta.report(user_id, post_id, reason) VALUES(?,?,?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, user_id);
            pstmt.setInt(2, post_id);
            pstmt.setString(3, report_reason);
            pstmt.executeUpdate();

            check = 1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }

    public int unreport(int user_id, int post_id) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql = "DELETE FROM insta.report WHERE user_id = ? AND post_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, post_id);
            pstmt.executeUpdate();

            check = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }

    public boolean isReports(int user_id, int post_id) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        boolean isReport = false;

        try{
            sql = "SELECT * FROM insta.report WHERE user_id = ? AND post_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, post_id);

            rs = pstmt.executeQuery();

            if (rs.next()){
            	isReport = true;
            }else{
            	isReport = false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return isReport;
    }
}
