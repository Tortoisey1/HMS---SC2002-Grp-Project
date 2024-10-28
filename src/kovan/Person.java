public class Person {
    private int age;
    private Gender gender;
    private Role role;
    private String name;

    Person(){}
    Person(int age, Gender gender, Role role, String name){
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.name = name;

    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
