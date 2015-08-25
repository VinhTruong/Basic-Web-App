package vn.kms.lp.dao;

public interface UserFactory {
    void fetchData();
    boolean checkUser(String userName, String passWord);
}
