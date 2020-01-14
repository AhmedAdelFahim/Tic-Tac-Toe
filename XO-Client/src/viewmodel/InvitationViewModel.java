package viewmodel;

import com.google.gson.JsonObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.ClientSideHandler;
import utils.Utils;

import java.util.HashMap;
import ui.PlayModeController;
import utils.Constant;

public class InvitationViewModel {
    private static final BooleanProperty currentInviteScreenflag = new SimpleBooleanProperty();
    
    public static void handleInvitation(JsonObject jsonObject) {
        currentInviteScreenflag.setValue(Boolean.TRUE);  
        PlayModeController.senderUserName=jsonObject.get(Constant.SENDER_NAME_KEY).toString();

    }
    
    public static BooleanProperty tocurrentInviteScreenflagProperty() {
        return currentInviteScreenflag;
    }
    
    public static boolean iscurrentInviteScreenflag() {
        return currentInviteScreenflag.get();
    }

    // switch(currentInviteScreenflag)
}
