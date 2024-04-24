package sample.spring.chapter10.bankapp.jms;


import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import jakarta.jms.JMSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sample.spring.chapter10.bankapp.EmailConfig;
import sample.spring.chapter10.bankapp.repository.BankAccountRepositoryCustom;
import sample.spring.chapter10.bankapp.repository.FixedDepositRepository;
import sample.spring.chapter10.bankapp.domain.FixedDepositDetails;

import java.io.IOException;

/**
 * This class shows usage of @JmsListener annotation.
 */
@Component
public class MyAnnotatedJmsListener {
	private static Logger logger = LogManager.getLogger(MyAnnotatedJmsListener.class);

	@Autowired
	@Qualifier("mailSender")
	private transient MailSender mailSender;

	@Autowired
	@Qualifier("requestReceivedTemplate")
	private transient SimpleMailMessage simpleMailMessage;

	@Autowired
	@Qualifier("requestReceivedSGTemplate")
	private Mail requestReceivedSGTemplate;

	@Autowired
	@Qualifier("sendGrid")
	private SendGrid sendGrid;

	@Autowired
	@Qualifier("sendEmailRequestSG")
	private Request requestSG;

	@Autowired
	private FixedDepositRepository fixedDepositRepository;

	@Autowired
	private BankAccountRepositoryCustom bankAccountRepositoryCustom;


	public void sendEmail() {
		mailSender.send(simpleMailMessage);
	}

	/**
	 * send email via sendgrid service
	 * @param address - destination address
	 */
	public void sendEmailSG(String address) {
		if (EmailConfig.isEmailsEnabled()) {
			var personalization = new Personalization();
			personalization.addTo(new Email(address));
			requestReceivedSGTemplate.addPersonalization(personalization);
			try {
				requestSG.setBody(requestReceivedSGTemplate.build());
				var response = sendGrid.api(requestSG);
				if (response.getStatusCode()!= 202) {
					logger.warn("Email was not sent. Response:\n" + response.getStatusCode() + response.getBody());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logger.warn("Emails sending is disabled in app");
		}
	}

	@JmsListener(destination = "EMAIL_QUEUE_DESTINATION")
	public void processEmailMessage(Message<String> message) {
		logger.info("processEmailMessage() invoked");
		logger.info(message.toString());
//		simpleMailMessage.setTo(message.getPayload());
		sendEmailSG(message.getPayload());
	}

	@JmsListener(destination = "FIXED_DEPOSIT_DESTINATION")
	public void processFixedDeposit(Message<FixedDepositDetails> message) {
		logger.info("processFixedDeposit() invoked");
		FixedDepositDetails fdd = message.getPayload();
		if (fdd != null) {
			createFixedDeposit(fdd);
		}
	}

	@Transactional(transactionManager = "dbTxManager")
	public int createFixedDeposit(FixedDepositDetails fdd) {
		// -- create fixed deposit
		bankAccountRepositoryCustom.subtractFromAccount(fdd.getBankAccountId().getAccountId(), fdd.getFdAmount());
		return fixedDepositRepository.createFixedDeposit(fdd);
	}
}
