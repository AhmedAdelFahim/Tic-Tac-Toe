package viewmodel;

import com.google.gson.JsonObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import ui.PlayModeController;

public class InvitationViewModel {
    private static final BooleanProperty currentInviteScreenflag = new SimpleBooleanProperty();
    private static final BooleanProperty declinedInvitationFlag = new SimpleBooleanProperty();
    public static void handleInvitation(JsonObject jsonObject) {
        currentInviteScreenflag.setValue(Boolean.TRUE);  
        PlayModeController.invitationJason =jsonObject;
    }
   
    
    public static BooleanProperty tocurrentInviteScreenflagProperty() {
        return currentInviteScreenflag;
    }
     public static void resetCurrentInviteScreenflag() {
         currentInviteScreenflag.setValue(Boolean.FALSE);  
    }
     
      public static void declineInvitation(JsonObject jsonObject) {
        declinedInvitationFlag.setValue(Boolean.TRUE);  
    }
    public static BooleanProperty toDeclinedInvitationFlag() {
        return declinedInvitationFlag;
    }
    public static void resetDeclinedInvitationFlag() {
         declinedInvitationFlag.setValue(Boolean.FALSE);  
    }
   
  
    
    public static boolean iscurrentInviteScreenflag() {
        return currentInviteScreenflag.get();
    }
    
    

    // switch(currentInviteScreenflag)
}
