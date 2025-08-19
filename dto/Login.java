package dto;

@Data
public class Login {
    // Private fields
    private String identifier; // 'username' for Admin or 'email' for Doctor/Patient
    private String password;

    // Getters
    public String getIdentifier() {
        return this.identifier;
    }

    // Setters
    public void setIdentifier(String identifier) {
        if (identifier.toLowerCase() == "username" || identifier.toLowerCase() == "email") {
            this.identifier = identifier;
        }   
    }
}
