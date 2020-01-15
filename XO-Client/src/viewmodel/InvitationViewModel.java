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
    private static  String acceptedInvitationFlag = null;
    public static void handleInvitation(JsonObject jsonObject) {
        currentInviteScreenflag.setValue(Boolean.TRUE);  
        PlayModeController.senderUserName=jsonObject.get(Constant.SENDER_NAME_KEY).toString();
        PlayModeController.invitationJason =jsonObject;
    }
    
    public static BooleanProperty tocurrentInviteScreenflagProperty() {
        return currentInviteScreenflag;
    }
     public static void resetCurrentInviteScreenflag() {
         currentInviteScreenflag.setValue(Boolean.FALSE);  
    }
     
    public static String tocurrentacceptedInvitationFlag() {
        return acceptedInvitationFlag;
    } 
    public static void acceptedInvitationFlag() {
         acceptedInvitationFlag = "accepted";
    }
    public static void declinedInvitationFlag() {
         acceptedInvitationFlag= "declined";;  
    }
    
    public static boolean iscurrentInviteScreenflag() {
        return currentInviteScreenflag.get();
    }

    // switch(currentInviteScreenflag)
}
