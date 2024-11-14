package auth.rancher;

import org.apache.ranger.authz.handler.RangerAuth;
import org.apache.ranger.authz.handler.RangerAuthHandler;
import org.apache.ranger.authz.handler.RangerAuth.AUTH_TYPE;
import org.apache.hadoop.security.authentication.server.AuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.security.cert.X509Certificate;
import java.util.Properties;

public class CustomTLSAuthProvider implements RangerAuthHandler {

    @Override
    public void initialize(Properties var1) throws Exception {
        // DO NOTHING FOR NOW
    }

    @Override
    public RangerAuth authenticate(HttpServletRequest request) {
        // Retrieve the client certificate from the request
        X509Certificate[] certChain = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");

        if (certChain == null || certChain.length == 0) {
            throw new IllegalArgumentException("No client certificate found.");
        }

        // Extract the subject from the certificate (CN is commonly used for username)
        String subjectDN = certChain[0].getSubjectDN().getName();
        //String username = extractUsernameFromDN(subjectDN);


        AuthenticationToken token = new AuthenticationToken(subjectDN, subjectDN, "casport");
        //token.
        // Create the user based on the certificate subject
        //User user = new User(username);

        // Optionally, check if the user exists, and if not, create them (assuming integration with a user store)
        //createUserIfNotExists(user);
        RangerAuth rangerAuth = new RangerAuth(token, AUTH_TYPE.JWT_JWKS);


        // Return successful authentication result
        return rangerAuth;
    }

    /*private String extractUsernameFromDN(String subjectDN) {
        // Assuming the DN is of the form CN=username,OU=... (extract CN)
        String[] parts = subjectDN.split(",");
        for (String part : parts) {
            if (part.startsWith("CN=")) {
                return part.substring(3);  // Return the username part
            }
        }
        return null;  // Return null if CN is not found
    }*/

    /*private void createUserIfNotExists(User user) {
        // Here you would integrate with your user store to check if the user exists
        // and create them if needed. For example, interacting with a database or an LDAP server.
        // This is a placeholder to simulate user creation.
        System.out.println("User created or verified: " + user.getName());
    }*/
}
