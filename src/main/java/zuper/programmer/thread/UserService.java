package zuper.programmer.thread;

public class UserService {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
//    String name;

    public void setUser(String user) {
        threadLocal.set(user);
//        name = user;
    }

    public void doAction() {
        String user = threadLocal.get();

        System.out.println(user + " do action ");
    }
}
