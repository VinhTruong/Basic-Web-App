package vn.kms.lp.dao;

public interface UserFactory {
    void fetchData();
	boolean CheckUser(String userName, String passWord);
}
