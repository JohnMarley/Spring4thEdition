package sample.spring.chapter10.bankapp.jms;


import jakarta.jms.JMSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sample.spring.chapter10.bankapp.repository.BankAccountRepositoryCustom;
import sample.spring.chapter10.bankapp.repository.FixedDepositRepository;
import sample.spring.chapter10.bankapp.domain.FixedDepositDetails;

/**
 * This class shows usage of @JmsListener annotation.
 */
@Component
public class MyAnnotatedJmsListener {
	private static Logger logger = LogManager.getLogger(MyAnnotatedJmsListener.class);

//	@Autowired
//	private transient MailSender mailSender;

//	@Autowired
//	@Qualifier("requestReceivedTemplate")
//	private transient SimpleMailMessage simpleMailMessage;

	@Autowired
	private FixedDepositRepository fixedDepositRepository;

	@Autowired
	private BankAccountRepositoryCustom bankAccountRepositoryCustom;

//	public void sendEmail() {
//		mailSender.send(simpleMailMessage);
//	}

	@JmsListener(destination = "EMAIL_QUEUE_DESTINATION")
	public void processEmailMessage(Message<String> message) {
		logger.info("processEmailMessage() invoked");
		logger.info(message.toString());
//		simpleMailMessage.setTo(message.getPayload());
//		sendEmail();
	}

	@JmsListener(destination = "FIXED_DEPOSIT_DESTINATION")
	public void processFixedDeposit(Message<FixedDepositDetails> message) throws JMSException {
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
