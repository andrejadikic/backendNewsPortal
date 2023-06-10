package com.example.backend.application;

import com.example.backend.adapter.mysql.article.MySqlArticleRepository;
import com.example.backend.adapter.mysql.category.MySqlCategoryRepository;
import com.example.backend.adapter.mysql.comment.MySqlCommentRepository;
import com.example.backend.adapter.mysql.user.MySqlUserRepository;
import com.example.backend.model.article.ArticleRepository;
import com.example.backend.model.article.ArticleService;
import com.example.backend.model.category.CategoryRepository;
import com.example.backend.model.category.CategoryService;
import com.example.backend.model.comment.CommentRepository;
import com.example.backend.model.comment.CommentService;
import com.example.backend.model.user.UserRepository;
import com.example.backend.model.user.UserService;
import com.example.backend.model.user.security.AuthService;
import com.example.backend.model.user.security.SecurityService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlArticleRepository.class).to(ArticleRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bindAsContract(AuthService.class);
                this.bindAsContract(UserService.class);
                this.bindAsContract(ArticleService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(SecurityService.class);
            }
        };
        register(binder);
        packages("com.example.backend");
    }
}