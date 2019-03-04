package com.eshop.kosonm.product;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.eshop.kosonm.product.Product;
import com.eshop.kosonm.product.ProductInfo;
import com.eshop.kosonm.pagination.PaginationResult;
import com.eshop.kosonm.product.ProductForm;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ProductDao {

    private EntityManager entityManager;

    @Autowired
    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Product findProduct(String code) {
        try {
            String sql = "Select e from " + Product.class.getName() + " e Where e.code =:code ";
            Session session = this.entityManager.unwrap(Session.class);
            Query<Product> query = session.createQuery(sql, Product.class);
            query.setParameter("code", code);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProductInfo findProductInfo(String code) {
        Product product = this.findProduct(code);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getPrice());
    }

    // if there's an error then nothing changes in db
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(ProductForm productForm) {

        Session session = this.entityManager.unwrap(Session.class);
        String code = productForm.getCode();

        Product product = null;

        boolean isNew = false;
        if (code != null) {
            product = this.findProduct(code);
        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }

       
        product.setCode(code);
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());

        if (productForm.getFileData() != null) {
            byte[] image = null;
            try {
                image = productForm.getFileData().getBytes();
            } catch (IOException e) {
            }
            if (StringUtils.isNotEmpty(code)) {
                product.setImage(image);
            }
        }
        if (isNew) {
            session.persist(product);
        }
        session.flush();
    }

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + ProductInfo.class.getName() + "(p.code, p.name, p.price) " + " from "
                + Product.class.getName() + " p ";
        if (StringUtils.isNotEmpty(likeName)) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
        //
        Session session = this.entityManager.unwrap(Session.class);
        Query<ProductInfo> query = session.createQuery(sql, ProductInfo.class);

        if (StringUtils.isNotEmpty(likeName)) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<>(query, page, maxResult, maxNavigationPage);
    }

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        return queryProducts(page, maxResult, maxNavigationPage, null);
    }

}