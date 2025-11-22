package group.habooba.backend;

public interface PolicyResource extends PolicyAttributable {
    String policyResourceType();
    long policyId();
}