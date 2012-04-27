package mjwall;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class RemoteClientSameJVM extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException { 
    PrintWriter out = resp.getWriter();
    new RemoteTester().run(out);
    out.println("Same JVM");
    out.close();
  }
}