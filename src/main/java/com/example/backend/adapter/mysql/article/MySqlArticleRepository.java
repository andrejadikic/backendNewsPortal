package com.example.backend.model.article.repository;

import com.example.backend.application.MySqlAbstractRepository;
import com.example.backend.model.article.Article;
import org.checkerframework.checker.units.qual.A;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlArticleRepository extends MySqlAbstractRepository implements ArticleRepository {

    @Override
    public Article addOne(Article article) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO articles (title, text, created_at, viewCount, author, categoryId, tags) VALUES (?, ?,?, ?,?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getText());
            preparedStatement.setLong(3, System.currentTimeMillis());
            preparedStatement.setLong(4, 0);
            preparedStatement.setString(5, article.getAuthor());
            preparedStatement.setInt(6, article.getCategoryId());
            preparedStatement.setString(7, article.getTags());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                article.setId(resultSet.getInt(1));
                article.setViewCount(0L);
                article.setCreated_At(System.currentTimeMillis());
                article.setLikeCount(0L);
                article.setDislikeCount(0L);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return article;
    }

    @Override
    public List<Article> getAll() {
        List<Article> articles = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM articles");
            while (resultSet.next()){
                articles.add(new Article(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("text"), resultSet.getString("author"), resultSet.getInt("categoryId"),
                        resultSet.getString("tags"), resultSet.getLong("created_at"), resultSet.getLong("viewCount"),
                        resultSet.getLong("likeCount"), resultSet.getLong("dislikeCount")));

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
            this.closeStatement(statement);
        }
        return articles;
    }

    @Override
    public List<Article> getAll(Integer categoryId) {
        List<Article> articles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM articles WHERE categoryId = ?");
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                articles.add(new Article(resultSet.getInt("id"), resultSet.getString("title"),resultSet.getString("text"),
                        resultSet.getString("author"),
                        resultSet.getInt("categoryId"), resultSet.getString("tags"), resultSet.getLong("created_at"),
                        resultSet.getLong("viewCount"),
                        resultSet.getLong("likeCount"), resultSet.getLong("dislikeCount") ));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
        }
        return articles;
    }

    @Override
    public List<Article> getByName(String text) {
        List<Article> articles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM articles WHERE title LIKE ? or text LIKE ?");
            preparedStatement.setString(1, "%" + text + "%");
            preparedStatement.setString(2, "%" + text + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                articles.add(new Article(resultSet.getInt("id"), resultSet.getString("title"),resultSet.getString("text"),
                        resultSet.getString("author"),
                        resultSet.getInt("categoryId"), resultSet.getString("tags"), resultSet.getLong("created_at"),
                        resultSet.getLong("viewCount"),
                        resultSet.getLong("likeCount"), resultSet.getLong("dislikeCount") ));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
        }
        return articles;
    }

    @Override
    public void increaseViews(Integer articleId, long newCount) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE articles SET viewCount = ? WHERE id = ?");
            preparedStatement.setLong(1, newCount);
            preparedStatement.setInt(2, articleId);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeConnection(connection);
            this.closeStatement(preparedStatement);
        }
    }

    @Override
    public Article getOne(Integer id) {
        Article article = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM articles where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                article = new Article(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("text"), resultSet.getString("author"),
                        resultSet.getInt("categoryId"),resultSet.getString("tags"), resultSet.getLong("created_at"),
                        resultSet.getLong("viewCount"), resultSet.getLong("likeCount"), resultSet.getLong("dislikeCount"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return article;
    }

    @Override
    public void deleteOne(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM articles where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
    @Override
    public void updateOne(Integer id, ArticleUpdateDto data) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE articles SET title = ?, text = ?, author = ? , categoryId = ? WHERE id = ?");
            preparedStatement.setString(1, data.getTitle());
            preparedStatement.setString(2, data.getText());
            preparedStatement.setString(3, data.getAuthor());
            preparedStatement.setInt(4, data.getCategoryId());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeConnection(connection);
            this.closeStatement(preparedStatement);
        }
    }
}
