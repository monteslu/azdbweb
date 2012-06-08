package com.azprogrammer.mappings.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.azprogrammer.db.web.*;
import com.azprogrammer.mappings.*;

import java.util.zip.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: LGPL</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Source extends HttpServlet {

  //Initialize global variables
  public void init() throws ServletException {
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try

{

        String respCode = "";
        String beanName = request.getParameter("beanName");
        if("bean".equals(request.getParameter("type"))){
          response.setContentType("text/java");
          BeanMetaData bean = (BeanMetaData) request.getSession().getAttribute(beanName);
          SourceGenerator sg = new SourceGenerator(bean);
          if("bean".equals(request.getParameter("part"))){
            respCode = sg.getBeanCode();
            response.setHeader("Content-Disposition","attachment; filename=\"" + beanName + ".java" + "\"");
          }else if("key".equals(request.getParameter("part"))){
            respCode = sg.getKeyCode();
            response.setHeader("Content-Disposition","attachment; filename=\"" + beanName + "Key.java" + "\"");
          }else if("director".equals(request.getParameter("part"))){
            respCode = sg.getDirectorCode();
            response.setHeader("Content-Disposition","attachment; filename=\"" + beanName + "Director.java" + "\"");
          }else if("query".equals(request.getParameter("part"))){
            respCode = sg.getQueryCode();
            response.setHeader("Content-Disposition","attachment; filename=\"" + beanName + "Query.java" + "\"");
          }

        }else if("jsp".equals(request.getParameter("type"))){
          response.setContentType("text/java");
          BeanMetaData bean = (BeanMetaData) request.getSession().getAttribute(beanName);
          SourceGenerator sg = new SourceGenerator(bean);
          if("list".equals(request.getParameter("part"))){
            respCode = sg.buildList();
            response.setHeader("Content-Disposition","attachment; filename=\"" + beanName + "List.jsp" + "\"");
          }else if("addNew".equals(request.getParameter("part"))){
            respCode = sg.buildForm(null);
            response.setHeader("Content-Disposition","attachment; filename=\"addNew" + beanName + ".jsp" + "\"");
          }else if("edit".equals(request.getParameter("part"))){
            respCode = sg.buildForm("a" + bean.getBeanName());
            response.setHeader("Content-Disposition","attachment; filename=\"edit" + beanName + ".jsp" + "\"");
          }else if("save".equals(request.getParameter("part"))){
            respCode = sg.buildSavePage();
            response.setHeader("Content-Disposition","attachment; filename=\"save" + beanName + ".jsp" + "\"");
          }

        } else if("zip".equals(request.getParameter("type"))){
          BeanMetaData bean = (BeanMetaData) request.getSession().getAttribute(beanName);
          SourceGenerator sg = new SourceGenerator(bean);


        //make eight files in the temp dir
        String tempDir = System.getProperty("java.io.tmpdir");
        System.out.print("temp dir = " + tempDir);
        String tempName = "" + System.currentTimeMillis() + "" + Math.random();

        File listFile = new File(tempDir + File.separator + tempName + "list.jsp" );
        writeFile(listFile,sg.buildList());
        FileInputStream listIS = new FileInputStream(listFile);

        File saveFile = new File(tempDir + File.separator + tempName + "save.jsp" );
        writeFile(saveFile,sg.buildSavePage());
        FileInputStream saveIS = new FileInputStream(saveFile);

        File newFile = new File(tempDir + File.separator + tempName + "new.jsp" );
        writeFile(newFile,sg.buildForm(null));
        FileInputStream newIS = new FileInputStream(newFile);

        File editFile = new File(tempDir + File.separator + tempName + "edit.jsp" );
        writeFile(editFile,sg.buildForm("a" + bean.getBeanName()));
        FileInputStream editIS = new FileInputStream(editFile);


        File beanFile = new File(tempDir + File.separator + tempName + "bean.java" );
        writeFile(beanFile,sg.getBeanCode());
        FileInputStream beanIS = new FileInputStream(beanFile);

       File keyFile = new File(tempDir + File.separator + tempName + "key.java" );
       writeFile(keyFile,sg.getKeyCode());
       FileInputStream keyIS = new FileInputStream(keyFile);

       File direcFile = new File(tempDir + File.separator + tempName + "director.java" );
       writeFile(direcFile,sg.getDirectorCode());
       FileInputStream direcIS = new FileInputStream(direcFile);

       File queryFile = new File(tempDir + File.separator + tempName + "query.java" );
       writeFile(queryFile,sg.getQueryCode());
       FileInputStream queryIS = new FileInputStream(queryFile);

       File springFile = new File(tempDir + File.separator + tempName + ".xml" );
       writeFile(springFile,sg.getDirectorSpringCode());
       FileInputStream springIS = new FileInputStream(springFile);

          response.setContentType("application/zip");
          response.setHeader("Content-Disposition","attachment; filename=\"" + request.getParameter("beanName") + ".zip" + "\"");

          //ByteArrayOutputStream bout=new ByteArrayOutputStream();

          ServletOutputStream out = response.getOutputStream();
          ZipOutputStream zout=new ZipOutputStream(out);

          //putNextEntry(listIS, zout, beanName + "List.jsp");
          //putNextEntry(editIS, zout, "edit" + beanName + ".jsp");
          //putNextEntry(saveIS, zout, "save" + beanName + ".jsp");
          //putNextEntry(newIS, zout, "addNew" + beanName + ".jsp");

          putNextEntry(beanIS, zout, getPackageDir(bean.getPackageName()) + beanName + ".java");
          putNextEntry(keyIS, zout, getPackageDir(bean.getPackageName() + ".dao") + beanName + "Key.java");
          putNextEntry(direcIS, zout, getPackageDir(bean.getPackageName() + ".dao") + beanName + "Director.java");
          putNextEntry(queryIS, zout, getPackageDir(bean.getPackageName() + ".dao") + beanName + "Query.java");
          putNextEntry(springIS, zout, getPackageDir(bean.getPackageName() + ".dao") + beanName + ".xml");
          

          //clean up

          listFile.delete();
          editFile.delete();
          saveFile.delete();
          newFile.delete();

          beanFile.delete();
          keyFile.delete();
          direcFile.delete();
          queryFile.delete();
          springFile.delete ();


          //out.print(zip);
          out.flush();
          zout.close();



        }



        PrintWriter out = response.getWriter();
        out.print(respCode);
        out.flush();

}
catch(Throwable theException)
{
        // uncomment the following line when unexpected exceptions
        // are occuring to aid in debugging the problem.
        //theException.printStackTrace();
}


  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

  //Clean up resources
  public void destroy() {
  }


  public void putNextEntry(FileInputStream is, ZipOutputStream os, String filename) throws IOException{
    byte[] buf = new byte[4096];            //read buffer
    int retval;                             //return value
    //set entry field in zipfile; do this once for each
                //file you're compressing
                os.putNextEntry(new ZipEntry(filename));

                //read the input file in and write it to the zipfile
                do
                        {
                        //read file in from input stream
                        retval = is.read(buf, 0, 4096);


                        //-1 indicates end of file
                        if (retval != -1)
                        {
                                //write buffer to output stream
                                os.write(buf, 0, retval);
                        }


                        }while (retval != -1);
                //close this ZipEntry
              os.closeEntry();

              //done with input stream
              is.close();


  }

  public String getPackageDir(String packageName){
    if(packageName == null){
      return "";

    }
    else{
      return WebUtil.replace(packageName,".","/") + "/";
    }
  }

  public void writeFile(File file, String fileText) throws Exception{
    FileOutputStream os = new FileOutputStream(file);
    os.write(fileText.getBytes());
    os.flush();
    os.close();


  }
}
