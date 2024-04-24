package sample.spring.chapter10.bankapp.job;

import java.io.IOException;
import java.util.List;

//import javax.mail.MessagingException;
//import javax.mail.internet.AddressException;

import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import sample.spring.chapter10.bankapp.EmailConfig;
import sample.spring.chapter10.bankapp.repository.FixedDepositRepository;
import sample.spring.chapter10.bankapp.domain.FixedDepositDetails;

@Slf4j
@Component
public class FixedDepositProcessorJob {

	@Autowired
	private FixedDepositRepository fixedDepositRepository;
//
//	@Autowired
//	private transient JavaMailSender mailSender;

//	@Autowired
//	@Qualifier("requestProcessedTemplate")
//	private transient SimpleMailMessage simpleMailMessage;

	@Autowired
	@Qualifier("requestProcessedSGTemplate")
	private Mail requestProcessedSGTemplate;

	@Autowired
	@Qualifier("sendGrid")
	private SendGrid sendGrid;

	@Autowired
	@Qualifier("sendEmailRequestSG")
	private Request requestSG;


	private List<FixedDepositDetails> getInactiveFixedDeposits() {
		return fixedDepositRepository.getInactiveFixedDeposits();
	}

	@Scheduled(fixedRate = 5000)
	public void sendEmail() {
		List<FixedDepositDetails> inactiveFds = getInactiveFixedDeposits();
		for (FixedDepositDetails fd : inactiveFds) {
			sendEmailSG(fd);
		}
		fixedDepositRepository.setFixedDepositsAsActive(inactiveFds);
	}

	/**
	 * send email via sendgrid service
	 * @param fd - FixedDepositDetails
	 */
	public void sendEmailSG(FixedDepositDetails fd) {
		if (EmailConfig.isEmailsEnabled()) {
			var personalization = new Personalization();
			personalization.addTo(new Email(fd.getEmail()));
			requestProcessedSGTemplate.addPersonalization(personalization);
			try {
				requestSG.setBody(requestProcessedSGTemplate.build());
				var response = sendGrid.api(requestSG);
				if (response.getStatusCode()!= 202) {
					log.warn("Email was not sent. Response:\n" + response.getStatusCode() + response.getBody());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			log.warn("Emails sending is disabled in app");
		}
	}
}
