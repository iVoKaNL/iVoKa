package nl.ivoka.launch;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            String webDirectoryLocation = "src/web/";
            Tomcat tomcat = new Tomcat();

            // If port variable is not set, set it to default 8080
            String webPort = System.getenv("PORT");
            if (webPort == null || webPort.isEmpty()) {
                webPort = "8080";
            }

            tomcat.setPort(Integer.valueOf(webPort));
            tomcat.addWebapp("/", new File(webDirectoryLocation).getAbsolutePath());

            System.out.println("Configuring app with basedir: "+new File("./" + webDirectoryLocation).getAbsolutePath());

            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
