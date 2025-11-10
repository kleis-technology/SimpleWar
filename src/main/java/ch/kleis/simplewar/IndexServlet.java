package ch.kleis.simplewar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
  private String env = "DEV";

  public IndexServlet() throws IOException, ServletException {
    Object mEnv;
    // tomcat class loader hierarchy
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    InputStream input_stream = cl.getResourceAsStream("app.properties");

    if (input_stream != null) {
      Properties props = new Properties();
      props.load(input_stream);

      if ((mEnv = props.get("env_name")) != null) {
        env = mEnv.toString();
      }
    }

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    var out = resp.getOutputStream();
    out.println(String.format("Cette application tourne sur l'environment de %s !", env));
  }
}
