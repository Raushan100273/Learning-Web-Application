/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.blog.servlet;

import com.tech.blog.dao.Postdao;
import com.tech.blog.entities.Post;
import com.tech.blog.entities.User;
import com.tech.blog.helper.ConnectionProvider;
import com.tech.blog.helper.Helper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Administrator
 */
@MultipartConfig
public class Addpostservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           int cid=Integer.parseInt(request.getParameter("cid"));
           String ptitle=request.getParameter("ptitle");
           String pcontent=request.getParameter("pcontent");
          String pcode=request.getParameter("pcode");
          Part part=request.getPart("pic");
          String pic=Helper.extractFileName(part);
          //getting cuurrent user
          HttpSession session=request.getSession();
          User user=(User)session.getAttribute("currentUser");
          Post p=new Post(ptitle,pcontent,pcode,pic,null,cid,user.getId());
          Postdao dao=new Postdao(ConnectionProvider.getConnection());
          if(dao.saveposts(p))
          {
              out.println("done");
              String SAVE_DIR="postimages";
              String savePath = "D:"+File.separator+"TechBlog"+File.separator+"web" + File.separator + SAVE_DIR;
               Helper.savefile(savePath);
               part.write(savePath + File.separator + pic);
              String filePath= savePath + File.separator + pic ;
              
              
          }
          else
              out.println("error");
          
          
        
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
