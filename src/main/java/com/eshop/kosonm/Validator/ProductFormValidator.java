package com.eshop.kosonm.Validator;

import com.eshop.kosonm.DAO.ProductDao;
import com.eshop.kosonm.Entity.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductFormValidator implements Validator {

   @Autowired
   private ProductDao productDao;

   @Override
   public boolean supports(Class<?> clazz) { // only for productForm
      return clazz == ProductForm.class;
   }

   @Override
   public void validate(Object target, Errors errors) {
      ProductForm productForm = (ProductForm) target;

      // validates productForm fields
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.productForm.code");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");

      String code = productForm.getCode();
      if (code != null && code.length() > 0) {
         if (code.matches("\\s+")) {
            errors.rejectValue("code", "Pattern.productForm.code");
         } else if (productForm.isNewProduct()) {
            Product product = productDao.findProduct(code);
            if (product != null) {
               errors.rejectValue("code", "Duplicate.productForm.code");
            }
         }
      }
   }

}