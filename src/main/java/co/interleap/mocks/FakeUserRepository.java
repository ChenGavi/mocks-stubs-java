package co.interleap.mocks;

/**
 * @author Gavin Chen
 * @Date 2022 01 22
 */
public class FakeUserRepository implements UserRepository{

    @Override
    public User findByEmail(String email) {
        return new User("1122334455", "existing@user.com", "Existing User");
    }
}
