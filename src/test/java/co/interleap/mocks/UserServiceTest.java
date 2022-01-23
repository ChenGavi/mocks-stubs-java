package co.interleap.mocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void shouldSendWelcomeEmail() {

        //Rename DummyEmailService to MockEmailService
        final var mockEmailService = new MockEmailService();
        final var userService  = new UserService(null, mockEmailService);
        userService.sendWelcomeEmail("test@email.co");
        final var expectEmail = new EmailBody("Welcome", "Welcome to the portal", "test@email.co");
        final var actualEmail = mockEmailService.getLastEmail();
        assertEquals(expectEmail, actualEmail);
    }

    @Test
    void shouldSendWelcomeEmail_2() {
        final var mockEmailService = mock(EmailService.class);
        final var userService  = new UserService(null, mockEmailService);
        userService.sendWelcomeEmail("test@email.co");
        final var expectEmail = new EmailBody("Welcome", "Welcome to the portal", "test@email.co");
        verify(mockEmailService).send(expectEmail);
    }

    @Test
    void shouldSendRegisteredPhoneNumberIfUserAccountExists() {
        //Rename FakeUserRepository to StubUserRepository
        // setup user repository to return the phone number 1122334455 for existing@user.com
        final var mockEmailService = new MockEmailService();
        final var userService = new UserService(new StubUserRepository(), mockEmailService);
        userService.sendRegisteredPhoneNumber("existing@user.com");
        final var expected = new EmailBody("Account Details",
                "Here is your Registered Phone Number: \n" + "1122334455", "existing@user.com");
        final var actual = mockEmailService.getLastEmail();
        assertEquals(expected, actual);
    }

    @Test
    void shouldSendRegisteredPhoneNumberIfUserAccountExists_2() throws NotFoundException {

        final var mockEmailService = mock(MockEmailService.class);
        final var stubUserRepository = mock(StubUserRepository.class);
        when(stubUserRepository.findByEmail("existing@user.com"))
                .thenReturn(new User("1122334455", "existing@user.com", "Existing User"));

        final var userService = new UserService(new StubUserRepository(), mockEmailService);
        userService.sendRegisteredPhoneNumber("existing@user.com");
        final var expected = new EmailBody("Account Details",
                "Here is your Registered Phone Number: \n" + "1122334455", "existing@user.com");
//        final var actual = mockEmailService.getLastEmail();
        verify(mockEmailService).send(expected);
    }

    @Test
    void sendAccountNotFoundEmailForUnregisteredUsersWhenTryingToGetRegisteredPhoneNumber(){
        final var mockEmailSerivce = new MockEmailService();
        final var userService = new UserService(new StubUserRepository(), mockEmailSerivce);
        userService.sendRegisteredPhoneNumber("other@user.com");
        final var expected = new EmailBody("Account Not Found",
                "We do not have a registered account matching your email address", "other@user.com");
        final var actual = mockEmailSerivce.getLastEmail();
        assertEquals(expected, actual);
    }

    @Test
    void sendAccountNotFoundEmailForUnregisteredUsersWhenTryingToGetRegisteredPhoneNumber_2() throws NotFoundException {
        final var mockEmailService = mock(MockEmailService.class);
        final var stubUserRepository = mock(StubUserRepository.class);
        when(stubUserRepository.findByEmail("other@user.com"))
                .thenThrow(new NotFoundException());

//        final var userService = new UserService(new StubUserRepository(), mockEmailService);
//        userService.sendRegisteredPhoneNumber("other@user.com");
//        final var expected = new EmailBody("Account Not Found",
//                "We do not have a registered account matching your email address", "other@user.com");
////        final var actual = mockEmailService.getLastEmail();
////        assertEquals(expected, actual);
//        verify(mockEmailService).send(expected);
    }
}