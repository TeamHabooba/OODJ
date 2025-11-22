package group.habooba.backend.auth;

public interface PolicyResource extends PolicyAttributable {
    String policyResourceType();
    long policyId();
}