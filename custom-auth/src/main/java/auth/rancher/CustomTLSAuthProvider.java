package auth.rancher;

import org.apache.ranger.admin.client.RangerAdminRESTClient;
import org.apache.ranger.authz.handler.RangerAuth;
import org.apache.ranger.authz.handler.RangerAuthHandler;
import org.apache.ranger.authz.handler.RangerAuth.AUTH_TYPE;
import org.apache.ranger.plugin.util.GrantRevokeRoleRequest;

import com.google.common.collect.Sets;

import org.apache.hadoop.security.authentication.server.AuthenticationToken;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;

import java.security.cert.X509Certificate;
import java.util.Properties;

public class CustomTLSAuthProvider implements RangerAuthHandler {
    private RangerAdminRESTClient client;

    @Override
    public void initialize(Properties var1) throws Exception {
        client = new RangerAdminRESTClient();
        client.init("customtls", "appid", "propertyPrefix", null);
    }

    @Override
    public RangerAuth authenticate(HttpServletRequest request) {
        // Retrieve the client certificate from the request
        X509Certificate[] certChain = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");

        //
        if (certChain == null || certChain.length == 0) {
            throw new IllegalArgumentException("No client certificate found.");
        }

        // Extract the subject from the certificate (CN is commonly used for username)
        X500Principal subjectDN = certChain[0].getSubjectX500Principal();
        X500Principal issuerDN = certChain[0].getIssuerX500Principal();

        // Create the user based on the certificate subject
        AuthenticationToken token = new AuthenticationToken(subjectDN.getName(), subjectDN.getName(), "certificate");

        // Optionally, check if the user exists, and if not, create them (assuming integration with a user store)
        RangerAuth rangerAuth = new RangerAuth(token, AUTH_TYPE.JWT_JWKS);

        // Create a role request to add
        GrantRevokeRoleRequest grantRequest = new GrantRevokeRoleRequest();
        grantRequest.setUsers(Sets.newHashSet(subjectDN.getName()));
        grantRequest.setRoles(Sets.newHashSet("123A","987B"));
        try {
            client.grantRole(grantRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Return successful authentication result
        return rangerAuth;
    }

}
