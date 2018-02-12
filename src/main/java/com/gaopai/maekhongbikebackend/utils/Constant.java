package com.gaopai.maekhongbikebackend.utils;

public class Constant {

    public static final String SECRET_KEY = "gw*1ar4Y0*KoF!v#yu%b2kG3e<4{v(*WV|J}|M";

    public static final String APP_TOKEN = "maekhongbike@version1.0";

    public static final String CURRENT_TIME = System.currentTimeMillis() + "|";

    public static final String IMAGE_PATH = "http://res.cloudinary.com/testhero2/image/upload/testherov2/";

    public static final String MEDIA_FOLDER = "media/";

    public static final String IMAGE_PATH_VERSION_ONE = "http://res.cloudinary.com/testhero2/image/upload/v1/testherov2/";

    public static interface APP_NAME {
        public static final String TRAINER = "trainer";
        public static final String ADMIN = "admin";
        public static final String USER = "user";
    }

    public static interface EXAM_STATUS {
        public static final Boolean ACTIVE = false;
        public static final Boolean DELETE = true;
    }

    public static interface SECTION_ITEM_TYPE {
        public static final String VIDEO = "video";
        public static final String PDF = "pdf";
        public static final String QUIZ = "quiz";
    }

    public static interface CART_ITEM_TYPE {
        public static final String COURSE = "course";
        public static final String CERTIFICATE = "certificate";
    }

    public static interface ORDER_ITEM_TYPE {
        public static final String COURSE = "course";
        public static final String CERTIFICATE = "certificate";
    }

    public static interface MY_TRAINING_ITEM_STATUS {
        public static final String AWAITING = "awaiting";
        public static final String PENDING = "pending";
        public static final String COMPLETE = "complete";
    }
   /////////////////// //Constant From testherov2//////////////////

//    public static interface ORDER_DESCRIPTION {
//        public static final String BUY_CREDIT = "buy more credit";
//    }
//
//    public static interface ORDER_STATUS {
//        public static final String BUY = "buy";
//        public static final String USED = "used";
//    }
//
//    public static interface LEARNING_TYPE {
//        public static final String LESSON = "lesson";
//        public static final String COURSE = "course";
//        public static final String EXAM = "exam";
//    }
//
//    public static String ORDER_DESCRIPTION_MSG(String type, int credit, String unlock, String description) {
//        String msg = "";
//        switch (type){
//            case "buy":
//                msg = ORDER_DESCRIPTION.BUY_CREDIT.concat(" \""+ credit +" Credit\"");
//                break;
//            case "used":
//                msg = "Used " + credit + " credit to unlock " + unlock + " \"" + description + "\"";
//                break;
//        }
//
//        return msg;
//    }
    //////////////////////////////////////////////////////


    public static interface MY_TRAINING_TYPE {
        public static final String COURSE = "course";
        public static final String CERTIFICATE = "certificate";
    }

    public static interface MY_TRAINING_ITEM_TYPE {
        public static final String VIDEO = "video";
        public static final String PDF = "pdf";
        public static final String QUIZ = "quiz";
        public static final String EXAM = "exam";
    }

    public static interface ROLE {
        public static final String TRAINER = "Trainer";
        public static final String TRAINEE = "Trainee";
        public static final String Admin = "admin";
    }

    public static interface STATUS {
        public static final String ACTIVE = "active";
        public static final String INACTIVE = "inactive";
    }

    public static interface RESPONSE {
        public static interface STATUS {
            public static final int EXCEPTION = 500;
            public static final int SUCCESS = 200;
            public static final int BAD_REQUEST = 400;
            public static final int INVALID_TOKEN = 401;
            public static final int INVALID_APP_TOKEN = 401;
            public static final int INTERNAL_SERVER_ERROR = 500;
            public static final int INVALID_PARAMETERS = 402;
            public static final int INPUT_TIME_OVERLAP = 403;
            public static final int INVALID_FILE = 404;
            public static final int EXIST_DATA = 201;
        }

        public static interface MSG {
            public static final String EXCEPTION = "internal server error";
            public static final String SUCCESS = "ok";
            public static final String UNAUTHORIZED = "unauthorized";
            public static final String BAD_REQUEST = "bad request";
            public static final String INVALID_TOKEN = "invalid token";
            public static final String INVALID_APP_TOKEN = "invalid app token";
            public static final String INTERNAL_SERVER_ERROR = "internal server error";
            public static final String DATA_NOT_FOUND = "data not found";
            public static final String INVALID_PARAMETERS = "invalid parameters";
            public static final String DATA_DUPLICATED = "data is duplicated";
            public static final String USER_DUPLICATED = "user is duplicated";
            public static final String INPUT_TIME_OVERLAP = "time is overlap";
            public static final String INVALID_FILE = "invalid file";
            public static final String USER_NOT_FOUND = "user not found";
            public static final String USER_NOT_VERIFIED_EMAIL = "user not verified email";
            public static final String PASSWORD_INCORRECT = "password incorrect";
            public static final String PASSWORD_NOT_MATCH = "password not match";
            public static final String REQUIRED_FIELD = "required field";
            public static final String FACEBOOK_CONNECTED = "This facebook account is already connected to another user";
            public static final String NOT_DISCONNECT_FACEBOOK = "Could not disconnect facebook account";
            public static final String GOOGLE_CONNECTED = "This google account is already connected to another user";
            public static final String NOT_DISCONNECT_GOOGLE = "Could not disconnect google account";
        }
    }

    public static interface SERVICE {
        public static final String EMAIL = "email";
        public static final String FACEBOOK = "facebook";
        public static final String GOOGLE = "google";
        public static final String LOGIN = "login";
        public static final String REGISTER = "register";
        public static final String LOGIN_RESULT = "login_result";
    }

    public static interface USER_SESSION {
        public static interface STATUS {
            public static final String AWAITING = "awaiting"; // after student input code
            public static final String READY = "ready"; // after trainer approved session
            public static final String PENDING = "pending"; // after student start testSession
            public static final String SUBMIT = "submit";
            public static final String COMPLETE = "complete";
            public static final String AWAITING_SCORE = "awaiting_score";
            public static final String FINISHED = "finished";
            public static final String FORCE_SUBMIT = "force_submit";
            public static final String KICKED = "kicked";
            public static final String TIME_OUT = "time_out";
        }
    }

    public static interface USER_ATTEMPT_STATUS {
        public static final String AWAITING = "awaiting";
        public static final String PENDING = "pending";
        public static final String SKIP = "skip";
        public static final String SUBMIT = "submit";
        public static final String COMPLETE = "complete";
        public static final String AWAITING_SCORE = "awaiting_score";
        public static final String FINISHED = "finished";
    }

    public static interface PASSAGE_TYPE {
        public static final String TEXT = "text";
        public static final String IMAGE = "image";
        public static final String SOUND = "sound";
    }

    public static interface QUESTION_TYPE {
        public static final String COL_PASSAGE = "P";
        public static final String COL_SINGLE = "S";
        public static final String PASSAGE = "passage";
        public static final String SINGLE = "single";
    }

    public static interface CHOICE_TYPE {
        public static final String RADIO = "radio";
        public static final String CHECKBOX = "checkbox";
        public static final String MATRIX = "matrix";
        public static final String RECORD = "record";
        public static final String TEXT = "text";
    }

    public static final Long ZERO_SCORE = 0L;
}
