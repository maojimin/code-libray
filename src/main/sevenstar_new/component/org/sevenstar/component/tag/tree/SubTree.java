package org.sevenstar.component.tag.tree;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class SubTree extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
     //   SystemContext.setHttpRequest(request);
        try {

            response.setContentType("text/html;charset=utf-8");
            OutputStream output = null;
            String pram = request.getParameter("act");
            //实现subtree载入

            if ("sub".equals(pram)) {

                PrintWriter print = response.getWriter();
                print.print("<script>\r\n<!--\r\n");
                print.print(Constants.getTreeJS());
                print.print("\r\n //-->\r\n</script>");
                String key = request.getParameter("id");
                Tree tree = TreeFactory.getInstance(Constants.IMP_TREE_CLASS);
                String name = tree.getSubTree(key);
                print.print("<script> \r\n");
                print.print("\r\ntree.addChildren(\"" + key + "\",\"" + name
                        + "\");");
                print.print("\r\n</script>");
                print.close();

            } else {
                output = response.getOutputStream();

                byte data[] = new byte[1024]; //缓冲字节数

                outImg(output, data, "org/sevenstar/component/tag/tree/pic/" + pram
                        + ".gif");

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
   //         SystemContext.realseHttpRequest();

        }

        //output.close();
    }

    private void outImg(OutputStream output, byte[] data, String path)
            throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        InputStream in = cl.getResourceAsStream(path);

        BufferedInputStream bis = new BufferedInputStream(in); //输入缓冲流
        BufferedOutputStream bos = new BufferedOutputStream(output); //输出缓冲流

        int size = 0;
        size = bis.read(data);

        while (size != -1) {

            bos.write(data, 0, size);
            size = bis.read(data);
        }
        bis.close();
      //  bos.flush(); //清空输出缓冲流
        bos.close();
    }

}
