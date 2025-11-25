package group.habooba.core.auth;

import java.util.List;

public interface PolicyRepository {
    List<Policy> loadPolicies();
    void savePolicies(List<Policy> policies);

}
