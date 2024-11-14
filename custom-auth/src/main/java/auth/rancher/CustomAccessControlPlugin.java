package auth.rancher;

import java.util.Collection;

import org.apache.ranger.plugin.policyengine.RangerAccessRequest;
import org.apache.ranger.plugin.policyengine.RangerAccessResult;
import org.apache.ranger.plugin.policyengine.RangerAccessResultProcessor;
import org.apache.ranger.plugin.policyengine.RangerPluginContext;
import org.apache.ranger.plugin.service.RangerBasePlugin;

public class CustomAccessControlPlugin extends RangerBasePlugin {
    private static final RangerAccessResultProcessor PROCESSOR = new RangerAccessResultProcessor() {

        @Override
        public void processResult(RangerAccessResult arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'processResult'");
        }

        @Override
        public void processResults(Collection<RangerAccessResult> arg0) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'processResults'");
        }

    };
    
    public CustomAccessControlPlugin() {
        super("a", "b");
        setResultProcessor(PROCESSOR);
    }

    @Override
    public RangerAccessResult isAccessAllowed(RangerAccessRequest request) {
        String username = request.getUser();

        ExternalRoleManagementSystem externalRoleManagementSystem = new ExternalRoleManagementSystem();

        RangerAccessResult result = new RangerAccessResult();
        request.
        return this.isAccessAllowed(request, this.resultProcessor);
   }

}
