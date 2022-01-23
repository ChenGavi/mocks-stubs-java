package co.interleap.mocks;

/**
 * @author Gavin Chen
 * @Date 2022 01 22
 */
public class MockEmailService implements EmailService{

    private EmailBody lastSentEmail;

    @Override
    public void send(EmailBody emailBody) {
        lastSentEmail = emailBody;
    }

    public EmailBody getLastEmail(){
        return lastSentEmail;
    }

}
