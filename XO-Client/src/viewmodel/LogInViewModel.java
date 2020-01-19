package viewmodel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.ClientSideHandler;
import utils.Utils;

import java.util.HashMap;

public class LogInViewModel {
    private static  BooleanProperty toPlayScreenFlag = new SimpleBooleanProperty();
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
