package mjwall.rmiSameJVM;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RemoteClient extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException
  {
    PrintWriter out = resp.getWriter();
    int runsPerTest = 0;
    if (req.getParameter("runsPerTest") != null) {
      runsPerTest = Integer.parseInt(req.getParameter("runsPerTest"));
    }
    new RemoteTester().run(out, runsPerTest);
    out.close();
  }
}
