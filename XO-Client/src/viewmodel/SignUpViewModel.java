package viewmodel;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.ClientSideHandler;
import utils.Utils;

import java.util.HashMap;

public class SignUpViewModel {
    private static final IntegerProperty toPlayScreenFlag = new SimpleIntegerProperty();
    public static void signUp(HashMap<String,Object> map){

        if(ClientSideHandler.getInstance().signUp(Utils.toString(map)))
        {
            toPlayScreenFlag.setValue(1);               // 1 > succeeded
        }
        else {
            toPlayScreenFlag.setValue(0);               // 0 > failed
        }


    }

    public static int isToPlayScreenFlag() {
        return toPlayScreenFlag.get();
    }

    public static IntegerProperty toPlayScreenFlagProperty() {
        return toPlayScreenFlag;
    }

    public static void setToPlayScreenFlag(int toPlayScreenFlag) {
        SignUpViewModel.toPlayScreenFlag.set(toPlayScreenFlag);

    }
}
