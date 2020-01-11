public class LogInViewModel {
    private static final BooleanProperty toPlayScreenFlag = new SimpleBooleanProperty();
    public static void logIn(HashMap<String,Object> map){

        toPlayScreenFlag.setValue(ClientSideHandler.getInstance().logIn(Utils.toString(map)));
    }

    public static boolean isToPlayScreenFlag() {
        return toPlayScreenFlag.get();
    }

    public static BooleanProperty toPlayScreenFlagProperty() {
        return toPlayScreenFlag;
    }

    public static void setToPlayScreenFlag(boolean toPlayScreenFlag) {
        LogInViewModel.toPlayScreenFlag.set(toPlayScreenFlag);

    }
}
