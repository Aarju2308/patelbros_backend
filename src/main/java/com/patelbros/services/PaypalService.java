package com.patelbros.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaypalService {

	private final APIContext apiContext;
	
	public Payment createPayment(
			double total,
			String currency,
			String method,
			String intent,
			String description,
			String cancleUrl,
			String successUrl
	) throws PayPalRESTException {
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setTotal(String.format("%.2f",total));
		
		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		
		Payer payer = new Payer();
		payer.setPaymentMethod(method);
		
		Payment payment = new Payment();
		payment.setIntent(intent);
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancleUrl);
		redirectUrls.setReturnUrl(successUrl);
		
		payment.setRedirectUrls(redirectUrls);
		
		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId,String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		
		PaymentExecution execution = new PaymentExecution();
		execution.setPayerId(payerId);
		
		return payment.execute(apiContext, execution);
	}
}
