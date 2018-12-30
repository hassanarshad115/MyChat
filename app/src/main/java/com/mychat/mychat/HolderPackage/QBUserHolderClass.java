package com.mychat.mychat.HolderPackage;

import android.util.SparseArray;

import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class QBUserHolderClass {
    private static QBUserHolderClass instance; //isi class ka instance yni veriable bnaya
    private SparseArray<QBUser> qbUserSparseArray;
    public static synchronized QBUserHolderClass getInstance(){
        if (instance==null)
            instance=new QBUserHolderClass();
        return instance;
    }

    public QBUserHolderClass() {
        qbUserSparseArray = new SparseArray<>(); }

        public void putUsersMethod(List<QBUser> qbUsers){

        for (QBUser user:qbUsers)
            putUser(user);
        }

    private void putUser(QBUser user) {
        qbUserSparseArray.put(user.getId(),user);
    }
    public QBUser getUserByIdMethod(int id){
        return qbUserSparseArray.get(id);
    }

    public List<QBUser> getUsersByIdMthod(List<Integer> ids){
        List<QBUser> qbUsers=new ArrayList<>();
        for (Integer id:ids){
            //oper wala method yha call hoa
            QBUser User=getUserByIdMethod(id);
            if (User !=null)
                qbUsers.add(User);
        }
        return qbUsers;
    }
}
