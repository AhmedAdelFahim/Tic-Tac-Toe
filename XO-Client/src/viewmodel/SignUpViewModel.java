package viewmodel;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.ClientSideHandler;
import utils.Utils;

import java.util.HashMap;

public class SignUpViewModel {
    private static final BooleanProperty toPlayScreenFlag = new SimpleBooleanProperty();
    public static void signUp(HashMap<String,Object> map){


        toPlayScreenFlag.setValue(ClientSideHandler.getInstance().signUp(Utils.toString(map)));
    }

    public static boolean isToPlayScreenFlag() {
        return toPlayScreenFlag.get();
    }

    public static BooleanProperty toPlayScreenFlagProperty() {
        return toPlayScreenFlag;
    }

    public static void setToPlayScreenFlag(boolean toPlayScreenFlag) {
        SignUpViewModel.toPlayScreenFlag.set(toPlayScreenFlag);

    }
}
