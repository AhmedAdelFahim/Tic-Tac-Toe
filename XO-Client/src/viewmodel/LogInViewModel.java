package viewmodel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.ClientSideHandler;
import utils.Utils;

import java.util.HashMap;

public class LogInViewModel {
//    private static  BooleanProperty toPlayScreenFlag = new SimpleBooleanProperty();
    private static IntegerProperty toPlayScreenFlag = new SimpleIntegerProperty(-1);
    public static void logIn(HashMap<String,Object> map){

        //System.out.println(toPlayScreenFlag);
        if(ClientSideHandler.getInstance().logIn(Utils.toString(map)))
        {
            toPlayScreenFlag.setValue(1);               // 1 > succeeded
        }
        else {
            toPlayScreenFlag.setValue(0);               // 0 > failed
        }
        //System.out.println(toPlayScreenFlag);
    }

    public static int isToPlayScreenFlag() {
        return toPlayScreenFlag.get();
    }

    public static IntegerProperty toPlayScreenFlagProperty() {
        return toPlayScreenFlag;
    }

    public static void setToPlayScreenFlag(int toPlayScreenFlag) {
        LogInViewModel.toPlayScreenFlag.set(toPlayScreenFlag);

    }
}
