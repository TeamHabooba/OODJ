package group.habooba.backend;

/** PermissionRecord
 * Describes one user permission.
 *
 * Related permissions:
 *
 */
class PermissionRecord {
    /**
     * Permission name.
     * Must not be null.
     * Must not be changed without
     * allow_permission_name_update permission.
     * Format: allow_add_new_user
     */
    private String name;

    /**
     * Permission description.
     * Can be null.
     * Must not be changed without
     * allow_permission_description_update permission.
     */
    private String description;

    /**
     * Permisssion value.
     * True = permission granted,
     * False = permission denied.
     * False by default.
     * Must not be changed without
     * allow_permission_value_update permission.
     */
    private boolean value;

    PermissionRecord(String name, String description, boolean value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    PermissionRecord(String name, boolean value) {
        this(name, "", value);
    }

    PermissionRecord(String name, String description) {
        this(name, description, false);
    }

    PermissionRecord(String name) {
        this(name, "", false);
    }

    String name(){
        return this.name;
    }

    String description(){
        return this.description;
    }

    boolean value(){
        return this.value;
    }

    void value(boolean value){
        this.value = value;
    }
}
