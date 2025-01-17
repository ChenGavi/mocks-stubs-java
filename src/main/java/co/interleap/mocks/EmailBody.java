package co.interleap.mocks;

import lombok.ToString;

import java.util.Objects;

@ToString
public record EmailBody(String subject, String body, String receiver) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailBody emailBody = (EmailBody) o;
        return subject.equals(emailBody.subject) && body.equals(emailBody.body) && receiver.equals(emailBody.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, body, receiver);
    }
}
