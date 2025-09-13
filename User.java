package Week1Work;

abstract class User {
   protected String id;
   protected String username;
   protected String password;
   protected String name;
   protected String IdentityId;
   protected String role;

    public User(String id, String username, String password, String name, String IdentityId, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.IdentityId = IdentityId;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityId() {
        return IdentityId;
    }

    public void setIdentityId(String IdentityId) {
        this.IdentityId = IdentityId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}

//Students
class Student extends User {

    public Student(String id, String username, String password, String name, String IdentityId, String role) {
        super(id, username, password, name, IdentityId, role);
    }


}

//teachers
class Teacher extends User {

    public Teacher(String id, String username, String password, String name, String IdentityId, String role) {
        super(id, username, password, name, IdentityId, role);
    }

}

class Counselor extends User{

    public Counselor(String id, String username, String password, String name, String IdentityId, String role) {
        super(id, username, password, name, IdentityId, role);
    }
}