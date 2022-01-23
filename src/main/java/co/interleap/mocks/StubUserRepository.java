package co.interleap.mocks;

/**
 * @author Gavin Chen
 * @Date 2022 01 22
 */
public class StubUserRepository implements UserRepository{

    @Override
    public User findByEmail(String email) throws NotFoundException {
        if(!"existing@user.com".equals(email)) throw new NotFoundException();
        return new User("1122334455", "existing@user.com", "Existing User");
    }
}
