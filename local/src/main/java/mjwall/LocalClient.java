package mjwall;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LocalClient extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException
  {
    PrintWriter out = resp.getWriter();
    new LocalTester().run(out);
    out.close();
  }
}