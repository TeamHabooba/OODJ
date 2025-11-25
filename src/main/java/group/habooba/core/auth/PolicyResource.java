package group.habooba.core.auth;

public interface PolicyResource extends PolicyAttributable {
    String policyResourceType();
    long policyId();
}