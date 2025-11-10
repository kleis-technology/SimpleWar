package ch.kleis.simplewar;

import java.io.IOException;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/version")
public class VersionServlet extends HttpServlet {
  private String version = "0.0.0";

  public VersionServlet() throws IOException, ServletException {
    Properties props = new Properties();
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    props.load(cl.getResourceAsStream("version.properties"));
    version = props.get("version").toString();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    resp.setHeader("Content-Type", "application/json");

    var out = resp.getOutputStream();
    out.println(String.format("{\"version\":\"%s\"}", version));
  }
}
