package mjwall.rmiSameJVM;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LocalClient extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException
  {
    PrintWriter out = resp.getWriter();
    int runsPerTest = 0;
    if (req.getParameter("runsPerTest") != null) {
      runsPerTest = Integer.parseInt(req.getParameter("runsPerTest"));
    }
    new LocalTester().run(out, runsPerTest);
    out.close();
  }
}