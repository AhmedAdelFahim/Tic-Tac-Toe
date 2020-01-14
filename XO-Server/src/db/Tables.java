package db;

public class Tables {

    public static class player{
        public static final String ID = "id";
        public static final String FIRST_NAME ="first_name";
        public static final String LAST_NAME = "last_name";
        public static final String USER_NAME = "user_name";
        public static final String IMAGE_URL = "image_url";
        public static final String SCORE = "score";
        public static final String STATUE = "status";
        public static final String PASSWORD = "password";
    }

    public static class saved_game{
        public static final String ID = "id";
        public static final String HOST_ID ="host_id";
        public static final String GUEST_ID = "guest_id";
        public static final String GAME_BOARD = "game_board";
    }
}
