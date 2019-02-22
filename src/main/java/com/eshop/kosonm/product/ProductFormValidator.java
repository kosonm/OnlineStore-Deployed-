package com.eshop.kosonm.product;

import com.eshop.kosonm.product.ProductDao;
import com.eshop.kosonm.configuration.HtmlUtil;
import com.eshop.kosonm.product.Product;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductFormValidator implements Validator {

   private ProductDao productDao;

   @Autowired
   public ProductFormValidator(ProductDao productDao) {
      this.productDao = productDao;
   }

   @Override
   public boolean supports(Class<?> clazz) { // only for productForm
      return clazz == ProductForm.class;
   }

   @Override
   public void validate(Object target, Errors errors) {
      ProductForm productForm = (ProductForm) target;

      if (HtmlUtil.isHtml(productForm.getCode()) || HtmlUtil.isHtml(productForm.getName())) {
         throw new AccessDeniedException("Access Denied");
      }

      // validates productForm fields
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.productForm.code");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");

      String code = productForm.getCode();
      if (StringUtils.isNotEmpty(code)) {
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