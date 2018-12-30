package com.mychat.mychat.CommonPackage;

import com.mychat.mychat.HolderPackage.QBUserHolderClass;
import com.quickblox.users.model.QBUser;

import java.util.List;

public class CommonClass {
    public static String CreateChatDialogNameMethod(List<Integer> qbuser){
      List<QBUser> qbUser1=QBUserHolderClass.getInstance().getUsersByIdMthod(qbuser);
      StringBuilder name=new StringBuilder();
      for (QBUser user:qbUser1)
          name.append(user.getFullName()).append(" ");
      if (name.length()>30)
          name=name.replace(30,name.length()-1,"...");
      return name.toString();
    }
}
