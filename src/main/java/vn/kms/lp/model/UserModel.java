package vn.kms.lp.model;

public class UserModel {
    private String userName;
    private String passWord;

    public UserModel(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    @Override
    public boolean equals(Object obj) {
        UserModel user = (UserModel) obj;
        if (this.userName.equals(user.getUserName()) && this.passWord.equals(user.getPassWord())) {
            return true;
        }
        return false;
    }
}
