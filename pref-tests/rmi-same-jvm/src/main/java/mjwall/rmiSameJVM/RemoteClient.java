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
    new RemoteTester().run(out);
    out.close();
  }
}
