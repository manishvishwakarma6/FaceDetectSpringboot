package com.abhiyantrikitech.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.abhiyantrikitech.model.FaceMaster;

@Repository
public class FaceDao {
  
	
	@Autowired
	ConnectionBean connBean;
  
  
 
    // Save upload file in database
    public FaceMaster saveUploadFile(FaceMaster faceMaster, Connection conn1) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            System.out.println("Saving file to Database: " + faceMaster.getFd_filename());
        	
            conn=connBean.getConnectionObj();
         
            pstmt = conn.prepareStatement(
                  "INSERT INTO tbl_face_detection (fd_filename, fd_status, fd_create_dt)"
                + " VALUES (?, ?, now())");

            pstmt.setString(1, faceMaster.getFd_filename());
            pstmt.setString(2, "Pending");

            int count = pstmt.executeUpdate();
            
            if (count > 0) {
                System.out.println(">>> file uploaded successfully <<<");
            } else {
                System.out.println(">>> File upload failed <<<");
            }
        } 
        
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        } 
        
        finally {
          
        	if (pstmt != null)
            	pstmt.close();
            
            if (conn1 == null && conn != null)
            	conn.close();  
        }
        return faceMaster;
    }
    
   
    // get file
    public List<FaceMaster> getPendingFaces() throws Exception {
           List<FaceMaster> list = new ArrayList<>();
           Connection conn = null;
       	   PreparedStatement pstmt = null;
      	   ResultSet rs = null;
      	   FaceMaster fm = null;
      	 try {
      	     conn = connBean.getConnectionObj();
      	     pstmt = conn.prepareStatement("SELECT fd_id, fd_filename FROM tbl_face_detection WHERE fd_status = 'Pending'");
      	     rs = pstmt.executeQuery(); 
      	    
      	     while (rs.next()) {
      	     	fm = new FaceMaster();
                fm.setFd_id(rs.getString("fd_id"));
                fm.setFd_filename(rs.getString("fd_filename"));
                list.add(fm);
      	   }    
    	     
    	 } finally {
    	     if (rs != null) rs.close();
    	     if (pstmt != null) pstmt.close();
    	     if (conn != null) conn.close();
    	 }

    	 return list;
    	}
    	

    // update database
    public void updateFaceInfo(
            String fd_id, 
            String originalBase64, 
            String detectedBase64, 
            String faceCount, 
            Connection conn
        ) throws Exception {

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(
                "UPDATE tbl_face_detection SET fd_original_img_base64 = ?, fd_detected_img_base64 = ?, " +
                "fd_face_count = ?, fd_status = 'Completed' WHERE fd_id = ?");

            pstmt.setString(1, originalBase64);
            pstmt.setString(2, detectedBase64);
            //pstmt.setString(3, faceCount);
            //pstmt.setString(4, fd_id);
            pstmt.setInt(3, Integer.parseInt(faceCount));
            pstmt.setInt(4, Integer.parseInt(fd_id));

            int count = pstmt.executeUpdate();
            if (count > 0) {
                System.out.println(">>> UPDATE file SUCCESS <<<");
            } else {
                System.out.println(">>> UPDATE file FAILED <<<");
            }

        } catch (Exception e) {
            System.out.println("Error while updating file: " + e.getMessage());
            throw e;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }


    
//  Duplicate Image Check 
    public boolean duplicateImageCheck(String fd_original_img_base64, String fd_id) throws Exception {
        int recexistcnt = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connBean.getConnectionObj();

            if (fd_id == null || fd_id.equals("") || fd_id.equals("0")) {
                pstmt = conn.prepareStatement("SELECT COUNT(*) cnt FROM tbl_face_detection WHERE fd_original_img_base64=?");
                pstmt.setString(1, fd_original_img_base64);
            } else {
                pstmt = conn.prepareStatement("SELECT COUNT(*) cnt FROM tbl_face_detection WHERE fd_original_img_base64=? AND fd_id <> ?");
                pstmt.setString(1, fd_original_img_base64);
                //pstmt.setString(2, fd_id);
                pstmt.setInt(2, Integer.parseInt(fd_id));

            }

            rs = pstmt.executeQuery();
            if (rs.next()) {
                recexistcnt = rs.getInt("cnt");
            }

            return recexistcnt > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null)
            	try { rs.close(); } 
            catch (Exception e) { }
            if (pstmt != null) 
            	try { pstmt.close(); } 
            catch (Exception e) { }
            if (conn != null) 
            	try { conn.close(); } 
            catch (Exception e) { }
        }
    }
    
    
}






