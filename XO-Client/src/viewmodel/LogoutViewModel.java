package viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.ClientSideHandler;
import utils.Utils;

import java.util.HashMap;

public class LogoutViewModel {

    private static final BooleanProperty toSignUpFlag = new SimpleBooleanProperty();

    public static void logout(HashMap<String,Object> map){
        ClientSideHandler.getInstance().logout(Utils.toString(map));
    }

    public static boolean isToSignUpFlag() {
        return toSignUpFlag.get();
    }

    public static BooleanProperty toSignUpFlagProperty() {
        return toSignUpFlag;
    }

    public static void setToSignUpFlag(boolean toSignUpFlag) {
        LogoutViewModel.toSignUpFlag.set(toSignUpFlag);
    }
}
