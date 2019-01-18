package util;

public class CourseComboListitems {

    private String id;
    private String name;


    public CourseComboListitems(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override // overwrite two string method
    public String toString() {
        return getId()+" - "+getName();
    }
}
