package com.eshop.kosonm.customer;

import com.eshop.kosonm.configuration.HtmlUtil;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerFormValidator implements Validator {

   private EmailValidator emailValidator = EmailValidator.getInstance();

   @Override
   public boolean supports(Class<?> clazz) { // only fo customerForm
      return clazz == CustomerForm.class;
   }

   @Override
   public void validate(Object target, Errors errors) {
      CustomerForm custInfo = (CustomerForm) target;

      if (HtmlUtil.isHtml(custInfo.getName()) || HtmlUtil.isHtml(custInfo.getEmail())
            || HtmlUtil.isHtml(custInfo.getAddress()) || HtmlUtil.isHtml(custInfo.getPhone())) {
         throw new AccessDeniedException("Access Denied!");

      }
      
      // validates fields in customerForm
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.customerForm.name");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.customerForm.email");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.customerForm.address");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.customerForm.phone");

      if (!emailValidator.isValid(custInfo.getEmail())) {
         errors.rejectValue("email", "Pattern.customerForm.email");
      }
   }

}