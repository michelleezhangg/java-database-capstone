package repository;

@Repository
public class AdminRepository extends JpaRepository<AdminRepository, Long> {
    private DdbTable usersTable;
    
    public AdminRepository findByUsername(final String username) {
        final User user = usersTable.find(username);
        return Admin.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .name(user.getName())
            .build();
    }
}
