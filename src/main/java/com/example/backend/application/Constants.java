package rs.raf.demo.enums;

public class Constants {

    public static final String INTERNAL_ERROR = "Internal server error";

    public static class UserType {
        public static final String CREATOR = "content_creator";
        public static final String ADMIN = "admin";
    }

    public static class EntityType {
        public static final String ARTICLE = "article";
        public static final String COMMENT = "comment";
    }

    public static class LikeType {
        public static final String LIKE = "like";
        public static final String DISLIKE = "dislike";
    }
}
