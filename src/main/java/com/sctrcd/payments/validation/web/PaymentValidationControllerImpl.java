package com.sctrcd.payments.validation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sctrcd.payments.facts.Payment;
import com.sctrcd.payments.validation.FxPaymentValidationResult;
import com.sctrcd.payments.validation.IbanValidationResult;
import com.sctrcd.payments.validation.IbanValidator;
import com.sctrcd.payments.validation.PaymentValidator;

@Controller("paymentValidationController")
public class PaymentValidationControllerImpl implements
        PaymentValidationController {

    private static Logger log = LoggerFactory
            .getLogger(PaymentValidationControllerImpl.class);
    
    @Autowired(required = true)
    @Qualifier("ruleBasedPaymentValidator")
    private PaymentValidator paymentValidator;

    @Autowired(required = true)
    @Qualifier("ruleBasedIbanValidator")
    private IbanValidator ibanValidator;

    @Override
    @RequestMapping(value = "/iban/validator", method = RequestMethod.GET, headers = "Accept=text/html")
    public ModelAndView validator() {
        ModelAndView mav = new ModelAndView("iban/validator");
        return mav;
    }

    @Override
    @RequestMapping(value = "/iban/validate/{iban}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody IbanValidationResult validateIban(@PathVariable String iban) {
        IbanValidationResult result = ibanValidator.validateIban(iban);
        log.debug("Validated IBAN: " + result.toString());
        return result;
    }

    @Override
    @RequestMapping(value = "/payment/validate", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody FxPaymentValidationResult validatePayment(Payment payment) {
        FxPaymentValidationResult result = paymentValidator.validatePayment(payment);
        log.debug("Validated payment: " + result.toString());
        return result;
    }

    public IbanValidator getIbanValidator() {
        return ibanValidator;
    }

    public void setValidator(IbanValidator validator) {
        this.ibanValidator = validator;
    }

    public PaymentValidator getPaymentValidator() {
        return paymentValidator;
    }

    public void setValidator(PaymentValidator validator) {
        this.paymentValidator = validator;
    }

}
