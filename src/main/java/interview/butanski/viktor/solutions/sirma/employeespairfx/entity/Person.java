package interview.butanski.viktor.solutions.sirma.employeespairfx.entity;

public abstract class Person {
    private String id;

    public Person() { }

    public Person(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
