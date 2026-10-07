package com.gotaigas.util;

public class Globals {
    public static String CURRENT_USER = "current_User";
    public static String CHAT_HISTORY = "chat_History";

    public static class Pages {
        public static final String SHOW_IDEAS = "show";
        public static final String ENTER_CAR = "enter";

        public static final String LOGIN_VIEW = "login";
        public static final String MAIN_VIEW = "";

        public static final String CREATE_ACCOUNT = "CreateAccount";
        public static final String CREATE_STUDENT_ACCOUNT = "CreateStudentAccount";
        public static final String CREATE_INVESTOR_ACCOUNT = "CreateInvestorAccount";
        public static final String DECIDE_USER_TYPE = "decideUserType";
        //public static final String TAIGA = "taiga";
        public static final String PROFILE = "dashboard";
        public static final String CREATE_IDEA = "createIdea";
    }

    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String USER = "user";

    }

    public static class Errors {
        public static final String NOUSERFOUND = "nouser";
        public static final String SQLERROR = "sql";
        public static final String DATABASE = "database";
    }

}
