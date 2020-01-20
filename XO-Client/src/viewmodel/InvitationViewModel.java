package viewmodel;

import com.google.gson.JsonObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import ui.PlayModeController;

public class InvitationViewModel {
     private static IntegerProperty currentInviteScreenflag = new SimpleIntegerProperty(-1);
      private static IntegerProperty declinedInvitationFlag = new SimpleIntegerProperty(-1);
      
    public static void handleInvitation(JsonObject jsonObject) {
        currentInviteScreenflag.setValue(1);  
        PlayModeController.invitationJason =jsonObject;
    }
   
    
    public static IntegerProperty tocurrentInviteScreenflagProperty() {
        return currentInviteScreenflag;
    }
     public static void resetCurrentInviteScreenflag() {
         currentInviteScreenflag.setValue(-1);  
    }
     
      public static void declineInvitation(JsonObject jsonObject) {
        declinedInvitationFlag.setValue(1);  
    }
    public static IntegerProperty toDeclinedInvitationFlag() {
        return declinedInvitationFlag;
    }
    public static void resetDeclinedInvitationFlag() {
         declinedInvitationFlag.setValue(-1);  
    }
   
  
    
    public static int iscurrentInviteScreenflag() {
        return currentInviteScreenflag.get();
    }
    
    

    // switch(currentInviteScreenflag)
}
